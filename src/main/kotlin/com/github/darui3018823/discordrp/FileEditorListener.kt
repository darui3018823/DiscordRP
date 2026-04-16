package com.github.darui3018823.discordrp

import com.intellij.openapi.fileEditor.FileEditorManager
import com.intellij.openapi.fileEditor.FileEditorManagerEvent
import com.intellij.openapi.fileEditor.FileEditorManagerListener
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile

class FileEditorListener(private val project: Project) : FileEditorManagerListener {

    override fun selectionChanged(event: FileEditorManagerEvent) {
        val service = project.getService(ProjectRPService::class.java) ?: return
        val newFile = event.newFile
        if (newFile != null) {
            service.onFileOpened(newFile)
        } else {
            service.onFileClosed()
        }
    }

    override fun fileClosed(source: FileEditorManager, file: VirtualFile) {
        if (source.openFiles.isEmpty()) {
            project.getService(ProjectRPService::class.java)?.onFileClosed()
        }
    }
}
