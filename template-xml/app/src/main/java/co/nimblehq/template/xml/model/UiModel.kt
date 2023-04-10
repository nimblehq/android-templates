package co.nimblehq.template.xml.model

import co.nimblehq.template.xml.domain.model.Model

data class UiModel(
    val id: Int
)

fun Model.toUiModel() = UiModel(id = id ?: -1)
