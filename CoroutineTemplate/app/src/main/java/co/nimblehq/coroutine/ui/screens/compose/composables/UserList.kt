package co.nimblehq.coroutine.ui.screens.compose.composables

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import co.nimblehq.coroutine.model.UserUiModel
import co.nimblehq.coroutine.ui.screens.compose.theme.Dimension

@Composable
fun UserList(
    userUiModels: List<UserUiModel>,
    onUserItemClick: (String) -> Unit
) {
    LazyColumn {
        itemsIndexed(items = userUiModels) { index, user ->
            if (index == 0) Spacer(modifier = Modifier.height(Dimension.Dp8))
            UserItem(
                userUiModel = user,
                onClick = onUserItemClick
            )
            if (index != userUiModels.size - 1) Divider()
        }
    }
}
