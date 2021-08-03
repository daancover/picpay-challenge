package com.picpay.desafio.android.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.picpay.desafio.android.R
import com.picpay.desafio.android.databinding.ActivityMainBinding
import com.picpay.desafio.android.extension.showDialog
import com.picpay.desafio.android.ui.adapter.UserListAdapter
import com.picpay.desafio.android.viewmodel.MainViewModel
import com.picpay.desafio.domain.model.User
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private val viewModel by viewModel<MainViewModel>()

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: UserListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setupObservers()
        setupView()
    }

    private fun setupObservers() {
        lifecycle.addObserver(viewModel)
        viewModel.onUserListResult().observe(this, handleUserList())
        viewModel.onErrorResult().observe(this, handleError())
    }

    private fun setupView() {
        adapter = UserListAdapter()

        with(binding) {
            recyclerView.adapter = adapter
            userListProgressBar.isVisible = true
        }
    }

    private fun getUsers() {
        with(binding) {
            userListProgressBar.isVisible = true
            recyclerView.isVisible = true
        }

        viewModel.getUsers()
    }

    private fun handleUserList() = Observer<List<User>> { users ->
        binding.userListProgressBar.isVisible = false
        adapter.users = users
    }

    private fun handleError() = Observer<Int> { error ->
        with(binding) {
            userListProgressBar.isVisible = false
            recyclerView.isVisible = false
        }

        showDialog(
            message = error,
            positiveButtonText = R.string.try_again_button,
            positiveButtonClick = { _, _ ->
                getUsers()
            }
        )
    }
}
