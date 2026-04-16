package com.github.darui3018823.discordrp

import com.intellij.lang.LanguageUtil
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile

object LanguageDetector {

    private val languageIdToKey = mapOf(
        "kotlin" to "lang_kotlin",
        "JAVA" to "lang_java",
        "JavaScript" to "lang_js",
        "TypeScript" to "lang_ts",
        "Python" to "lang_python",
        "go" to "lang_go",
        "ObjectiveC" to "lang_c",
        "CPP" to "lang_cpp",
        "C#" to "lang_csharp",
        "PHP" to "lang_php",
        "Ruby" to "lang_ruby",
        "RUST" to "lang_rust",
        "SQL" to "lang_sql",
        "HTML" to "lang_html",
        "CSS" to "lang_css",
        "Markdown" to "lang_md",
        "JSON" to "lang_json",
        "yaml" to "lang_yaml",
        "Shell Script" to "lang_shell",
        "bash" to "lang_shell",
        "zsh" to "lang_shell",
        "PowerShell" to "lang_shell",
        "XML" to "lang_xml",
        "Dockerfile" to "lang_docker",
    )

    private val fileTypeNameToKey = mapOf(
        "Kotlin" to "lang_kotlin",
        "JAVA" to "lang_java",
        "JavaScript" to "lang_js",
        "TypeScript" to "lang_ts",
        "Python" to "lang_python",
        "Go" to "lang_go",
        "C" to "lang_c",
        "C++" to "lang_cpp",
        "C#" to "lang_csharp",
        "PHP" to "lang_php",
        "Ruby" to "lang_ruby",
        "Rust" to "lang_rust",
        "SQL" to "lang_sql",
        "HTML" to "lang_html",
        "CSS" to "lang_css",
        "Markdown" to "lang_md",
        "JSON" to "lang_json",
        "YAML" to "lang_yaml",
        "Shell Script" to "lang_shell",
        "XML" to "lang_xml",
        "Dockerfile" to "lang_docker",
    )

    private val extensionToKey = mapOf(
        "kt" to "lang_kotlin",
        "kts" to "lang_kotlin",
        "java" to "lang_java",
        "js" to "lang_js",
        "mjs" to "lang_js",
        "cjs" to "lang_js",
        "ts" to "lang_ts",
        "tsx" to "lang_ts",
        "jsx" to "lang_js",
        "py" to "lang_python",
        "go" to "lang_go",
        "c" to "lang_c",
        "h" to "lang_c",
        "cpp" to "lang_cpp",
        "cxx" to "lang_cpp",
        "cc" to "lang_cpp",
        "hpp" to "lang_cpp",
        "cs" to "lang_csharp",
        "php" to "lang_php",
        "rb" to "lang_ruby",
        "rs" to "lang_rust",
        "sql" to "lang_sql",
        "html" to "lang_html",
        "htm" to "lang_html",
        "css" to "lang_css",
        "scss" to "lang_css",
        "sass" to "lang_css",
        "md" to "lang_md",
        "markdown" to "lang_md",
        "json" to "lang_json",
        "yaml" to "lang_yaml",
        "yml" to "lang_yaml",
        "sh" to "lang_shell",
        "zsh" to "lang_shell",
        "bash" to "lang_shell",
        "cmd" to "lang_shell",
        "bat" to "lang_shell",
        "ps1" to "lang_shell",
        "xml" to "lang_xml",
        "dockerfile" to "lang_docker",
    )

    fun getAssetKey(file: VirtualFile, project: Project): String? {
        val language = LanguageUtil.getFileLanguage(file)
        if (language != null) {
            languageIdToKey[language.id]?.let { return it }
        }

        fileTypeNameToKey[file.fileType.name]?.let { return it }

        val ext = file.extension?.lowercase()
        if (ext != null) {
            extensionToKey[ext]?.let { return it }
        }
        if (file.name.lowercase() == "dockerfile") {
            return "lang_docker"
        }

        return null
    }

    fun getDisplayName(file: VirtualFile, project: Project): String {
        val language = LanguageUtil.getFileLanguage(file)
        if (language != null) return language.displayName
        return file.fileType.description.ifBlank { file.fileType.name }
    }
}
