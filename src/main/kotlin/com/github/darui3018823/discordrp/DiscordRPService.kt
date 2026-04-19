package com.github.darui3018823.discordrp

import org.json.JSONObject
import com.intellij.notification.Notification
import com.intellij.notification.NotificationAction
import com.intellij.notification.NotificationGroupManager
import com.intellij.notification.NotificationType
import com.intellij.openapi.Disposable
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.components.Service
import com.intellij.openapi.diagnostic.logger
import com.intellij.openapi.project.ProjectManager
import java.util.concurrent.CopyOnWriteArrayList
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

@Service(Service.Level.APP)
class DiscordRPService : Disposable {

    companion object {
        private const val APPLICATION_ID = 1494417249736589342L
        private const val MILLIS_TO_SECONDS = 1000L
        private val log = logger<DiscordRPService>()

        fun getInstance(): DiscordRPService =
            ApplicationManager.getApplication().getService(DiscordRPService::class.java)
    }

    private val worker = Executors.newSingleThreadExecutor { r ->
        Thread(r, "DiscordRP-IPC").apply { isDaemon = true }
    }

    @Volatile
    var isConnected = false
        private set

    @Volatile
    private var startTime: Long = 0L

    @Volatile
    private var disconnectExpected = false

    @Volatile
    private var notifyOnReconnect = false

    private val activeNotifications = CopyOnWriteArrayList<Notification>()

    private fun newClient(): DiscordIpcClient = DiscordIpcClient(APPLICATION_ID).also { client ->
        client.listener = object : DiscordIpcClient.Listener {
            override fun onReady() {
                log.info("Discord IPC connected")
                if (notifyOnReconnect) {
                    notifyOnReconnect = false
                    ApplicationManager.getApplication().invokeLater {
                        val n = NotificationGroupManager.getInstance()
                            .getNotificationGroup("DiscordRP")
                            .createNotification("Discord Rich Presence", "Reconnected to Discord.", NotificationType.INFORMATION)
                        activeNotifications += n
                        n.whenExpired { activeNotifications -= n }
                        n.notify(null)
                    }
                }
                ProjectManager.getInstance().openProjects.forEach { project ->
                    project.getService(ProjectRPService::class.java)?.updateRichPresence()
                }
            }

            override fun onDisconnect(error: Throwable?) {
                if (!disconnectExpected) onUnexpectedDisconnect()
            }
        }
    }

    @Volatile
    private var client = newClient()

    private fun onUnexpectedDisconnect() {
        isConnected = false
        ApplicationManager.getApplication().invokeLater {
            val n = NotificationGroupManager.getInstance()
                .getNotificationGroup("DiscordRP")
                .createNotification("Discord Rich Presence", "Disconnected from Discord.", NotificationType.WARNING)
            activeNotifications += n
            n.whenExpired { activeNotifications -= n }
            n.addAction(NotificationAction.createSimple("Reconnect") {
                n.expire()
                reconnect()
            })
            n.notify(null)
        }
    }

    fun connect() {
        if (isConnected) return
        worker.submit {
            if (isConnected) return@submit
            try {
                client.connect()
                startTime = System.currentTimeMillis() / MILLIS_TO_SECONDS
                isConnected = true
            } catch (e: Exception) {
                log.info("Discord IPC connection failed (Discord may not be running): ${e.message}")
            }
        }
    }

    fun reconnect() {
        notifyOnReconnect = true
        worker.submit {
            disconnectExpected = true
            isConnected = false
            try { client.close() } catch (_: Exception) {}
            client = newClient()
            disconnectExpected = false
            try {
                client.connect()
                startTime = System.currentTimeMillis() / MILLIS_TO_SECONDS
                isConnected = true
            } catch (e: Exception) {
                log.info("Discord IPC reconnect failed: ${e.message}")
            }
        }
    }

    fun disconnect() {
        worker.submit {
            disconnectExpected = true
            isConnected = false
            try { client.sendActivity(null) } catch (_: Exception) {}
            try { client.close() } catch (_: Exception) {}
            client = newClient()
            disconnectExpected = false
        }
    }

    fun updatePresence(activity: JSONObject?) {
        worker.submit {
            if (!isConnected) return@submit
            try {
                client.sendActivity(activity)
            } catch (e: Exception) {
                log.warn("Failed to update Discord presence: ${e.message}")
                isConnected = false
            }
        }
    }

    override fun dispose() {
        disconnectExpected = true
        isConnected = false
        activeNotifications.forEach { it.expire() }
        activeNotifications.clear()
        worker.shutdown()
        try {
            if (!worker.awaitTermination(2, TimeUnit.SECONDS)) worker.shutdownNow()
        } catch (_: InterruptedException) {
            worker.shutdownNow()
        }
        try { client.sendActivity(null) } catch (_: Exception) {}
        try { client.close() } catch (_: Exception) {}
    }
}
