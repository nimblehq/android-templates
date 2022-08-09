package co.nimblehq.sample.compose.ui.screens.compose.composables

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import co.nimblehq.sample.compose.model.UiModel
import co.nimblehq.sample.compose.ui.screens.compose.theme.Dimension.SpacingNormal
import co.nimblehq.sample.compose.ui.screens.compose.theme.Theme
import timber.log.Timber

@Suppress("FunctionNaming")
@ExperimentalComposeUiApi
@Composable
fun HomeScreen(
    title: String,
    uiModels: List<UiModel>
) {
    Theme {
        Text(
            text = title,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .wrapContentHeight()
                .padding(all = SpacingNormal)
        )
    }
    Timber.d("Result : $uiModels")
}
