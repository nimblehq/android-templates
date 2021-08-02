package co.nimblehq.coroutine.ui.screens.compose.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import co.nimblehq.coroutine.R
import co.nimblehq.coroutine.domain.data.entity.UserEntity
import co.nimblehq.coroutine.ui.screens.compose.theme.Dimen

@Suppress("LongMethod")
@Composable
fun UserItem(
    user: UserEntity,
    onClick: (String) -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = { onClick(user.toString()) })
    ) {
        Row(modifier = Modifier.padding(Dimen.Dp16)) {
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_foreground),
                contentDescription = null,
                modifier = Modifier
                    .width(Dimen.Dp52)
                    .height(Dimen.Dp52),
            )
            Column(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(start = Dimen.Dp16)
            ) {
                with(user) {
                    Text(
                        text = name,
                        style = MaterialTheme.typography.subtitle1
                    )
                    Text(
                        text = phone,
                        style = MaterialTheme.typography.body1,
                        modifier = Modifier.padding(top = Dimen.Dp4)
                    )
                }
            }
        }
    }
}
