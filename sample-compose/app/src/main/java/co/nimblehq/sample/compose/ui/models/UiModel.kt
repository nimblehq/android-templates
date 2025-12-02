package co.nimblehq.sample.compose.ui.models

import android.os.Parcelable
import co.nimblehq.sample.compose.domain.models.Model
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class UiModel(
    val id: String,
    val username: String,
) : Parcelable

fun Model.toUiModel() = UiModel(
    id.toString(),
    username.orEmpty(),
)
