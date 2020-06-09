package com.example.usersimageapp.albuminfo

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.usersimageapp.ApiStatus
import com.example.usersimageapp.network.Albums
import com.example.usersimageapp.network.AlbumsApi
import com.example.usersimageapp.network.Users
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.io.IOException

class AlbumInfoViewModel(users: Users?, app: Application) : AndroidViewModel(app) {


    // Album live data
    private val _albums = MutableLiveData<List<Albums>>()
    val albums: LiveData<List<Albums>> get() = _albums

    // To check api status
    private val _albumapistatus = MutableLiveData<ApiStatus>()
    val albumapistatus: LiveData<ApiStatus>
        get() = _albumapistatus

    // For navigation from albumfragment to picturefragment
    private val _navigateAlbumToPictureFragment = MutableLiveData<Albums>()
    val navigateAlbumToPictureFragment: LiveData<Albums> get() = _navigateAlbumToPictureFragment

    // Coroutines
    val job = Job()
    val viewModelScope = CoroutineScope(job + Dispatchers.Main)

    init {
        getAlbum(users)
    }

    fun navigateToPictureScreen(albums: Albums) {
        _navigateAlbumToPictureFragment.value = albums
    }

    fun onNavigationToPictureFinish() {
        _navigateAlbumToPictureFragment.value = null
    }

    fun getAlbum(users: Users?) {

        viewModelScope.launch {

            _albumapistatus.value = ApiStatus.LOADING
            Log.i("StartLoading", "loading")
            val defferedAlbum = AlbumsApi.albumslist.getAlbums(users?.id)

            try {
                val albumsList = defferedAlbum.await()
                if (albumsList.size > 0) {
                    Log.i("NetworkSuccess", "done")
                    _albumapistatus.value = ApiStatus.DONE
                    _albums.value = albumsList
                }
            } catch (networkError: IOException) {
                Log.i("AlbuminfoViewModel", networkError.toString())
                _albumapistatus.value = ApiStatus.ERROR
                _albums.value = ArrayList()
            }
        }
    }

    //cancelling coroutine job on clearing viewmodel
    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }

}