package com.example.usersimageapp.userinfo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.usersimageapp.R
import com.example.usersimageapp.databinding.UserinfoItemBinding
import com.example.usersimageapp.network.Users


class UserDataAdapter(val callback: UserItemClick) : RecyclerView.Adapter<UserInfoViewHolder>() {


    var users: List<Users> = emptyList()
        set(value) {
            field = value

            // Notify any registered observers that the data set has changed. This will cause every
            // element in our RecyclerView to be invalidated.
            notifyDataSetChanged()
        }

    /**
     * Called when RecyclerView needs a new {@link ViewHolder} of the given type to represent
     * an item.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserInfoViewHolder {
        val withDataBinding: UserinfoItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            UserInfoViewHolder.LAYOUT,
            parent,
            false)
        return UserInfoViewHolder(withDataBinding)
    }

    override fun getItemCount() = users.size

    /**
     * Called by RecyclerView to display the data at the specified position. This method should
     * update the contents of the {@link ViewHolder#itemView} to reflect the item at the given
     * position.
     */
    override fun onBindViewHolder(holder: UserInfoViewHolder, position: Int) {
        holder.viewDataBinding.also {
            it.users = users[position]
            it.userItemClick = callback
        }
    }

}


class UserItemClick(val block: (Users) -> Unit) {

    fun onClick(users: Users) = block(users)
}


class UserInfoViewHolder(val viewDataBinding: UserinfoItemBinding) :
    RecyclerView.ViewHolder(viewDataBinding.root) {
    companion object {
        @LayoutRes
        val LAYOUT = R.layout.userinfo_item
    }
}