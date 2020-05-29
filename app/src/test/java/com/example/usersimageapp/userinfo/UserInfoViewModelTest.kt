package com.example.usersimageapp.userinfo

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.usersimageapp.getOrAwaitValue
import com.example.usersimageapp.network.Users
import com.example.usersimageapp.network.UsersService
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.*
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class UserInfoViewModelTest{


    private lateinit var userViewModel : UserInfoViewModel

    private lateinit var usersService : UsersService

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp(){
        //Given a fresh userviewmodel
         userViewModel = UserInfoViewModel(ApplicationProvider.getApplicationContext())

        usersService = mockk()
    }

    @Test
    fun usersService_returndeferredUsersList() {

        val usersList = listOf<Users>(Users(1,"Kapil","kaps12@gmail.com","46546545646"),
                                      Users(2,"rahul","rahs13@gmail.com","5887455454") )

        coEvery { usersService.getUsers() } returns CompletableDeferred(usersList)

        val usersData = runBlocking { usersService.getUsers().await() }

        assertEquals(2, usersData.size)
    }

    @Test
    fun onNavigateToAlbumScreen_passUserParcel(){

        val user = Users(1,"Kapil","kaps123@gmail.com","0425584488")

        //When navigating to album screen
        userViewModel.navigateToAlbumScreen(user)

        //Then the selected user object passed
        val value = userViewModel.navigateUserToAlbumFragment.getOrAwaitValue()

        assertThat(value.name, `is`("Kapil"))

    }

    @Test
    fun onNavigationToAlbumScreenFinish_setValueNull(){

        //When navigation finish
        userViewModel.onNavigationToAlbumFinish()

        //Then the set value to null
        assertThat(userViewModel.navigateUserToAlbumFragment.getOrAwaitValue(),(nullValue()))

    }


}