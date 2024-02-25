package ru.netology.nmedia.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.core.content.ContextCompat.startActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.CardPostBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.viewmodel.empty

interface OnInteractionListener {
    fun onLike(post: Post)
    fun onRemove(post: Post)
    fun onEdit(post: Post)
    fun onShare(post: Post)

    fun play(post: Post)
}


class PostsAdapter(

    private val onInteractionListener: OnInteractionListener
) :
    ListAdapter<Post, PostViewHolder>(PostDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding = CardPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(binding, onInteractionListener)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class PostViewHolder(
    private val binding: CardPostBinding,
    private val onInteractionListener: OnInteractionListener,

    ) : RecyclerView.ViewHolder(binding.root) {
    fun bind(post: Post) {
        with(binding) {
            author.text = post.author
            published.text = post.published
            content.text = post.content
            //    textLikes.text = transform(post.likes)
            imageShare.text = transform(post.share)

            groupVideo.isVisible = !post.videoUrl.isNullOrEmpty()
            like.isChecked = post.likedByMe

            like.text = transform(post.likes)
            imageShare.text = transform(post.share)
            like.setOnClickListener {
                onInteractionListener.onLike(post)
            }

            imageShare.setOnClickListener {

                //    post.copy(share =post.share+1)
                onInteractionListener.onShare(post)
            }

            imageVideo.setOnClickListener {
                onInteractionListener.play(post)
            }

            buttonPlay.setOnClickListener {
                onInteractionListener.play(post)
            }

            imgMenu.setOnClickListener {
                PopupMenu(it.context, it).apply {
                    inflate(R.menu.option_post)
                    setOnMenuItemClickListener { item ->
                        when (item.itemId) {
                            R.id.remove -> {
                                onInteractionListener.onRemove(post)
                                true
                            }

                            R.id.edit -> {
                                onInteractionListener.onEdit(post)

                                true
                            }
//
                            else -> false
                        }

                    }
                }.show()
            }
        }

    }

    private fun startActivity(intent: Intent) {
        startActivity(intent)
    }


}

object PostDiffCallback : DiffUtil.ItemCallback<Post>() {
    override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Post, newItem: Post) = oldItem == newItem


}

fun transform(number: Int): String {
    var x = number.toString()
    if (number > 999) {
        x = String.format((number / 1000).toString() + "K")
    }
    if (number > 1099) {
        x = String.format("%1.1fK", (number / 100).toDouble() / 10)
    }
    if (number > 9999) {
        x = String.format((number / 1000).toString() + "K")
    }
    if (number > 999999) {
        x = String.format((number / 1000000).toString() + "М")
    }

    if (number > 1099999) {
        x = String.format("%1.1fM", number.toDouble() / 1000000)
    }

    return x;
}