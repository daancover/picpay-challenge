package com.picpay.desafio.data.network.interceptor

import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Response
import java.util.concurrent.TimeUnit.MINUTES

class CacheInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.proceed(chain.request())

        val cacheControl = CacheControl.Builder()
            .maxAge(CACHE_TIME, MINUTES)
            .build()

        return response.newBuilder()
            .removeHeader(HEADER_PRAGMA)
            .removeHeader(HEADER_CACHE_CONTROL)
            .header(HEADER_CACHE_CONTROL, cacheControl.toString())
            .build()
    }

    companion object {
        private const val CACHE_TIME = 2
        private const val HEADER_PRAGMA = "Pragma"
        private const val HEADER_CACHE_CONTROL = "Cache-Control"
    }
}