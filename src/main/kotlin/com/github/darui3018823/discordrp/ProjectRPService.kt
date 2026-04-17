package com.github.darui3018823.discordrp

import com.intellij.openapi.application.ReadAction
import com.intellij.openapi.components.Service
import com.intellij.openapi.project.Project
import com.intellij.util.concurrency.AppExecutorUtil
import com.intellij.openapi.vfs.VirtualFile
import com.jagrosh.discordipc.entities.RichPresence

@Service(Service.Level.PROJECT)
class ProjectRPService(private val project: Project) {

    @Volatile
    var currentFile: VirtualFile? = null

    fun onFileOpened(file: VirtualFile) {
        currentFile = file
        updateRichPresence()
    }

    fun onFileClosed() {
        currentFile = null
        updateRichPresence()
    }

    fun updateRichPresence() {
        AppExecutorUtil.getAppExecutorService().submit {
            val ideKey = IdeDetector.getAssetKey()
            val fullVersion = IdeDetector.getFullVersionString()
            val projectName = project.name
            val branch = GitBranchDetector.getBranch(project)
            val state = if (branch != null) "$projectName at $branch branch" else projectName

            val file = currentFile
            val builder = if (file != null) {
                val langKey = ReadAction.compute<String?, Throwable> {
                    LanguageDetector.getAssetKey(file, project)
                }
                val langName = ReadAction.compute<String, Throwable> {
                    LanguageDetector.getDisplayName(file, project)
                }

                if (langKey != null) {
                    RichPresence.Builder()
                        .setDetails("Editing ${file.name}")
                        .setState(state)
                        .setLargeImage(langKey, "Editing $langName File")
                        .setSmallImage(ideKey, fullVersion)
                } else {
                    RichPresence.Builder()
                        .setDetails("Editing ${file.name}")
                        .setState(state)
                        .setLargeImage(ideKey, fullVersion)
                        .setSmallImage(ideKey, fullVersion)
                }
            } else {
                RichPresence.Builder()
                    .setState(state)
                    .setLargeImage(ideKey, fullVersion)
            }

            DiscordRPService.getInstance().updatePresence(builder)
        }
    }
}
