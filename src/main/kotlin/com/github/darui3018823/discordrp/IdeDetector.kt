package com.github.darui3018823.discordrp

import com.intellij.openapi.application.ApplicationInfo

object IdeDetector {

    fun getAssetKey(): String = when (ApplicationInfo.getInstance().build.productCode) {
        "IC", "IU" -> "idea"
        "WS" -> "ws"
        "PC", "PY" -> "pc"
        "GO" -> "go_land"
        "CL" -> "cl"
        "RD" -> "rd"
        "PS" -> "ps"
        "RM" -> "rm"
        "RR" -> "rr"
        "DG" -> "dg"
        "DS" -> "ds"
        "DC" -> "dc"
        "DM" -> "dm"
        "DP" -> "dp"
        "DT" -> "dt"
        "AI", "AS" -> "as"
        else -> "idea"
    }

    fun getDisplayName(): String = ApplicationInfo.getInstance().versionName

    fun getFullVersionString(): String =
        "${ApplicationInfo.getInstance().versionName} ${ApplicationInfo.getInstance().fullVersion}"
}
