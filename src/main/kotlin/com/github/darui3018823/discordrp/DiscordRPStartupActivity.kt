package com.github.darui3018823.discordrp

import com.intellij.openapi.project.Project
import com.intellij.openapi.startup.StartupActivity

class DiscordRPStartupActivity : StartupActivity.DumbAware {
    override fun runActivity(project: Project) {
        val service = DiscordRPService.getInstance()
        if (!service.isConnected) {
            service.connect()
        }
        project.getService(ProjectRPService::class.java)?.updateRichPresence()
    }
}
