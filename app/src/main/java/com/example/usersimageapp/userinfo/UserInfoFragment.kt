package com.example.usersimageapp.userinfo

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.usersimageapp.R
import com.example.usersimageapp.createFactory
import com.example.usersimageapp.databinding.FragmentUserInfoBinding
import com.example.usersimageapp.network.Users
import com.example.usersimageapp.network.UsersApi


class UserInfoFragment : Fragment() {


    // Accessing viewModel on activity created
    private val viewModel: UserInfoViewModel by lazy {
        val activity = requireNotNull(this.activity) {
            "We can only access the viewModel after onActivityCreated()"
        }
        ViewModelProviders.of(this, UserInfoViewModel(activity.application).createFactory())
            .get(UserInfoViewModel::class.java)
    }


    private var userdataAdapter: UserDataAdapter? = null

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        //Observing users live data
        viewModel.users.observe(viewLifecycleOwner, Observer<List<Users>> { users ->
            users?.apply {
                userdataAdapter?.users = users
            }
        })

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //Databinding
        val binding: FragmentUserInfoBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_user_info, container, false)

        // Setting lifecycleOwner so DataBinding can observe LiveData
       binding.also {
           it.lifecycleOwner = viewLifecycleOwner
           it.viewModel = viewModel
       }

        // Navigation from userfragment to albumfragment
        userdataAdapter = UserDataAdapter(UserItemClick {

            viewModel.navigateToAlbumScreen(it)

            // Toast.makeText(context,"User ${it.id}",Toast.LENGTH_SHORT).show()
        })

        viewModel.navigateUserToAlbumFragment.observe(this, Observer {
             it?.let {
                this.findNavController().navigate(
                    UserInfoFragmentDirections.actionUserInfoFragmentToAlbumInfoFragment(it)
                )
                viewModel.onNavigationToAlbumFinish()
            }
        })


        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = userdataAdapter
        }

        return binding.root
    }

}
