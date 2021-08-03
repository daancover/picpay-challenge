package com.picpay.desafio.domain.repository

import com.picpay.desafio.domain.model.User

interface UserRepository {

    suspend fun getUsers(): List<User>
}