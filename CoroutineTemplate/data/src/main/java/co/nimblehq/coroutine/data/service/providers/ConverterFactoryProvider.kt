package co.nimblehq.coroutine.data.service.providers

import retrofit2.Converter
import retrofit2.converter.gson.GsonConverterFactory

object ConverterFactoryProvider {

    fun getGsonConverterFactory(): Converter.Factory {
        return GsonConverterFactory.create()
    }
}
