package com.example.usersimageapp.pictureinfo


import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.usersimageapp.network.Albums


class PictureViewModel(albums: Albums?, app: Application) : AndroidViewModel(app) {

    private var _selectedAlbum = MutableLiveData<Albums>()
    val selectedAlbum: LiveData<Albums>
        get() = _selectedAlbum


    init {
        _selectedAlbum.value = albums
    }

}