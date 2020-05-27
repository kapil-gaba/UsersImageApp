package com.example.usersimageapp.albuminfo

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.usersimageapp.network.Albums
import com.example.usersimageapp.network.AlbumsApi
import com.example.usersimageapp.network.Users
import com.example.usersimageapp.userinfo.ApiStatus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.io.IOException

class AlbumInfoViewModel(users: Users, app: Application) : AndroidViewModel(app) {

    private val _albums = MutableLiveData<List<Albums>>()
    val albums: LiveData<List<Albums>> get() = _albums

    private val _albumapistatus = MutableLiveData<ApiStatus>()
      val albumapistatus : LiveData<ApiStatus>
                               get() = _albumapistatus

    val job = Job()

    val viewModelScope = CoroutineScope(job + Dispatchers.Main)

    init {
        getAlbum(users)
    }

    fun getAlbum(users: Users) {
        viewModelScope.launch {
            _albumapistatus.value = ApiStatus.LOADING
            Log.i("StartLoading", "loading")
            val defferedAlbum = AlbumsApi.albumslist.getAlbums(users.id)
            try {
                val albumsList = defferedAlbum.await()
                if (albumsList.size > 0) {
                    Log.i("NetworkSuccess", "done")
                    _albumapistatus.value = ApiStatus.DONE
                    _albums.value = albumsList
                }
            } catch (error: IOException) {
                Log.i("NetworkException", error.toString())
                _albumapistatus.value = ApiStatus.ERROR
                _albums.value = ArrayList()
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }

}