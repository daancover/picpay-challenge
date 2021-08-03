package com.picpay.desafio.di

import android.app.Application
import com.google.gson.GsonBuilder
import com.picpay.desafio.data.network.interceptor.CacheInterceptor
import com.picpay.desafio.data.network.interceptor.LoggingInterceptorProvider
import com.picpay.desafio.data.network.service.PicPayService
import okhttp3.Cache
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit.SECONDS

private const val TIMEOUT = 30L
private const val BASE_URL = "https://609a908e0f5a13001721b74e.mockapi.io/picpay/api/"
private const val CACHE_SIZE = 5L * 1024 * 1024 // 5MB
const val FILE_NAME = "eQxBiQwh"

@Suppress("USELESS_CAST")
val networkModule = module {

    single {
        provideCacheControl(androidApplication())
    }

    single {
        provideOkHttpClient(
            cache = get()
        )
    }

    single {
        provideRetrofit(
            okHttpClient = get()
        )
    }

    single {
        providePicPayService(
            retrofit = get()
        )
    }
}

private fun provideCacheControl(application: Application) = Cache(
    File(application.filesDir, FILE_NAME),
    CACHE_SIZE
)

private fun provideOkHttpClient(cache: Cache): OkHttpClient {
    val builder = OkHttpClient().newBuilder()

    with(builder) {
        @Suppress("UNNECESSARY_SAFE_CALL")
        LoggingInterceptorProvider().provide()?.let {
            addInterceptor(it)
        }

        cache(cache)
        addNetworkInterceptor(CacheInterceptor())
        connectTimeout(TIMEOUT, SECONDS)
        readTimeout(TIMEOUT, SECONDS)
        writeTimeout(TIMEOUT, SECONDS)

        return build()
    }
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

private fun providePicPayService(retrofit: Retrofit): PicPayService =
    retrofit.create(PicPayService::class.java)