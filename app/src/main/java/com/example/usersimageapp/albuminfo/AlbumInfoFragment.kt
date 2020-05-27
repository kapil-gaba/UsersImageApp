package com.example.usersimageapp.albuminfo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.usersimageapp.R
import com.example.usersimageapp.databinding.FragmentAlbumInfoBinding
import com.example.usersimageapp.network.Albums
import com.example.usersimageapp.network.Users
import com.example.usersimageapp.userinfo.UserInfoViewModel
import com.example.usersimageapp.userinfo.UserInfoViewModelFactory

/**
 * A simple [Fragment] subclass.
 */
class AlbumInfoFragment : Fragment() {



private lateinit var albuminfoViewModel : AlbumInfoViewModel

private var albumDataAdapter : AlbumDataAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding : FragmentAlbumInfoBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_album_info,container,false)
        val application = requireNotNull(activity).application
        val args = AlbumInfoFragmentArgs.fromBundle(arguments!!).selectedUser
        val viewModelFactory = AlbumInfoViewModelFactory(args,application)
         albuminfoViewModel = ViewModelProviders.of(this, viewModelFactory).get(AlbumInfoViewModel::class.java)

        binding.setLifecycleOwner(this)
        binding.albumViewmodel = albuminfoViewModel
        albuminfoViewModel.albums.observe(viewLifecycleOwner, Observer<List<Albums>> { albums ->
            albums?.apply {
                albumDataAdapter?.albums = albums
            }
        })

     albumDataAdapter = AlbumDataAdapter( AlbumItemClick {
         Toast.makeText(context,"Album ${it.albumId}",Toast.LENGTH_SHORT).show()
     })


        binding.root.findViewById<RecyclerView>(R.id.recycler_view).apply {
            layoutManager = LinearLayoutManager(context)
            adapter = albumDataAdapter
        }


        return binding.root
    }

}
