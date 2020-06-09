package com.example.usersimageapp.userinfo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.usersimageapp.R
import com.example.usersimageapp.databinding.UserinfoItemBinding
import com.example.usersimageapp.network.Users

// Recyclerview Adapter for attaching users data
class UserDataAdapter(val callback: UserItemClick) : RecyclerView.Adapter<UserInfoViewHolder>() {


    var users: List<Users> = emptyList()
        set(value) {
            field = value

            // Notify any registered observers that the data set has changed. This will cause every
            // element in our RecyclerView to be invalidated.
            notifyDataSetChanged()
        }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserInfoViewHolder {
        val withDataBinding: UserinfoItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            UserInfoViewHolder.LAYOUT,
            parent,
            false
        )
        return UserInfoViewHolder(withDataBinding)
    }

    override fun getItemCount() = users.size



    override fun onBindViewHolder(holder: UserInfoViewHolder, position: Int) {
        holder.viewDataBinding.also {
            it.users = users[position]
            it.userItemClick = callback
        }
    }

}

//On user item click lambda
class UserItemClick(val block: (Users) -> Unit) {

    fun onClick(users: Users) = block(users)
}

//ViewHolder for userdataitem
class UserInfoViewHolder(val viewDataBinding: UserinfoItemBinding) :
    RecyclerView.ViewHolder(viewDataBinding.root) {
    companion object {
        @LayoutRes
        val LAYOUT = R.layout.userinfo_item
    }
}