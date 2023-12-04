package com.yilmaz.stackapiapp.presentation.secreens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.yilmaz.stackapiapp.presentation.navigation.SetUpNavGraph
import com.yilmaz.stackapiapp.presentation.ui.theme.StackApiAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            StackApiAppTheme{
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    SetStatusbarItemsColor()
                    SetUpNavGraph(navHostController = rememberNavController())
                }
            }
        }
    }
}

@Composable
fun SetStatusbarItemsColor() {
    rememberSystemUiController().also { systemUiController ->
        if (!isSystemInDarkTheme())
            SideEffect { systemUiController.statusBarDarkContentEnabled = true }
        else
            SideEffect { systemUiController.statusBarDarkContentEnabled = false }
    }
}