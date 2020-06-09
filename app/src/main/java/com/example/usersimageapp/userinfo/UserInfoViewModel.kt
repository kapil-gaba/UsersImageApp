package com.example.usersimageapp.userinfo

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.usersimageapp.ApiStatus
import com.example.usersimageapp.network.Users
import com.example.usersimageapp.network.UsersApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.io.IOException


class UserInfoViewModel(
    application: Application
) : AndroidViewModel(application) {


    // Coroutines
    val job = Job()
    val viewModelScope = CoroutineScope(job + Dispatchers.Main)

    // For api status
    private val _apiStatus = MutableLiveData<ApiStatus>()
    val apiStatus: LiveData<ApiStatus>
        get() = _apiStatus

    // For user data
    private val _users = MutableLiveData<List<Users>>()
    val users: LiveData<List<Users>>
        get() = _users

    // Navigation from userfragment to albumfragment
    private val _navigateUserToAlbumFragment = MutableLiveData<Users>()
    val navigateUserToAlbumFragment: LiveData<Users> get() = _navigateUserToAlbumFragment

    init {
        //getting data on initialization of viewModel
        getUsersData()
    }

    fun navigateToAlbumScreen(users: Users) {
        _navigateUserToAlbumFragment.value = users
    }

    fun onNavigationToAlbumFinish() {
        _navigateUserToAlbumFragment.value = null
    }

    fun getUsersData() {

        viewModelScope.launch {
            _apiStatus.value = ApiStatus.LOADING
            val deferredData = UsersApi.userslist.getUsers()
            try {
                val usersList = deferredData.await()
                if (usersList.isNotEmpty()) {
                    _apiStatus.value = ApiStatus.DONE
                    _users.value = usersList
                }
            } catch (networkError: IOException) {
                Log.i("UserinfoViewModel",networkError.toString())
                _apiStatus.value = ApiStatus.ERROR
                _users.value = ArrayList()
            }
        }
    }

    // Cancelling job on clearing viewModel
    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }


}