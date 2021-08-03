package com.picpay.desafio.android.viewmodel

import androidx.lifecycle.*
import androidx.lifecycle.Lifecycle.Event.ON_CREATE
import com.picpay.desafio.android.R
import com.picpay.desafio.domain.error.Error
import com.picpay.desafio.domain.error.Error.DeviceNotConnected
import com.picpay.desafio.domain.error.ErrorHandler
import com.picpay.desafio.domain.model.User
import com.picpay.desafio.domain.repository.UserRepository
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent

class MainViewModel(
    private val userRepository: UserRepository,
    private val errorHandler: ErrorHandler
) : ViewModel(), LifecycleObserver {

    private val listener = { error: Error ->
        val errorMessage: Int = getErrorMessage(error)
        this.error.postValue(errorMessage)
    }

    private val userList = MutableLiveData<List<User>>()
    private val error = MutableLiveData<Int>()

    fun onUserListResult(): LiveData<List<User>> = userList

    fun onErrorResult(): LiveData<Int> = error

    @OnLifecycleEvent(ON_CREATE)
    fun setupErrorHandlerListener() {
        errorHandler.errorListener = listener
    }

    @OnLifecycleEvent(ON_CREATE)
    fun getUsers() {
        viewModelScope.launch(errorHandler.getHandler()) {
            val users = userRepository.getUsers()
            userList.postValue(users)
        }
    }

    private fun getErrorMessage(error: Error) = when (error) {
        is DeviceNotConnected -> R.string.device_not_connected_error
        else -> R.string.error
    }
}