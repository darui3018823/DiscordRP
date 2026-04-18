package com.github.darui3018823.discordrp

import com.intellij.openapi.diagnostic.logger
import org.json.JSONObject
import java.io.Closeable
import java.io.RandomAccessFile
import java.net.StandardProtocolFamily
import java.net.UnixDomainSocketAddress
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.channels.SocketChannel
import java.util.UUID
import java.util.concurrent.Executors
import java.util.concurrent.atomic.AtomicBoolean

class DiscordIpcClient(private val clientId: Long) : Closeable {

    companion object {
        private val log = logger<DiscordIpcClient>()
        private const val OPCODE_HANDSHAKE = 0
        private const val OPCODE_FRAME = 1
        private const val OPCODE_CLOSE = 2
    }

    interface Listener {
        fun onReady()
        fun onDisconnect(error: Throwable?)
    }

    var listener: Listener? = null

    private val running = AtomicBoolean(false)
    private val readerExecutor = Executors.newSingleThreadExecutor { r ->
        Thread(r, "DiscordIPC-Reader").apply { isDaemon = true }
    }

    private var windowsPipe: RandomAccessFile? = null
    private var unixChannel: SocketChannel? = null

    val isConnected: Boolean get() = running.get()

    private val isWindows = System.getProperty("os.name", "").lowercase().startsWith("win")

    fun connect() {
        for (i in 0..9) {
            try {
                if (isWindows) connectWindows(i) else connectUnix(i)
                return
            } catch (e: Exception) {
                log.debug("discord-ipc-$i failed: ${e.message}")
                closeTransport()
            }
        }
        throw Exception("Could not connect to any Discord IPC pipe")
    }

    private fun connectWindows(i: Int) {
        val path = "\\\\.\\pipe\\discord-ipc-$i"
        val pipe = RandomAccessFile(path, "rw")
        windowsPipe = pipe
        writeRaw(OPCODE_HANDSHAKE, handshakeJson())
        readFrame()
        running.set(true)
        startReadLoop()
        listener?.onReady()
    }

    private fun connectUnix(i: Int) {
        val dir = System.getenv("XDG_RUNTIME_DIR")
            ?: System.getenv("TMPDIR")
            ?: System.getenv("TMP")
            ?: System.getenv("TEMP")
            ?: "/tmp"
        val path = "$dir/discord-ipc-$i"
        val ch = SocketChannel.open(StandardProtocolFamily.UNIX)
        ch.connect(UnixDomainSocketAddress.of(path))
        unixChannel = ch
        writeRaw(OPCODE_HANDSHAKE, handshakeJson())
        readFrame()
        running.set(true)
        startReadLoop()
        listener?.onReady()
    }

    private fun handshakeJson() = JSONObject()
        .put("v", 1)
        .put("client_id", clientId.toString())
        .toString()

    fun sendActivity(activity: JSONObject?) {
        if (!running.get()) return
        val args = JSONObject().put("pid", ProcessHandle.current().pid())
        if (activity != null) args.put("activity", activity)
        val payload = JSONObject()
            .put("cmd", "SET_ACTIVITY")
            .put("args", args)
            .put("nonce", UUID.randomUUID().toString())
        try {
            synchronized(this) { writeRaw(OPCODE_FRAME, payload.toString()) }
        } catch (e: Exception) {
            log.warn("Failed to send activity: ${e.message}")
        }
    }

    private fun writeRaw(opcode: Int, data: String) {
        val bytes = data.toByteArray(Charsets.UTF_8)
        val header = ByteBuffer.allocate(8).order(ByteOrder.LITTLE_ENDIAN)
            .putInt(opcode).putInt(bytes.size).array()
        windowsPipe?.let { p -> p.write(header); p.write(bytes); return }
        unixChannel?.let { ch ->
            ch.write(ByteBuffer.wrap(header))
            ch.write(ByteBuffer.wrap(bytes))
        }
    }

    private fun readFrame(): JSONObject? {
        val headerBuf = ByteArray(8)
        windowsPipe?.readFully(headerBuf) ?: readUnixFully(headerBuf)
        val buf = ByteBuffer.wrap(headerBuf).order(ByteOrder.LITTLE_ENDIAN)
        val opcode = buf.int
        val length = buf.int
        val dataBuf = ByteArray(length)
        windowsPipe?.readFully(dataBuf) ?: readUnixFully(dataBuf)
        if (opcode == OPCODE_CLOSE) return null
        return JSONObject(String(dataBuf, Charsets.UTF_8))
    }

    private fun readUnixFully(buf: ByteArray) {
        val ch = unixChannel ?: return
        val bb = ByteBuffer.wrap(buf)
        while (bb.hasRemaining()) ch.read(bb)
    }

    private fun startReadLoop() {
        readerExecutor.submit {
            try {
                while (running.get()) {
                    readFrame() ?: break
                }
            } catch (e: Exception) {
                if (running.getAndSet(false)) {
                    listener?.onDisconnect(e)
                }
            }
        }
    }

    private fun closeTransport() {
        try { windowsPipe?.close() } catch (_: Exception) {}
        try { unixChannel?.close() } catch (_: Exception) {}
        windowsPipe = null
        unixChannel = null
    }

    override fun close() {
        running.set(false)
        closeTransport()
        readerExecutor.shutdownNow()
    }
}
