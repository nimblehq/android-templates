package co.nimblehq.sample.xml.model

import android.os.Parcelable
import co.nimblehq.sample.xml.domain.model.Model
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UiModel(
    val id: String?
) : Parcelable

private fun Model.toUiModel() = UiModel(id = id.toString())

fun List<Model>.toUiModels() = this.map { it.toUiModel() }
