package co.nimblehq.template.compose.ui.models

import co.nimblehq.template.compose.domain.models.Model

data class UiModel(
    val id: Int,
)

fun Model.toUiModel() = UiModel(id = id ?: -1)
