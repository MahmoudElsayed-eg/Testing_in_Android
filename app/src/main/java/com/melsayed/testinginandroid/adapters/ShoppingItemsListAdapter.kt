package com.melsayed.testinginandroid.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.melsayed.testinginandroid.R
import com.melsayed.testinginandroid.data.local.ShoppingItem
import com.melsayed.testinginandroid.databinding.ItemShoppingBinding
import javax.inject.Inject

class ShoppingItemsListAdapter (private val glide: RequestManager,private val onShoppingItemClicked: OnShoppingItemClicked) :
    ListAdapter<ShoppingItem, ShoppingItemsListAdapter.ShoppingItemViewHolder>(diffUtil) {

    inner class ShoppingItemViewHolder(private val binding: ItemShoppingBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val item = getItem(position)
                    if (item != null) {
                        onShoppingItemClicked.onShoppingItemClick(item)
                    }
                }
            }
        }

        fun bind(shoppingItem: ShoppingItem) {
            binding.apply {
                itemTxtAmount.text = String.format("%d%s", shoppingItem.amount, "x")
                itemTxtName.text = shoppingItem.name
                itemTxtPrice.text = String.format("%.2f%s", shoppingItem.price, "$")
            }
            glide.load(shoppingItem.imgUrl)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(binding.itemShoppingImage)
        }

    }

    companion object {
        private val diffUtil = object : DiffUtil.ItemCallback<ShoppingItem>() {
            override fun areItemsTheSame(oldItem: ShoppingItem, newItem: ShoppingItem) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: ShoppingItem, newItem: ShoppingItem) =
                oldItem == newItem


        }
    }

    override fun onBindViewHolder(holder: ShoppingItemViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null) {
            holder.bind(item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoppingItemViewHolder {
        val binding =
            ItemShoppingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ShoppingItemViewHolder(binding)
    }

    interface OnShoppingItemClicked {
        fun onShoppingItemClick(shoppingItem: ShoppingItem)
    }

}