package com.example.usersimageapp.pictureinfo

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.usersimageapp.albuminfo.AlbumInfoViewModel
import com.example.usersimageapp.network.Albums


class PictureViewModelFactory(
    private val albums: Albums,
    private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PictureViewModel::class.java)) {
            return PictureViewModel(albums, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}