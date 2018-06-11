package com.nimbl3.data.service.providers

import com.google.gson.Gson
import retrofit2.Converter
import retrofit2.converter.gson.GsonConverterFactory

class ConverterFactoryProvider {
    companion object {
        fun getConverterFactoryProvider(gson: Gson): Converter.Factory {
            return GsonConverterFactory.create(gson)
        }
    }
}