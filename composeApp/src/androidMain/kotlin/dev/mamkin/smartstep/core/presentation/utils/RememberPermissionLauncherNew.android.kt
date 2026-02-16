package dev.mamkin.smartstep.core.presentation.utils

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember

@Composable
actual fun rememberNewPermissionLauncher(

    onResult: (isGranted: Boolean) -> Unit
): NewPermissionLauncher {

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = onResult
    )

    return remember {
        object : NewPermissionLauncher {

            override fun launch(permission: Permission) {
                launcher.launch(permission.toManifestPermissionCode())
            }
        }
    }
}