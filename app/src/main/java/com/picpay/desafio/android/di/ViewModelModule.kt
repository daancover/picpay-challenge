package com.picpay.desafio.android.di

import com.picpay.desafio.android.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel {
        MainViewModel(
            userRepository = get(),
            errorHandler = get()
        )
    }
}