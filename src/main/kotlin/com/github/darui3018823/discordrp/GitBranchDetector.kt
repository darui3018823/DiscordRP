package com.github.darui3018823.discordrp

import com.intellij.openapi.project.Project

object GitBranchDetector {

    fun getBranch(project: Project): String? {
        return try {
            val manager = git4idea.repo.GitRepositoryManager.getInstance(project)
            val repo = manager.repositories.firstOrNull() ?: return null
            repo.currentBranch?.name ?: repo.currentRevision?.take(7)
        } catch (_: Throwable) {
            null
        }
    }
}
