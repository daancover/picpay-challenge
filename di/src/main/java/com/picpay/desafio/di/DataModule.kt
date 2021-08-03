package com.picpay.desafio.di

import com.picpay.desafio.data.datasource.UserDataSource
import com.picpay.desafio.domain.repository.UserRepository
import org.koin.dsl.module

@Suppress("USELESS_CAST")
val dataModule = module {

    single {
        UserDataSource(
            picPayService = get()
        ) as UserRepository
    }
}