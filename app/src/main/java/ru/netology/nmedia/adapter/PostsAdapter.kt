package ru.netology.nmedia.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.CardPostBinding
import ru.netology.nmedia.dto.Post

interface OnInteractionListener {
    fun onLike(post: Post)
    fun onRemove(post: Post)
    fun onEdit(post: Post)
    fun onShare(post: Post)
    fun cancell()
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
            textLikes.text = transform(post.likes)
            textViewShare.text = transform(post.share)

            like.setImageResource(
                if (post.likedByMe) R.drawable.baseline_favorite_red else R.drawable.outline_favorite_border_24
            )
            textLikes.text = transform(post.likes)
            textViewShare.text = transform(post.share)
            like.setOnClickListener {
                onInteractionListener.onLike(post)
            }
            imageShare.setOnClickListener {

                //    post.copy(share =post.share+1)
                onInteractionListener.onShare(post)

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

                            R.id.closeEdit -> {
                                onInteractionListener.cancell()

                                true
                            }

                            else -> false
                        }

                    }
                }.show()
            }
        }

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