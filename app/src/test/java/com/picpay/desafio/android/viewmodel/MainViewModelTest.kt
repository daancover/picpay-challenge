package com.picpay.desafio.android.viewmodel

import androidx.lifecycle.Observer
import com.picpay.desafio.android.core.BaseCoroutineTest
import com.picpay.desafio.domain.error.ErrorHandler
import com.picpay.desafio.domain.model.User
import com.picpay.desafio.domain.repository.UserRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Test
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify

@ExperimentalCoroutinesApi
class MainViewModelTest : BaseCoroutineTest() {

    @Mock
    private lateinit var userRepository: UserRepository

    @Mock
    private lateinit var errorHandler: ErrorHandler

    @Mock
    private lateinit var userListObserver: Observer<List<User>>

    @Captor
    private lateinit var userListCaptor: ArgumentCaptor<List<User>>

    @InjectMocks
    private lateinit var viewModel: MainViewModel

    override fun setup() {
        super.setup()
        viewModel = MainViewModel(userRepository, errorHandler)
        stubErrorHandler()
    }

    @Test
    fun getUsers_callsCorrectRepository() {
        testCoroutineRule.runBlockingTest {
            val expectedValue = emptyList<User>()
            stubGetUsers(expectedValue)

            viewModel.getUsers()

            verify(userRepository).getUsers()
        }
    }

    @Test
    fun getUsers_answersCorrectLiveData() {
        viewModel.onUserListResult().observeForever(userListObserver)

        testCoroutineRule.runBlockingTest {
            val expectedValue = emptyList<User>()
            stubGetUsers(expectedValue)

            viewModel.getUsers()

            userListCaptor.run {
                verify(userListObserver).onChanged(capture())
                assert(value == expectedValue)
            }
        }

        viewModel.onUserListResult().removeObserver(userListObserver)
    }

    private fun stubErrorHandler() {
        `when`(errorHandler.getHandler()).thenReturn(CoroutineExceptionHandler { _, _ -> })
    }

    private suspend fun stubGetUsers(expectedValue: List<User>) {
        `when`(userRepository.getUsers()).thenReturn(expectedValue)
    }
}