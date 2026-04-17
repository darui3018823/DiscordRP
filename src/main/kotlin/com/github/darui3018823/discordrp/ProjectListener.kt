package com.github.darui3018823.discordrp

import com.intellij.openapi.project.Project
import com.intellij.openapi.project.ProjectManagerListener

@Suppress("OVERRIDE_DEPRECATION")
class ProjectListener : ProjectManagerListener {

    override fun projectOpened(project: Project) {
        val rpcService = DiscordRPService.getInstance()
        if (!rpcService.isConnected) {
            rpcService.connect()
        }
        project.getService(ProjectRPService::class.java)?.updateRichPresence()
    }

    override fun projectClosed(project: Project) {
        val openProjects = com.intellij.openapi.project.ProjectManager.getInstance().openProjects
            .filter { it != project }

        if (openProjects.isEmpty()) {
            DiscordRPService.getInstance().disconnect()
        } else {
            openProjects.firstOrNull()
                ?.getService(ProjectRPService::class.java)
                ?.updateRichPresence()
        }
    }
}
