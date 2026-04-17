package com.github.darui3018823.discordrp

import com.intellij.ide.plugins.PluginManagerCore
import com.intellij.openapi.extensions.PluginId
import com.intellij.openapi.project.Project
import com.intellij.openapi.project.guessProjectDir

object GitBranchDetector {

    fun getBranch(project: Project): String? {
        return try {
            if (!PluginManagerCore.isPluginInstalled(PluginId.getId("Git4Idea"))) return null

            val projectDir = project.guessProjectDir() ?: return null
            val manager = git4idea.repo.GitRepositoryManager.getInstance(project)
            val repo = manager.getRepositoryForFile(projectDir) ?: return null

            repo.currentBranch?.name ?: repo.currentRevision?.take(7)
        } catch (_: Throwable) {
            null
        }
    }
}
