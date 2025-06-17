package dev.kerscher.openlibraryandroid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            dev.kerscher.openlibraryandroid.TestAppTheme {
                dev.kerscher.openlibraryandroid.TestAppNavGraph()
            }
        }
    }
}