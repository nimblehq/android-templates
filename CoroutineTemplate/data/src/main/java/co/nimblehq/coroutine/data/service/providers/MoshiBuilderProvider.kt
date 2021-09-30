package co.nimblehq.coroutine.data.service.providers

import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import java.util.*

object MoshiBuilderProvider {

    val moshiBuilder: Moshi.Builder
        get() = Moshi.Builder()
            // Parse the DateTime in this format: [yyyy-MM-ddThh:mm:ss.ssZ]
            // e.g: [2019-10-12T07:20:50.52Z]
            .add(Date::class.java, Rfc3339DateJsonAdapter())
            .add(KotlinJsonAdapterFactory())
}
