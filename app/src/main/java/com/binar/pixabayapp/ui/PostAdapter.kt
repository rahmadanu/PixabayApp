package com.binar.pixabayapp.ui;

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.binar.pixabayapp.R
import com.binar.pixabayapp.databinding.ItemPostBinding
import com.binar.pixabayapp.model.Post


class PostAdapter(private val itemClick: (Post) -> Unit) :
    RecyclerView.Adapter<PostAdapter.PostViewHolder>() {


    private var items: MutableList<Post> = mutableListOf()

    fun setItems(items: List<Post>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    fun clearItems() {
        this.items.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding = ItemPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(binding, itemClick)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bindView(items[position])
    }

    override fun getItemCount(): Int = items.size


    class PostViewHolder(private val binding: ItemPostBinding, val itemClick: (Post) -> Unit) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindView(item: Post) {
            with(item) {
                itemView.setOnClickListener { itemClick(this) }
                binding.tvUsername.text = item.user.orEmpty()
                binding.tvImageTag.text = item.tags.orEmpty()
                binding.ivProfile.load(item.userImageURL) {
                    crossfade(true)
                    placeholder(R.drawable.ic_placeholder_profile)
                    transformations(CircleCropTransformation())
                }
                binding.ivPost.load(item.largeImageURL) {
                    crossfade(true)
                    placeholder(R.drawable.img_placeholder)
                }
            }

        }
    }

}