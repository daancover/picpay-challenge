package com.picpay.desafio.di

import com.picpay.desafio.data.error.NetworkErrorHandler
import com.picpay.desafio.domain.error.ErrorHandler
import org.koin.dsl.module

@Suppress("USELESS_CAST")
val errorModule = module {

    factory {
        NetworkErrorHandler() as ErrorHandler
    }
}