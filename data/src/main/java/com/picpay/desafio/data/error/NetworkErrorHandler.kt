package com.picpay.desafio.data.error

import com.picpay.desafio.domain.error.Error
import com.picpay.desafio.domain.error.Error.DeviceNotConnected
import com.picpay.desafio.domain.error.Error.GenericError
import com.picpay.desafio.domain.error.ErrorHandler
import kotlinx.coroutines.CoroutineExceptionHandler
import retrofit2.HttpException
import java.net.UnknownHostException
import kotlin.coroutines.CoroutineContext

class NetworkErrorHandler : ErrorHandler() {

    override var errorListener: ((Error) -> Unit)? = null

    private val handler = CoroutineExceptionHandler { _, exception ->
        val error = getError(exception)
        errorListener?.invoke(error)
    }

    override fun getHandler(): CoroutineContext {
        return handler
    }

    private fun getError(
        exception: Throwable
    ): Error {
        return when (exception) {
            is HttpException -> handleHttpException(exception)
            is UnknownHostException -> DeviceNotConnected
            else -> GenericError
        }
    }

    private fun handleHttpException(exception: HttpException) = when (exception.code()) {
        // Handle different error codes
        else -> GenericError
    }
}