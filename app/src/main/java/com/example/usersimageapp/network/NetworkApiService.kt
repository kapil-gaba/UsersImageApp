package com.example.usersimageapp.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.squareup.okhttp.OkHttpClient
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit


private const val BASE_URL = "https://jsonplaceholder.typicode.com/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

//manually setting timeouts for slow network response
val okHttpClient = okhttp3.OkHttpClient.Builder().connectTimeout(1, TimeUnit.MINUTES)
    .readTimeout(30, TimeUnit.SECONDS).writeTimeout(15, TimeUnit.SECONDS).build()


// Configure common retrofit object to parse JSON and use coroutines
val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .client(okHttpClient)
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .build()

/**
 * User service interface
 */
interface UsersService {
    @GET("users")
    fun getUsers(): Deferred<List<Users>>
}

object UsersApi {

    val userslist = retrofit.create(UsersService::class.java)
}

/**
 * Album service interface
 */
interface AlbumsService {
    @GET("photos")
    fun getAlbums(@Query("albumId") type: Int?): Deferred<List<Albums>>
}

object AlbumsApi {

    val albumslist = retrofit.create(AlbumsService::class.java)
}

