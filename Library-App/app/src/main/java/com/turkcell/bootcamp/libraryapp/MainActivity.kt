package com.turkcell.bootcamp.libraryapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.turkcell.bootcamp.libraryapp.ui.navigation.NavGraph
import com.turkcell.bootcamp.libraryapp.ui.theme.LibraryAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LibraryAppTheme {
                NavGraph()
            }
        }
    }
}
