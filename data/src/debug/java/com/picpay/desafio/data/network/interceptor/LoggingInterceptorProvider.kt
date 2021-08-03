package com.picpay.desafio.data.network.interceptor

import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level.BODY

class LoggingInterceptorProvider : InterceptorProvider {

    override fun provide() = HttpLoggingInterceptor().apply {
        level = BODY
    }
}