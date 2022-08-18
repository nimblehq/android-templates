package co.nimblehq.sample.compose.model

import android.os.Parcelable
import co.nimblehq.sample.compose.domain.model.Model
import kotlinx.parcelize.Parcelize

@Parcelize
data class UiModel(
    val id: Int
) : Parcelable

private fun Model.toUiModel() = UiModel(id = id ?: -1)

fun List<Model>.toUiModels() = this.map { it.toUiModel() }
