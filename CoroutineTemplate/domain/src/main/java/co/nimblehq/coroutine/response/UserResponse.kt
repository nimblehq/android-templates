package co.nimblehq.coroutine.response

import co.nimblehq.coroutine.model.User
import com.squareup.moshi.Json

data class UserResponse(
    @Json(name = "id") val id: Int?,
    @Json(name = "name") val name: String?,
    @Json(name = "username") val username: String?,
    @Json(name = "email") val email: String?,
    @Json(name = "address") val addressResponse: AddressResponse?,
    @Json(name = "phone") val phone: String?,
    @Json(name = "website") val website: String?
) {
    data class AddressResponse(
        @Json(name = "street") val street: String?,
        @Json(name = "suite") val suite: String?,
        @Json(name = "city") val city: String?,
        @Json(name = "zipcode") val zipCode: String?,
        @Json(name = "geo") val geoResponse: GeoResponse?
    ) {
        data class GeoResponse(
            @Json(name = "lat") val latitude: String?,
            @Json(name = "lng") val longitude: String?
        )
    }
}

fun List<UserResponse>.toUser(): List<User> {
    return this.map { it.toUser() }
}

private fun UserResponse.toUser(): User {
    return User(
        id = this.id,
        name = this.name.orEmpty(),
        username = this.username.orEmpty(),
        email = this.email.orEmpty(),
        address = this.addressResponse?.toAddress(),
        phone = this.phone.orEmpty(),
        website = this.website.orEmpty()
    )
}

private fun UserResponse.AddressResponse.toAddress(): User.Address {
    return User.Address(
        street = this.street.orEmpty(),
        suite = this.suite.orEmpty(),
        city = this.city.orEmpty(),
        zipCode = this.zipCode.orEmpty(),
        geo = this.geoResponse?.toGeo()
    )
}

private fun UserResponse.AddressResponse.GeoResponse.toGeo(): User.Address.Geo {
    return User.Address.Geo(
        latitude = this.latitude.orEmpty(),
        longitude = this.longitude.orEmpty()
    )
}
