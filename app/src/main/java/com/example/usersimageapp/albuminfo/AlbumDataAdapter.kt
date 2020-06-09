package com.example.usersimageapp.albuminfo


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.usersimageapp.R
import com.example.usersimageapp.databinding.AlbuminfoItemBinding
import com.example.usersimageapp.network.Albums


/**
 * Recyclerview adapter for attaching all albums in albuminfo
 *
 */
class AlbumDataAdapter(val callback: AlbumItemClick) : RecyclerView.Adapter<AlbumInfoViewHolder>() {


    var albums: List<Albums> = emptyList()
        set(value) {
            field = value

            // Notify any registered observers that the data set has changed. This will cause every
            // element in our RecyclerView to be invalidated.
            notifyDataSetChanged()
        }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumInfoViewHolder {
        val withDataBinding: AlbuminfoItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            AlbumInfoViewHolder.LAYOUT,
            parent,
            false
        )
        return AlbumInfoViewHolder(withDataBinding)
    }

    override fun getItemCount() = albums.size


    override fun onBindViewHolder(holder: AlbumInfoViewHolder, position: Int) {
        holder.viewDataBinding.also {
            it.albums = albums[position]
            it.albumItemClick = callback

        }
    }

}

/**
 * On Album click lambda
 *
 */
class AlbumItemClick(val block: (Albums) -> Unit) {

    fun onClick(albums: Albums) = block(albums)
}

/**
 * Viewholder to albums
 *
 */
class AlbumInfoViewHolder(val viewDataBinding: AlbuminfoItemBinding) :
    RecyclerView.ViewHolder(viewDataBinding.root) {
    companion object {
        @LayoutRes
        val LAYOUT = R.layout.albuminfo_item
    }
}