package com.example.usersimageapp

import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.core.net.toUri

import androidx.databinding.BindingAdapter
import com.example.usersimageapp.userinfo.ApiStatus
import com.squareup.picasso.Picasso

//  Image url from web service not working with Glide and Picasso libraries
// Under testing
@BindingAdapter("imgSrcUrl")
fun loadImage(imgView: ImageView, imgUrl: String) {
    //  Glide.with(imgView.context).load("https://via.placeholder.com/600/92c952").into(imgView)
    // Picasso.with(imgView.context).load("http://mars.jpl.nasa.gov/msl-raw-images/msss/01000/mcam/1000MR0044631300503690E01_DXXX.jpg").into(imgView)

    val imgUri = "https://via.placeholder.com/150/771796".toUri().buildUpon().scheme("https").build()
        Picasso.with(imgView.context).load(imgUri).into(imgView)


////    imgUrl?.let {
////        val imgUri = it.toUri().buildUpon().scheme("https").build()
////        Glide.with(imgView.context).load(imgUri).
////            apply(
////                RequestOptions().placeholder(R.drawable.loading_animation).
////                    error(R.drawable.ic_broken_image)).into(imgView)
//    }

}

// Binding adapter for getting ApiStatus check

@BindingAdapter("ApiStatus")
fun getApiStatus(imgView: ImageView, status: ApiStatus?){
    when(status){
        ApiStatus.LOADING -> {
            Log.i("LoadingStatus", "ok")
            imgView.visibility = View.VISIBLE
            imgView.setImageResource(R.drawable.loading_animation)
        }
        ApiStatus.ERROR -> {
            Log.i("ErrorStatus", "ok")
            imgView.visibility = View.VISIBLE
            imgView.setImageResource(R.drawable.ic_connection_error)
        }
        ApiStatus.DONE ->{
            imgView.visibility = View.GONE
            Log.i("DoneStatus", "ok")
        }

    }
}