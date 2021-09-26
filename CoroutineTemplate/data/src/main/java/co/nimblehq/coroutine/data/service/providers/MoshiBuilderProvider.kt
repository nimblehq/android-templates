package co.nimblehq.coroutine.data.service.providers

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

object MoshiBuilderProvider {

    val moshiBuilder: Moshi.Builder
        get() = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
}
