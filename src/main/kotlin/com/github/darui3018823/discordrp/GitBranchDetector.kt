package com.github.darui3018823.discordrp

import com.intellij.openapi.project.Project

object GitBranchDetector {
    private const val SHORT_HASH_LENGTH = 7

    fun getBranch(project: Project): String? {
        return try {
            val manager = git4idea.repo.GitRepositoryManager.getInstance(project)
            val repo = manager.repositories.firstOrNull() ?: return null
            repo.currentBranch?.name ?: repo.currentRevision?.take(SHORT_HASH_LENGTH)
        } catch (_: Throwable) {
            null
        }
    }
}
