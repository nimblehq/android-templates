package co.nimblehq.coroutine.domain.model

data class User(
    val id: Int?,
    val name: String,
    val username: String,
    val email: String,
    val address: Address?,
    val phone: String,
    val website: String
) {

    data class Address(
        val street: String,
        val suite: String,
        val city: String,
        val zipCode: String,
        val geo: Geo?
    ) {

        data class Geo(
            val latitude: String,
            val longitude: String
        )
    }
}
