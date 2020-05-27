package com.example.usersimageapp.albuminfo

import android.os.AsyncTask
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.usersimageapp.R
import com.example.usersimageapp.databinding.AlbuminfoItemBinding
import com.example.usersimageapp.databinding.UserinfoItemBinding
import com.example.usersimageapp.network.Albums



class AlbumDataAdapter(val callback: AlbumItemClick) : RecyclerView.Adapter<AlbumInfoViewHolder>() {


    var albums: List<Albums> = emptyList()
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
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumInfoViewHolder {
        val withDataBinding: AlbuminfoItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            AlbumInfoViewHolder.LAYOUT,
            parent,
            false)
        return AlbumInfoViewHolder(withDataBinding)
    }

    override fun getItemCount() = albums.size

    /**
     * Called by RecyclerView to display the data at the specified position. This method should
     * update the contents of the {@link ViewHolder#itemView} to reflect the item at the given
     * position.
     */
    override fun onBindViewHolder(holder: AlbumInfoViewHolder, position: Int) {
        holder.viewDataBinding.also {
            it.albums = albums[position]
            it.albumItemClick = callback

        }
    }

}


class AlbumItemClick(val block: (Albums) -> Unit) {

    fun onClick(albums: Albums) = block(albums)
}


class AlbumInfoViewHolder(val viewDataBinding: AlbuminfoItemBinding) :
    RecyclerView.ViewHolder(viewDataBinding.root) {
    companion object {
        @LayoutRes
        val LAYOUT = R.layout.albuminfo_item
    }
}