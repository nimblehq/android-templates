package co.nimblehq.rxjava.domain.data

import android.os.Parcelable
import co.nimblehq.rxjava.data.service.response.ExampleResponse
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Data(
    val content: String,
    val imageUrl: String
) : Parcelable

fun ExampleResponse.toData(): Data {
    var content = ""
    (0..2)
        .map { data.children[it].data }
        .forEach {
            content += "Author = ${it.author} \nTitle = ${it.title} \n\n"
        }

    // Image from a random place
    val imageUrl = "http://www.monkeyuser.com/assets/images/2018/80-the-struggle.png"
    return Data(content, imageUrl)
}
