package co.nimblehq.rxjava.data.service.common.secrets

object Secrets {
    val apiEndpointUrl: String
        get() = ApiEndpointUrlImpl().value

    val clientId: String
        get() = ClientIdImpl().value

    val clientSecret: String
        get() = ClientSecretImpl().value
}
