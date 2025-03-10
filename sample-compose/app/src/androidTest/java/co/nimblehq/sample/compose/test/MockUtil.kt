package co.nimblehq.sample.compose.test

import co.nimblehq.sample.compose.domain.models.Model

object MockUtil {

    val models = listOf(
        Model(
            id = 1,
            username = "name1",
        ),
        Model(
            id = 2,
            username = "name2",
        ),
        Model(
            id = 3,
            username = "name3",
        ),
    )
}
