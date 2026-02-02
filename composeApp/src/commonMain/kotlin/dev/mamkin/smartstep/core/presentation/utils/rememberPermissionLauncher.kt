package dev.mamkin.smartstep.core.presentation.utils

import androidx.compose.runtime.Composable

@Composable
expect fun rememberPermissionLauncher(permission: Permission): PermissionLauncher

interface PermissionLauncher {
    fun launch(
        onPermission: (IsPermissionGranted) -> Unit
    )
}