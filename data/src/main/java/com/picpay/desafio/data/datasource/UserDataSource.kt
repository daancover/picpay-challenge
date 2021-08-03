package com.picpay.desafio.data.datasource

import com.picpay.desafio.data.network.service.PicPayService
import com.picpay.desafio.domain.model.User
import com.picpay.desafio.domain.repository.UserRepository

class UserDataSource(
    private val picPayService: PicPayService
) : UserRepository {

    override suspend fun getUsers(): List<User> {
        return picPayService.getUsers()
    }
}