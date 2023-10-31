package co.nimblehq.rxjava.data.service.common.secrets

class ClientSecretImpl : ClientSecret {
    override val value: String
        // TODO: modify for your usage
        get() = "staging secret keys"
}
