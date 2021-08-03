package com.picpay.desafio.domain.error

sealed class Error {
    object GenericError : Error()
    object DeviceNotConnected : Error()
    open class CustomError : Error()
}