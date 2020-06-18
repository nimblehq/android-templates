package co.nimblehq.data.service.common.secrets

interface ClientSecret {
    companion object {
        // TODO: modify for your usage
        const val ID = "client_secret"
    }
    val value: String
}
