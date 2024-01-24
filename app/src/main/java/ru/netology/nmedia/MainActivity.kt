package ru.netology.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import android.widget.ImageView
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.viewmodel.PostViewModel
import androidx.activity.viewModels
import ru.netology.nmedia.databinding.CardPostBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel: PostViewModel by viewModels()
        viewModel.data.observe(this) { posts ->
            binding.container?.removeAllViews()

            posts.onEach { post ->

                CardPostBinding.inflate(layoutInflater,  binding.container, true).apply {

                    author.text = post.author
                    published.text = post.published
                    content.text = post.content
                    textLikes.text = transform(post.likes)
                    textViewShare.text = transform(post.share)

                    like.setImageResource(
                        if (post.likedByMe) R.drawable.baseline_favorite_red else R.drawable.outline_favorite_border_24
                    )
                    textLikes.text = post.likes.toString()

                }
                binding.like?.setOnClickListener {
                    viewModel.likeById(post.id)
                }
//                binding.imageShare?.setOnClickListener {
//                    viewModel.share()
//                }
            }

        }
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
}
