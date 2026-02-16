package dev.mamkin.smartstep.core.domain.service

import dev.mamkin.smartstep.core.domain.model.Permission
import kotlinx.coroutines.flow.Flow

typealias IsPermissionGranted = Boolean
typealias IsPermanentlyDenied = Boolean

interface PermissionManager {
    fun isPermissionGranted(permission: Permission): Flow<Pair<IsPermissionGranted, IsPermanentlyDenied>>
    suspend fun requestInitialPermission(permission: Permission)
    fun onRequestPermission(permission: Permission)
}