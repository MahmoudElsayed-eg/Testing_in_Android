package com.melsayed.testinginandroid.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.TransitionOptions
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.melsayed.testinginandroid.R
import com.melsayed.testinginandroid.data.local.ShoppingItem
import com.melsayed.testinginandroid.data.remote.PixabayPhoto
import com.melsayed.testinginandroid.databinding.ItemImageBinding
import com.melsayed.testinginandroid.databinding.ItemShoppingBinding
import javax.inject.Inject

class ImagesPagerAdapter (private val glide: RequestManager,private val onPhotoClicked: OnImageClicked) :
    PagingDataAdapter<PixabayPhoto, ImagesPagerAdapter.ImageItemViewHolder>(diffUtil) {



    inner class ImageItemViewHolder(private val binding: ItemImageBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val item = getItem(position)
                    if (item != null) {
                        onPhotoClicked.onImageClick(item)
                    }
                }
            }

        }

        fun bind(pixabayPhoto: PixabayPhoto) {
            glide.load(pixabayPhoto.previewURL)
                .transition(DrawableTransitionOptions.withCrossFade()).into(binding.itemImage)
        }

    }

    companion object {
        private val diffUtil = object : DiffUtil.ItemCallback<PixabayPhoto>() {
            override fun areItemsTheSame(oldItem: PixabayPhoto, newItem: PixabayPhoto) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: PixabayPhoto, newItem: PixabayPhoto) =
                oldItem == newItem


        }
    }


    override fun onBindViewHolder(holder: ImageItemViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null) {
            holder.bind(item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageItemViewHolder {
        val binding =
            ItemImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ImageItemViewHolder(binding)
    }

    interface OnImageClicked{
        fun onImageClick(pixabayPhoto: PixabayPhoto)
    }

}