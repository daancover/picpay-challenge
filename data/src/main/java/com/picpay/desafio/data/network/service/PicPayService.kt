package com.picpay.desafio.data.network.service

import com.picpay.desafio.domain.model.User
import retrofit2.http.GET

interface PicPayService {

    @GET("users")
    suspend fun getUsers(): List<User>
}