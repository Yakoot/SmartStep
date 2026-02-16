package dev.mamkin.smartstep.core.presentation.utils


expect class PermissionManager {
   suspend fun getPermissionStatus(permission: Permission): PermissionStatus
   suspend fun setPermissionRequested(permission: Permission)

}
