package dev.mamkin.smartstep.core.presentation.utils


import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

actual class PermissionManager(private val context: Context,
                               private val dataStore: DataStore<Preferences>
) {

    actual suspend fun getPermissionStatus(permission: Permission): PermissionStatus {
        val manifestPermission = permission.toManifestPermissionCode()

        // 1. First, always check if the permission has already been granted.
        if (ContextCompat.checkSelfPermission(context, manifestPermission) == PackageManager.PERMISSION_GRANTED) {
            return PermissionStatus.GRANTED
        }

        // 2. Get the current foreground activity from our reliable holder.
        val activity = ActivityHolder.getActivity()
            ?: // If there is no active activity, we can't reliably check the status.
            // Defer the decision.
            return PermissionStatus.NOT_DETERMINED

        // 3. With a valid activity, we can now perform the detailed checks.
        val hasAskedBefore = hasAskedForPermissionBefore(manifestPermission)
        val shouldShowRationale = ActivityCompat.shouldShowRequestPermissionRationale(activity, manifestPermission)

        // 4. Use a clear `when` block to determine the precise status based on the flags.
        return when {
            // If the system says we should explain ourselves, it's a simple denial.
            shouldShowRationale -> PermissionStatus.DENIED

            // If we are NOT supposed to explain AND we HAVE asked before,
            // it means the user checked "Don't ask again". This is a permanent denial.
            !shouldShowRationale && hasAskedBefore -> PermissionStatus.PERMANENTLY_DENIED

            // If none of the above conditions are met, it must be the very first time
            // the app is attempting to ask for this permission.
            else -> PermissionStatus.NOT_DETERMINED
        }
    }

    // 3. This function ALSO must be a suspend function to write to DataStore
    actual suspend fun setPermissionRequested(permission: Permission) {
        val key = booleanPreferencesKey(permission.toManifestPermissionCode())
       dataStore.edit { prefs ->
            prefs[key] = true
        }
    }

    // 4. The helper is also a suspend function now
    private suspend fun hasAskedForPermissionBefore(manifestPermission: String): Boolean {
        val key = booleanPreferencesKey(manifestPermission)
        return dataStore.data.map { prefs ->
            prefs[key] ?: false
        }.first() // .first() collects the first value from the flow and returns it
    }

    private fun Context.findActivity(): Activity? = when (this) {
        is Activity -> this
        is ContextWrapper -> baseContext.findActivity()
        else -> null
    }
}