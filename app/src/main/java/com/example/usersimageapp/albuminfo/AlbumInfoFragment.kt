package com.example.usersimageapp.albuminfo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.usersimageapp.R
import com.example.usersimageapp.createFactory
import com.example.usersimageapp.databinding.FragmentAlbumInfoBinding
import com.example.usersimageapp.network.Albums


class AlbumInfoFragment : Fragment() {


    private lateinit var albuminfoViewModel: AlbumInfoViewModel

    private var albumDataAdapter: AlbumDataAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //Databinding
        val binding: FragmentAlbumInfoBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_album_info, container, false)

        val application = requireNotNull(activity).application

        val args = arguments?.let {
            AlbumInfoFragmentArgs.fromBundle(it).selectedUser
        }


        albuminfoViewModel = AlbumInfoViewModel(args, application)
        val viewModelFactory = albuminfoViewModel.createFactory()

        albuminfoViewModel =
            ViewModelProviders.of(this, viewModelFactory).get(AlbumInfoViewModel::class.java)


        binding.also {
            it.lifecycleOwner = this
            it.albumViewmodel = albuminfoViewModel
        }

        // observing albums livedata
        albuminfoViewModel.albums.observe(viewLifecycleOwner, Observer<List<Albums>> { albums ->
            albums?.apply {
                albumDataAdapter?.albums = albums
            }
        })

        /**
         * Navigation from albumfragment to picturefragment
         * */
        albumDataAdapter = AlbumDataAdapter(AlbumItemClick {
            //Toast.makeText(context,"Album ${it.albumId}",Toast.LENGTH_SHORT).show()
            albuminfoViewModel.navigateToPictureScreen(it)
        })

        albuminfoViewModel.navigateAlbumToPictureFragment.observe(this, Observer {
            it?.let {

                this.findNavController().navigate(
                    AlbumInfoFragmentDirections.actionAlbumInfoFragmentToPictureFragment(it)
                )
                albuminfoViewModel.onNavigationToPictureFinish()
            }
        })


        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = albumDataAdapter
        }


        return binding.root
    }

}
