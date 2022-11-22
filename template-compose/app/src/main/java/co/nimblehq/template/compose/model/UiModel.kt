package co.nimblehq.template.compose.model

import co.nimblehq.template.compose.domain.model.Model

data class UiModel(
    val id: Int
)

private fun Model.toUiModel() = UiModel(id = id ?: -1)

fun List<Model>.toUiModels() = this.map { it.toUiModel() }
