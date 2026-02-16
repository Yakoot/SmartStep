package dev.mamkin.smartstep.core.data.util

import android.Manifest
import dev.mamkin.smartstep.core.domain.model.Permission

fun Permission.toManifestPermission(): String {
    return when (this) {
        Permission.RUN -> Manifest.permission.ACTIVITY_RECOGNITION
    }
}