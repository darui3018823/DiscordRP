package com.github.darui3018823.discordrp

import com.intellij.openapi.project.Project
import com.intellij.openapi.project.guessProjectDir

object GitBranchDetector {

    fun getBranch(project: Project): String? {
        return try {
            val pluginId = com.intellij.ide.plugins.PluginManagerCore.getPluginByClassName(
                "git4idea.repo.GitRepositoryManager"
            )
            if (pluginId == null) return null

            val projectDir = project.guessProjectDir() ?: return null
            val manager = git4idea.repo.GitRepositoryManager.getInstance(project)
            val repo = manager.getRepositoryForFile(projectDir) ?: return null

            repo.currentBranch?.name ?: repo.currentRevision?.take(7)
        } catch (_: Throwable) {
            null
        }
    }
}
