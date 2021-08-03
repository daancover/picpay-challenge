package com.picpay.desafio.domain.error

import kotlin.coroutines.CoroutineContext

abstract class ErrorHandler {

    abstract var errorListener: ((Error) -> Unit)?

    abstract fun getHandler(): CoroutineContext
}