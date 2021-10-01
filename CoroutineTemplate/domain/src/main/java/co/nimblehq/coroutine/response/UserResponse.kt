package co.nimblehq.coroutine.response

import co.nimblehq.coroutine.model.User
import com.squareup.moshi.Json

data class UserResponse(
    @Json(name = "id") val id: Int?,
    @Json(name = "name") val name: String?,
    @Json(name = "username") val username: String?,
    @Json(name = "email") val email: String?,
    @Json(name = "address") val address: Address?,
    @Json(name = "phone") val phone: String?,
    @Json(name = "website") val website: String?
) {
    data class Address(
        @Json(name = "street") val street: String?,
        @Json(name = "suite") val suite: String?,
        @Json(name = "city") val city: String?,
        @Json(name = "zipcode") val zipCode: String?,
        @Json(name = "geo") val geo: Geo?
    ) {
        data class Geo(
            @Json(name = "lat") val latitude: String?,
            @Json(name = "lng") val longitude: String?
        )
    }
}

fun List<UserResponse>.toUserModel(): List<User> {
    return this.map { it.toModel() }
}

private fun UserResponse.toModel(): User {
    return User(
        id = this.id,
        name = this.name.orEmpty(),
        username = this.username.orEmpty(),
        email = this.email.orEmpty(),
        address = this.address?.toModel(),
        phone = this.phone.orEmpty(),
        website = this.website.orEmpty()
    )
}

private fun UserResponse.Address.toModel(): User.Address {
    return User.Address(
        street = this.street.orEmpty(),
        suite = this.suite.orEmpty(),
        city = this.city.orEmpty(),
        zipCode = this.zipCode.orEmpty(),
        geo = this.geo?.toModel()
    )
}

private fun UserResponse.Address.Geo.toModel(): User.Address.Geo {
    return User.Address.Geo(
        latitude = this.latitude.orEmpty(),
        longitude = this.longitude.orEmpty()
    )
}
