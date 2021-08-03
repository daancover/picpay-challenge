package com.picpay.desafio.data.error

import com.picpay.desafio.data.core.BaseCoroutineTest
import com.picpay.desafio.domain.error.Error.DeviceNotConnected
import com.picpay.desafio.domain.error.Error.GenericError
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Test
import java.net.UnknownHostException

@ExperimentalCoroutinesApi
class NetworkErrorHandlerTest : BaseCoroutineTest() {

    private val errorHandler = NetworkErrorHandler()

    @Test
    fun getError_returnsDeviceNotConnected_whenUnknownHostExceptionIsThrown() {
        errorHandler.errorListener = {
            assert(it is DeviceNotConnected)
        }

        testCoroutineRule.runBlockingTest {
            assert(errorHandler.getHandler() is CoroutineExceptionHandler)
            (errorHandler.getHandler() as CoroutineExceptionHandler).handleException(
                coroutineContext, UnknownHostException()
            )
        }
    }

    @Test
    fun getError_returnsGenericError_whenAnyExceptionIsThrown() {
        errorHandler.errorListener = {
            assert(it is GenericError)
        }

        testCoroutineRule.runBlockingTest {
            assert(errorHandler.getHandler() is CoroutineExceptionHandler)
            (errorHandler.getHandler() as CoroutineExceptionHandler).handleException(
                coroutineContext, Exception()
            )
        }
    }
}