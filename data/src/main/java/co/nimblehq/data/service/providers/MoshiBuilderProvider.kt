package co.nimblehq.data.service.providers

import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import java.util.*

object MoshiBuilderProvider {

    val moshiBuilder: Moshi.Builder
        get() {
            return Moshi.Builder()
                .add(Date::class.java, Rfc3339DateJsonAdapter())
                .add(KotlinJsonAdapterFactory())
        }
}
