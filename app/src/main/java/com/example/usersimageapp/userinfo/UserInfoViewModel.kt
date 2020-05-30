package com.example.usersimageapp.userinfo

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.usersimageapp.network.Users
import com.example.usersimageapp.network.UsersApi
import com.example.usersimageapp.network.UsersService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.io.IOException


enum class ApiStatus {
    LOADING,
    DONE,
    ERROR
}


class UserInfoViewModel(
    application: Application
) : AndroidViewModel(application) {

// Coroutines
    val job = Job()

    val viewModelScope = CoroutineScope(job + Dispatchers.Main)

    // For api status
    private val _status = MutableLiveData<ApiStatus>()

    val status: LiveData<ApiStatus>
        get() = _status

    // For user data
    private val _users = MutableLiveData<List<Users>>()

    val users: LiveData<List<Users>>
        get() = _users


    // Navigation from userfragment to albumfragment
    private val _navigateUserToAlbumFragment = MutableLiveData<Users>()
    val navigateUserToAlbumFragment: LiveData<Users> get() = _navigateUserToAlbumFragment

    fun navigateToAlbumScreen(users: Users) {
        _navigateUserToAlbumFragment.value = users
    }

    fun onNavigationToAlbumFinish() {
        _navigateUserToAlbumFragment.value = null
    }


    init {
        //getting data on initialization of viewModel
        getUsersData()
    }


    fun getUsersData() {

        viewModelScope.launch {
            _status.value = ApiStatus.LOADING
        val deferredData = UsersApi.userslist.getUsers()
            try {
                val usersList = deferredData.await()
                if (usersList.isNotEmpty()) {
                    _status.value = ApiStatus.DONE
                    _users.value = usersList
                }
            } catch (networkerror: IOException) {
                _status.value = ApiStatus.ERROR
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