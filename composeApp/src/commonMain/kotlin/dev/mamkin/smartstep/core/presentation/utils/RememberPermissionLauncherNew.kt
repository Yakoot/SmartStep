package dev.mamkin.smartstep.core.presentation.utils

import androidx.compose.runtime.Composable


// In the interface
interface NewPermissionLauncher{
    // The launch function no longer takes a callback.
    fun launch(permission: Permission)
}

// The composable function
@Composable
expect fun rememberNewPermissionLauncher(
    // It now takes the onResult callback here.
    onResult: (isGranted: Boolean) -> Unit
): NewPermissionLauncher