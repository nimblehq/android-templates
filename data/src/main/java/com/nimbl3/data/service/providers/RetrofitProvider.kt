package com.nimbl3.data.service.providers

import com.nimbl3.data.service.common.secrets.Secrets
import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory

class RetrofitProvider {
    companion object {
        fun getRetrofitBuilder(
            converterFactory: Converter.Factory,
            okHttpClient: OkHttpClient
        ): Retrofit.Builder {
            return Retrofit.Builder()
                .baseUrl(Secrets.apiEndpointUrl)
                .addConverterFactory(converterFactory)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
        }
    }
}
