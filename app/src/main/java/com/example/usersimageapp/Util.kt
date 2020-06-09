package com.example.usersimageapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider


enum class ApiStatus {
    LOADING,
    DONE,
    ERROR
}


// Extension function for viewModelFactory to return viewModel class
fun <T : ViewModel> T.createFactory(): ViewModelProvider.Factory {
    val viewModel = this
    return object : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T = viewModel as T
    }
}

