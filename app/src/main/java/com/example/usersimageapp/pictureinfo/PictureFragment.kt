package com.example.usersimageapp.pictureinfo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders

import com.example.usersimageapp.R
import com.example.usersimageapp.createFactory
import com.example.usersimageapp.databinding.FragmentPictureBinding



class PictureFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //Databinding
        val binding: FragmentPictureBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_picture, container, false)

        val application = requireNotNull(activity).application

        val args = arguments?.let {
            PictureFragmentArgs.fromBundle(it).selectedAlbum
        }


        //Getting viewmodel from viewmodelfactory
        val viewModelFactory = PictureViewModel(args, application).createFactory()
        val pictureViewModel =
            ViewModelProviders.of(this, viewModelFactory).get(PictureViewModel::class.java)

        binding.also{
            it.lifecycleOwner = this
            it.pictureViewModel = pictureViewModel
        }

        // Inflate the layout for this fragment
        return binding.root
    }

}
