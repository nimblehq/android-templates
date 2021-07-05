package co.nimblehq.coroutine.ui.screens.compose.composables

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import co.nimblehq.coroutine.domain.data.entity.UserEntity

@Suppress("MagicNumber")
@Composable
fun UserList(
    users: List<UserEntity>,
    onUserItemClick: (String) -> Unit
) {
    LazyColumn {
        itemsIndexed(items = users) { index, user ->
            if (index == 0) Spacer(modifier = Modifier.height(8.dp))
            UserItem(
                user = user,
                onClick = onUserItemClick
            )
            if (index != users.size - 1) Divider()
        }
    }
}
