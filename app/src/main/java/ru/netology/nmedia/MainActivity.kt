package ru.netology.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.dto.Post

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val post = Post(
            id = 1,
            autor = "Нетология. Университет интернет-профессий будущего.",
            published = "21 мая в 18:36",
            content = "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растем сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия - помочь встать на путь роста и начать цепочку перемен - http://netology.gy/fyb ",
            likes = 1299,
            share = 998,
        )

        with(binding) {
            author.text = post.autor
            published.text = post.published
            txtNetology.text = post.content
            textLikes.text = transform(post.likes)
            textViewShare.text = post.share.toString()

            if (post.likedByMe) {
                like.setImageResource(R.drawable.baseline_favorite_red)
            }

            like.setOnClickListener {
                println("like")
                if (post.likedByMe) post.likes-- else post.likes++
                post.likedByMe = !post.likedByMe
                like.setImageResource(if (post.likedByMe) R.drawable.baseline_favorite_red else R.drawable.outline_favorite_border_24)
                textLikes.text = transform(post.likes)
            }

            imageShare.setOnClickListener {
                post.share++
                textViewShare.text = transform(post.share)
            }

        }

    }


    fun transform(number: Int): String {
        var x = number.toString()
        if (number > 999) {
            x = String.format((number / 1000).toString() + "K")
        }
        if (number > 1099) {
            x = String.format("%1.1fK", (number / 100).toDouble()/10)
        }
        if (number > 9999) {
            x = String.format((number / 1000).toString() + "K")
        }
        if (number > 999999) {
            x = String.format("%1.1fM", number.toDouble() / 1000000)
        }

        return x;
    }

}
