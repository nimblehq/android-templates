package co.nimblehq.sample.compose.ui.models

import co.nimblehq.sample.compose.domain.models.Model
import kotlinx.serialization.Serializable

@Serializable
data class UiModel(
    val id: String,
    val username: String,
)

fun Model.toUiModel() = UiModel(
    id.toString(),
    username.orEmpty(),
)
