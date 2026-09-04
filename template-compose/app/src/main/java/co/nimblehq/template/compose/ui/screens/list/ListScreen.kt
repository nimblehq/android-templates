package co.nimblehq.template.compose.ui.screens.list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import co.nimblehq.template.compose.R
import co.nimblehq.template.compose.common.BaseViewState
import co.nimblehq.template.compose.common.ErrorEvent
import co.nimblehq.template.compose.common.NavigateBackEvent
import co.nimblehq.template.compose.common.ui.BaseScreen
import co.nimblehq.template.compose.extensions.collectAsEffect
import co.nimblehq.template.compose.ui.screens.list.model.ListUiModel
import co.nimblehq.template.compose.ui.showToast
import co.nimblehq.template.compose.ui.theme.AppTheme.dimensions
import co.nimblehq.template.compose.ui.theme.ComposeTheme

@Composable
fun ListScreen(
    viewModel: ListViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit,
) = BaseScreen {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current

    viewModel.events.collectAsEffect { event ->
        when (event) {
            is NavigateBackEvent -> onNavigateBack()
            is ErrorEvent -> event.error.showToast(context)
        }
    }

    ListScreenContent(
        title = stringResource(id = R.string.app_name),
        state = state,
        onNavigateToHome = { viewModel.setIntent(ListIntent.NavigateBack) }
    )
}

@Composable
private fun ListScreenContent(
    title: String,
    state: BaseViewState<ListUiModel>,
    onNavigateToHome: () -> Unit,
) {
    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            text = title,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = dimensions.spacingMedium)
        )

        Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
            Button(
                onClick = onNavigateToHome,
            ) {
                Text(
                    text = stringResource(id = R.string.go_back),
                    textAlign = TextAlign.Center,
                )
            }
            when (state) {
                is BaseViewState.Initial -> Unit
                is BaseViewState.Loading -> ListLoadingIndicator()
                is BaseViewState.Loaded -> ListScreenContent(state.uiModel)
                is BaseViewState.Error -> state.uiModel?.let { ListContent(uiModel = it) }
            }
        }
    }
}

@Composable
private fun ListLoadingIndicator() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun ListScreenContent(uiModel: ListUiModel) {
    Column(modifier = Modifier.fillMaxSize()) {
        ListContent(uiModel)
    }
}

@Composable
private fun ListContent(uiModel: ListUiModel) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(items = uiModel.ids, key = { it }) { id ->
            Text(
                text = id.toString(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = dimensions.spacingMedium)
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun ListScreenPreview() {
    ComposeTheme {
        ListScreenContent(
            title = stringResource(id = R.string.app_name),
            state = BaseViewState.Loaded(
                uiModel = ListUiModel(ids = listOf(1, 2, 3))
            ),
            onNavigateToHome = {}
        )
    }
}
