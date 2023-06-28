package co.nimblehq.sample.compose.test

import co.nimblehq.sample.compose.domain.model.Model

object MockUtil {

    val models = listOf(
        Model(
            id = 1,
            userName = "name1",
        ),
        Model(
            id = 2,
            userName = "name2",
        ),
        Model(
            id = 3,
            userName = "name3",
        ),
    )
}
