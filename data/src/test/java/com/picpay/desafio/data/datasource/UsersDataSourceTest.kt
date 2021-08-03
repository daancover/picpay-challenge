package com.picpay.desafio.data.datasource

import com.picpay.desafio.data.core.BaseCoroutineTest
import com.picpay.desafio.data.network.service.PicPayService
import com.picpay.desafio.domain.model.User
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify

@ExperimentalCoroutinesApi
class UsersDataSourceTest : BaseCoroutineTest() {

    @Mock
    private lateinit var service: PicPayService

    @InjectMocks
    private lateinit var dataSource: UserDataSource

    override fun setup() {
        super.setup()
        dataSource = UserDataSource(service)
    }

    @Test
    fun getUsers_callsCorrectApi() {
        testCoroutineRule.runBlockingTest {
            val expectedValue = emptyList<User>()
            stubGetUsers(expectedValue)

            dataSource.getUsers()

            verify(service).getUsers()
        }
    }

    private suspend fun stubGetUsers(expectedValue: List<User>) {
        `when`(service.getUsers()).thenReturn(expectedValue)
    }
}