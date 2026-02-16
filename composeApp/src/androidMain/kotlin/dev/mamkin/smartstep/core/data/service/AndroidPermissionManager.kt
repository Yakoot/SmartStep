package dev.mamkin.smartstep.core.data.service

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import dev.mamkin.smartstep.core.data.util.toManifestPermission
import dev.mamkin.smartstep.core.domain.model.Permission
import dev.mamkin.smartstep.core.domain.service.IsPermanentlyDenied
import dev.mamkin.smartstep.core.domain.service.IsPermissionGranted
import dev.mamkin.smartstep.core.domain.service.PermissionManager
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume


class AndroidPermissionManager(
    private val activity: ComponentActivity
) : PermissionManager {

    private val permissionState =
        MutableStateFlow(checkPermissionState(Permission.RUN))

    private val permissionLauncher = activity.activityResultRegistry.register(
        "permission_request",
        ActivityResultContracts.RequestPermission()
    ) {
        permissionState.value = checkPermissionState(Permission.RUN)
    }

    override fun isPermissionGranted(
        permission: Permission
    ): Flow<Pair<IsPermissionGranted, IsPermanentlyDenied>> =
        callbackFlow {
            val observer = LifecycleEventObserver { _, event ->
                if (event == Lifecycle.Event.ON_RESUME) {
                    permissionState.value = checkPermissionState(permission)
                }
            }
            activity.lifecycle.addObserver(observer)

            permissionState
                .onStart { permissionState.value = checkPermissionState(permission) }
                .collect { trySend(it) }

            awaitClose { activity.lifecycle.removeObserver(observer) }
        }

    override suspend fun requestInitialPermission(permission: Permission) {
        val manifestPermission = permission.toManifestPermission()

        val isGranted = ContextCompat.checkSelfPermission(
            activity,
            manifestPermission
        ) == PackageManager.PERMISSION_GRANTED

        if (!isGranted) {
            suspendCancellableCoroutine { continuation ->
                val launcher = activity.activityResultRegistry.register(
                    "initial_permission_request",
                    ActivityResultContracts.RequestPermission()
                ) {
                    permissionState.value = checkPermissionState(permission)
                    continuation.resume(Unit)
                }

                continuation.invokeOnCancellation { launcher.unregister() }

                launcher.launch(manifestPermission)
            }
        }
    }

    override fun onRequestPermission(permission: Permission) {
        val manifestPermission = permission.toManifestPermission()

        val isGranted = ContextCompat.checkSelfPermission(
            activity,
            manifestPermission
        ) == PackageManager.PERMISSION_GRANTED

        if (isGranted) return

        val shouldShowRationale = ActivityCompat.shouldShowRequestPermissionRationale(
            activity,
            manifestPermission
        )

        if (shouldShowRationale) {
            permissionLauncher.launch(manifestPermission)
        } else {
            openAppSettings()
        }
    }

    private fun checkPermissionState(
        permission: Permission
    ): Pair<IsPermissionGranted, IsPermanentlyDenied> {
        val manifestPermission = permission.toManifestPermission()

        val isGranted = ContextCompat.checkSelfPermission(
            activity,
            manifestPermission
        ) == PackageManager.PERMISSION_GRANTED

        val shouldShowRationale = ActivityCompat.shouldShowRequestPermissionRationale(
            activity,
            manifestPermission
        )

        val isPermanentlyDenied = !isGranted && !shouldShowRationale

        return isGranted to isPermanentlyDenied
    }

    private fun openAppSettings() {
        val intent = Intent(
            Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
            Uri.fromParts("package", activity.packageName, null)
        )
        activity.startActivity(intent)
    }
}
