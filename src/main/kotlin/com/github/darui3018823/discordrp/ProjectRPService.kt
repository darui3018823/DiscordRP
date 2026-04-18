package com.github.darui3018823.discordrp

import com.intellij.openapi.application.ReadAction
import com.intellij.openapi.components.Service
import com.intellij.openapi.project.Project
import com.intellij.util.concurrency.AppExecutorUtil
import com.intellij.openapi.vfs.VirtualFile
import org.json.JSONObject
import java.time.Instant

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
            val branch = ReadAction.compute<String?, Throwable> { GitBranchDetector.getBranch(project) }
            val state = if (branch != null) "$projectName at $branch branch" else projectName

            val timestamps = JSONObject().put("start", Instant.now().epochSecond)

            val activity = JSONObject().put("state", state).put("timestamps", timestamps)

            val file = currentFile
            if (file != null) {
                val langKey = ReadAction.compute<String?, Throwable> { LanguageDetector.getAssetKey(file) }
                val langName = ReadAction.compute<String, Throwable> { LanguageDetector.getDisplayName(file) }
                val effectiveLangKey = langKey ?: "text"

                activity.put("details", "Editing ${file.name}")
                activity.put("assets", JSONObject()
                    .put("large_image", effectiveLangKey)
                    .put("large_text", "Editing $langName File")
                    .put("small_image", ideKey)
                    .put("small_text", fullVersion))
            } else {
                activity.put("assets", JSONObject()
                    .put("large_image", ideKey)
                    .put("large_text", fullVersion))
            }

            DiscordRPService.getInstance().updatePresence(activity)
        }
    }
}
