package dev.mamkin.smartstep.core.presentation.utils

import android.app.Activity
import java.lang.ref.WeakReference
object ActivityHolder {
    private var activity: WeakReference<Activity>? = null

    fun setActivity(activity: Activity) {
        this.activity = WeakReference(activity)
    }

    fun clearActivity(activity: Activity) {
        if (this.activity?.get() == activity) {
            this.activity?.clear()
            this.activity = null
        }
    }

    fun getActivity(): Activity? {
        return activity?.get()
    }
}