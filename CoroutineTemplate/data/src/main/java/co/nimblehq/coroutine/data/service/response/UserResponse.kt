package co.nimblehq.coroutine.data.service.response

import co.nimblehq.coroutine.entity.UserEntity
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

fun List<UserResponse>.toUserEntities(): List<UserEntity> {
    return this.map { it.toEntity() }
}

private fun UserResponse.toEntity(): UserEntity {
    return UserEntity(
        id = this.id,
        name = this.name.orEmpty(),
        username = this.username.orEmpty(),
        email = this.email.orEmpty(),
        address = this.address?.toEntity(),
        phone = this.phone.orEmpty(),
        website = this.website.orEmpty()
    )
}

private fun UserResponse.Address.toEntity(): UserEntity.Address {
    return UserEntity.Address(
        street = this.street.orEmpty(),
        suite = this.suite.orEmpty(),
        city = this.city.orEmpty(),
        zipCode = this.zipCode.orEmpty(),
        geo = this.geo?.toEntity()
    )
}

private fun UserResponse.Address.Geo.toEntity(): UserEntity.Address.Geo {
    return UserEntity.Address.Geo(
        latitude = this.latitude.orEmpty(),
        longitude = this.longitude.orEmpty()
    )
}
