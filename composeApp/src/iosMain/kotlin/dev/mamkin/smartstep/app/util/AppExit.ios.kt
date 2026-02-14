package dev.mamkin.smartstep.app.util

import platform.posix.exit

actual fun requestAppExit() {
    exit(0)
}