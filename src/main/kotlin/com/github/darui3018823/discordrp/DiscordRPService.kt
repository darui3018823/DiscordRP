package com.github.darui3018823.discordrp

import com.intellij.openapi.Disposable
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.diagnostic.logger
import com.jagrosh.discordipc.IPCClient
import com.jagrosh.discordipc.IPCListener
import com.jagrosh.discordipc.entities.RichPresence
import java.util.concurrent.Executors

class DiscordRPService : Disposable {

    companion object {
        private const val APPLICATION_ID = 0L // TODO: set Discord Application ID
        private val log = logger<DiscordRPService>()

        fun getInstance(): DiscordRPService =
            ApplicationManager.getApplication().getService(DiscordRPService::class.java)
    }

    private val client = IPCClient(APPLICATION_ID)
    private val worker = Executors.newSingleThreadExecutor { r ->
        Thread(r, "DiscordRP-IPC").apply { isDaemon = true }
    }

    @Volatile
    var isConnected = false
        private set

    @Volatile
    private var startTime: Long = 0L

    init {
        client.setListener(object : IPCListener {
            override fun onReady(client: IPCClient) {
                log.info("Discord IPC connected")
            }
        })
    }

    fun connect() {
        if (isConnected) return
        worker.submit {
            try {
                client.connect()
                startTime = System.currentTimeMillis() / 1000L
                isConnected = true
            } catch (e: Exception) {
                log.info("Discord IPC connection failed (Discord may not be running): ${e.message}")
            }
        }
    }

    fun disconnect() {
        worker.submit {
            try {
                client.close()
            } catch (_: Exception) {
            } finally {
                isConnected = false
            }
        }
    }

    fun updatePresence(builder: RichPresence.Builder) {
        worker.submit {
            if (!isConnected) return@submit
            try {
                client.sendRichPresence(
                    builder.setStartTimestamp(startTime).build()
                )
            } catch (e: Exception) {
                log.warn("Failed to update Discord presence: ${e.message}")
                isConnected = false
            }
        }
    }

    override fun dispose() {
        worker.shutdownNow()
        try {
            client.close()
        } catch (_: Exception) {
        }
    }
}
