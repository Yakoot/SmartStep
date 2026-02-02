package dev.mamkin.smartstep.core.presentation.utils

typealias IsPermissionGranted = Boolean

interface PermissionChecker {
    fun isPermissionGranted(permission: Permission): IsPermissionGranted
}
