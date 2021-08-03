package com.picpay.desafio.data.network.interceptor

import okhttp3.Interceptor

interface InterceptorProvider {

    fun provide(): Interceptor?
}