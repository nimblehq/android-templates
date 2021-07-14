package co.nimblehq.coroutine.domain.data.entity

import android.os.Parcelable
import co.nimblehq.coroutine.data.service.response.UserResponse
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserEntity(
    val id: Int?,
    val name: String,
    val username: String,
    val email: String?,
    val address: Address?,
    val phone: String,
    val website: String
) : Parcelable {

    @Parcelize
    data class Address(
        val street: String,
        val suite: String,
        val city: String,
        val zipCode: String,
        val geo: Geo?
    ) : Parcelable {

        @Parcelize
        data class Geo(
            val latitude: String,
            val longitude: String
        ) : Parcelable
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
