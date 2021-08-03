package com.picpay.desafio.android.di

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level.BODY
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "http://127.0.0.1:8080/"

val testNetworkModule = module(override = true) {
    single {
        provideOkHttpClient()
    }

    single {
        provideRetrofit(
            okHttpClient = get()
        )
    }
}

private fun provideOkHttpClient(): OkHttpClient {
    return OkHttpClient().newBuilder()
        .addInterceptor(
            HttpLoggingInterceptor().apply {
                level = BODY
            }
        ).build()
}

private fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
    val gson = GsonBuilder()
        .setLenient()
        .create()

    return Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()
}