package co.nimblehq.sample.xml.model

import android.os.Parcelable
import co.nimblehq.sample.xml.domain.model.Model
import kotlinx.parcelize.Parcelize

@Parcelize
data class UiModel(
    val id: String
) : Parcelable

fun Model.toUiModel() = UiModel(id = id.toString())
