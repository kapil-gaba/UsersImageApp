package com.example.usersimageapp.albuminfo

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.usersimageapp.getOrAwaitValue
import com.example.usersimageapp.network.Albums
import com.example.usersimageapp.network.AlbumsService
import com.example.usersimageapp.network.Users
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.nullValue
import org.junit.Before

import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AlbumInfoViewModelTest {

    private lateinit var albumViewModel : AlbumInfoViewModel

    private lateinit var users : Users

    private lateinit var albumsService: AlbumsService

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {

        albumsService = mockk()

        users = Users(1,"Kapil","kaps123@gmail.com","0425584488")

        albumViewModel = AlbumInfoViewModel(users,ApplicationProvider.getApplicationContext())

    }


    @Test
    fun albumsServiceCall_returnAlbumList(){

        val albumList = listOf(Albums(1,"Title","Image URL","Image Thumbnail Url"),
                               Albums(2,"Album Title","Image Url","Thumbnail Url"))

        coEvery { albumsService.getAlbums(users.id) } returns CompletableDeferred(albumList)

        val result = runBlocking {
            albumsService.getAlbums(users.id).await()
        }

        assertEquals(2, result.size)


    }


    @Test
    fun onNavigationToPictureScreen_passAlbumsParcel(){
        val album = Albums(1,"Title","Image URL","Image Thumbnail Url")

        //When navigating to picture screen
        albumViewModel.navigateToPictureScreen(album)

        //Then the selected Album passed
        val value = albumViewModel.navigateAlbumToPictureFragment.getOrAwaitValue()

        assertThat(value.title,`is`("Title"))

    }

    @Test
    fun onNavigationToPictureScreenFinish_setValueNull(){

        //When navigation finish
        albumViewModel.onNavigationToPictureFinish()

        //Then setvalue to null
        assertThat(albumViewModel.navigateAlbumToPictureFragment.getOrAwaitValue(),(nullValue()))
    }
}