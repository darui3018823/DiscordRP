package com.github.darui3018823.discordrp

import com.intellij.ide.plugins.PluginManagerCore
import com.intellij.openapi.diagnostic.logger
import com.intellij.openapi.extensions.PluginId
import com.intellij.openapi.project.Project

object GitBranchDetector {

    private val log = logger<GitBranchDetector>()

    fun getBranch(project: Project): String? {
        return try {
            if (!PluginManagerCore.isPluginInstalled(PluginId.getId("Git4Idea"))) {
                log.warn("Git4Idea plugin not installed")
                return null
            }

            val manager = git4idea.repo.GitRepositoryManager.getInstance(project)
            val repo = manager.repositories.firstOrNull() ?: run {
                log.warn("No git repositories found in project: ${project.name}")
                return null
            }

            repo.currentBranch?.name ?: repo.currentRevision?.take(7)
        } catch (e: Throwable) {
            com.intellij.openapi.diagnostic.logger<GitBranchDetector>().warn("getBranch failed", e)
            null
        }
    }
}
