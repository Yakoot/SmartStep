package dev.mamkin.androidapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import dev.mamkin.smartstep.App
import dev.mamkin.smartstep.core.presentation.utils.ActivityHolder
import org.koin.android.ext.koin.androidContext

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()

        enableEdgeToEdge()
        super.onCreate(savedInstanceState)


        // This observer tells the ActivityHolder about the activity's lifecycle.
        val lifecycleObserver = object : DefaultLifecycleObserver {
            override fun onResume(owner: LifecycleOwner) {
                // When the activity is in the foreground, set it in the holder.
                ActivityHolder.setActivity(this@MainActivity)
            }
            override fun onPause(owner: LifecycleOwner) {
                // When the activity leaves the foreground, clear the reference.
                ActivityHolder.clearActivity(this@MainActivity)
            }
        }
        lifecycle.addObserver(lifecycleObserver)
        setContent {
            App(
                platformConfiguration = {
                    androidContext(this@MainActivity.applicationContext)
                }
            )
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App()
}