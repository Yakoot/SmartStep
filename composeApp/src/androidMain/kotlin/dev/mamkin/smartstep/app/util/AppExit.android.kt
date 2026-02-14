package dev.mamkin.smartstep.app.util

import kotlin.system.exitProcess

actual fun requestAppExit() {
    exitProcess(0)
}