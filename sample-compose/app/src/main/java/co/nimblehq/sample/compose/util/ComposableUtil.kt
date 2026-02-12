package co.nimblehq.sample.compose.util

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@SuppressLint("ComposableNaming")
@Composable
fun StatusBarColor(
    color: Color,
    darkIcons: Boolean,
) {
    val systemUiController = rememberSystemUiController()
    LaunchedEffect(key1 = darkIcons) {
        systemUiController.setStatusBarColor(
            color = color,
            darkIcons = darkIcons,
        )
    }
}
