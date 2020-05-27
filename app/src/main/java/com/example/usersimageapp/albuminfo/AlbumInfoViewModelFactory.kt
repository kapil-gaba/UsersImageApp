package com.example.usersimageapp.albuminfo

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.usersimageapp.network.Users

class AlbumInfoViewModelFactory(
    private val users: Users,
    private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AlbumInfoViewModel::class.java)) {
            return AlbumInfoViewModel(users, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}