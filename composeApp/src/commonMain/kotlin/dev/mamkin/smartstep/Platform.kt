package dev.mamkin.smartstep

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform