package co.nimblehq.template.compose.domain.test

import co.nimblehq.template.compose.domain.models.Model
import co.nimblehq.template.compose.domain.models.OAuthTokenModel

object MockUtil {

    val models = listOf(
        Model(id = 1)
    )

    val oAuthTokenModel = OAuthTokenModel(
        accessToken = "accessToken",
        expiresIn = null,
        refreshToken = "refreshToken",
        tokenType = "tokenType",
    )
}
