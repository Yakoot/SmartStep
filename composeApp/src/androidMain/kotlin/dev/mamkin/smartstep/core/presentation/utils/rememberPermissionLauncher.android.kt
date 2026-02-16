package dev.mamkin.smartstep.core.presentation.utils

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

@Composable
actual fun rememberPermissionLauncher(permission: Permission): PermissionLauncher {
    val context = LocalContext.current

    var onPermissionResult by remember { mutableStateOf<(Boolean) -> Unit>({}) }

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        onPermissionResult(isGranted)
    }

    return remember {
        object : PermissionLauncher {
            override fun launch(onPermission: (Boolean) -> Unit) {
                val activity = context.findActivity() ?: return
                val manifestPermission = permission.toManifestPermissionCode()

                onPermissionResult = onPermission

                when {
                    ContextCompat.checkSelfPermission(
                        context,
                        manifestPermission
                    ) == PackageManager.PERMISSION_GRANTED -> {
                        onPermission(true)
                    }

                    ActivityCompat.shouldShowRequestPermissionRationale(activity, manifestPermission) -> {
                        permissionLauncher.launch(manifestPermission)
                    }

                    else -> {
                        permissionLauncher.launch(manifestPermission)
                    }
                }
            }
        }
    }
}

private fun Context.findActivity(): Activity? = when (this) {
    is Activity -> this
    is ContextWrapper -> baseContext.findActivity()
    else -> null
}

private fun openAppSettings(context: Context) {
    val intent = Intent(
        Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
        Uri.fromParts("package", context.packageName, null)
    )
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    context.startActivity(intent)
}