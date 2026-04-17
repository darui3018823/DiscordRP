package com.github.darui3018823.discordrp

import com.intellij.openapi.Disposable
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.components.Service
import com.intellij.openapi.diagnostic.logger
import com.intellij.openapi.project.ProjectManager
import com.jagrosh.discordipc.IPCClient
import com.jagrosh.discordipc.IPCListener
import com.jagrosh.discordipc.entities.RichPresence
import java.time.OffsetDateTime
import java.time.ZoneOffset
import java.util.concurrent.Executors

@Service(Service.Level.APP)
class DiscordRPService : Disposable {

    companion object {
        private const val APPLICATION_ID = 1494417249736589342L
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
                // Update presence for all open projects after connection is established
                ProjectManager.getInstance().openProjects.forEach { project ->
                    project.getService(ProjectRPService::class.java)?.updateRichPresence()
                }
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
            isConnected = false
            try { client.sendRichPresence(null) } catch (_: Exception) {}
            try { client.close() } catch (_: Exception) {}
        }
    }

    fun updatePresence(builder: RichPresence.Builder) {
        worker.submit {
            if (!isConnected) return@submit
            try {
                val presence = builder.setStartTimestamp(
                    OffsetDateTime.ofInstant(
                        java.time.Instant.ofEpochSecond(startTime),
                        ZoneOffset.UTC
                    )
                ).build()
                client.sendRichPresence(presence)
            } catch (e: Exception) {
                log.warn("Failed to update Discord presence: ${e.message}")
                isConnected = false
            }
        }
    }

    override fun dispose() {
        // Clear presence immediately before shutdown so Discord removes it without waiting for timeout
        if (isConnected) {
            try { client.sendRichPresence(null) } catch (_: Exception) {}
        }
        worker.shutdownNow()
        try { client.close() } catch (_: Exception) {}
    }
}
