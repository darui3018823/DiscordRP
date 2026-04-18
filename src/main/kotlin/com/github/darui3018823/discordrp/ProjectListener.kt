package com.github.darui3018823.discordrp

import com.intellij.openapi.project.Project
import com.intellij.openapi.project.ProjectManagerListener

class ProjectListener : ProjectManagerListener {

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
