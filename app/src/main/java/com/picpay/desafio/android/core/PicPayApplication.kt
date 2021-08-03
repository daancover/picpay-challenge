package com.picpay.desafio.android.core

import android.app.Application
import com.picpay.desafio.android.di.viewModelModule
import com.picpay.desafio.di.dataModule
import com.picpay.desafio.di.errorModule
import com.picpay.desafio.di.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class PicPayApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@PicPayApplication)
            modules(
                listOf(
                    viewModelModule,
                    dataModule,
                    networkModule,
                    errorModule
                )
            )
        }
    }
}