package dev.mamkin.smartstep.core.presentation.utils


/**
 * Represents the detailed status of a runtime permission.
 * This is the language our shared code uses to understand the permission state.
 */
enum class PermissionStatus {
    /*** The user has granted the permission. The feature can be used.
     */
    GRANTED,

    /**
     * It's the first time the app is asking for this permission.
     * The UI should now trigger the system permission dialog.
     */
    NOT_DETERMINED,

    /**
     * The user has denied the permission at least once, but has NOT checked "Don't ask again".
     * The app can ask for the permission again, but it should show a rationale (an explanation) first.
     */
    DENIED,

    /**
     * The user has denied the permission and checked "Don't ask again".
     * The app CANNOT show the system dialog anymore. The user must go to the
     * app's system settings to grant the permission manually.
     */
    PERMANENTLY_DENIED
}