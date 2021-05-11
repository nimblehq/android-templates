package co.nimblehq.coroutine.domain.data.entity

import co.nimblehq.coroutine.data.service.response.UserResponse

data class UserEntity(
    val id: Int?,
    val name: String,
    val username: String,
    val email: String?,
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

fun List<UserResponse>.toUsersEntity(): List<UserEntity> {
    return this.map { it.toUser() }
}

fun UserResponse.toUser(): UserEntity {
    return UserEntity(
        id = id,
        name = name.orEmpty(),
        username = username.orEmpty(),
        email = email.orEmpty(),
        address = address?.toUserAddress(),
        phone = phone.orEmpty(),
        website = website.orEmpty()
    )
}

fun UserResponse.Address.toUserAddress(): UserEntity.Address {
    return UserEntity.Address(
        street = street.orEmpty(),
        suite = suite.orEmpty(),
        city = city.orEmpty(),
        zipCode = zipCode.orEmpty(),
        geo = geo?.toUserAddressGeo()
    )
}

fun UserResponse.Address.Geo.toUserAddressGeo(): UserEntity.Address.Geo {
    return UserEntity.Address.Geo(
        latitude = latitude.orEmpty(),
        longitude = longitude.orEmpty()
    )
}
