package co.nimblehq.template.compose.data.test

import co.nimblehq.template.compose.data.remote.models.responses.ErrorResponse
import co.nimblehq.template.compose.data.remote.models.responses.OAuthTokenResponse
import co.nimblehq.template.compose.domain.models.AuthStatus
import co.nimblehq.template.compose.domain.models.OAuthTokenModel
import io.mockk.every
import io.mockk.mockk
import okhttp3.ResponseBody
import retrofit2.HttpException
import retrofit2.Response

object MockUtil {

    val mockHttpException: HttpException
        get() {
            val response = mockk<Response<Any>>()
            val httpException = mockk<HttpException>()
            val responseBody = mockk<ResponseBody>()
            every { response.code() } returns 500
            every { response.message() } returns "message"
            every { response.errorBody() } returns responseBody
            every { httpException.code() } returns response.code()
            every { httpException.message() } returns response.message()
            every { httpException.response() } returns response
            every { responseBody.string() } returns "{\n" +
                    "  \"message\": \"message\"\n" +
                    "}"
            return httpException
        }

    val errorResponse = ErrorResponse(
        message = "message"
    )

    val responses = listOf(
        co.nimblehq.template.compose.data.remote.models.responses.Response(id = 1)
    )

    val oauthTokenResponse = OAuthTokenResponse(
        accessToken = "accessToken",
        tokenType = "tokenType",
        expiresIn = null,
        refreshToken = "refreshToken",
    )

    val oAuthTokenModel = OAuthTokenModel(
        accessToken = "accessToken",
        tokenType = "tokenType",
        expiresIn = null,
        refreshToken = "refreshToken",
    )

    val authenticatedStatus = AuthStatus.Authenticated(
        accessToken = "accessToken",
        tokenType = "tokenType",
        expiresIn = 10,
        refreshToken = "refreshToken"
    )
}
