package co.nimblehq.coroutine.data.service.response

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
