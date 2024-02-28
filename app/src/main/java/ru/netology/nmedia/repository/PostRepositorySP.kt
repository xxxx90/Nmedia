package ru.netology.nmedia.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.viewmodel.empty
import java.lang.reflect.Type
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

class PostRepositorySP (
    context: Context
) : PostRepository {

    private val gson = Gson()
    private val prefs =context.getSharedPreferences("repo", Context.MODE_PRIVATE)
    private val type = TypeToken.getParameterized(List::class.java, Post::class.java).type
    private val key = "posts"

    private var nextId = 1L
    private var posts = emptyList<Post>()
        private set(value) {
            field = value
            data.value=value
            sync()
        }

//        listOf(
//        Post(
//            id = nextId++,
//            author = "Нетология. Университет интернет-профессий будущего",
//            content = "Знаний хватит на всех: на следующей неделе разбираемся с разработкой мобильных приложений. \n Конечно это очень сложно, но не на столько, что бы с этим нельзя было справиться!",
//            published = "16 сентября в 10:13",
//            likes = 1999,
//            likedByMe = false,
//            share = 1,
//            videoUrl = "https://www.youtube.com/shorts/-WNg-R3AkDY"
//        ),
//        Post(
//            id = nextId++,
//            author = "Нетология. Университет интернет-профессий будущего",
//            content = "Знаний хватит на всех: на следующей неделе разбираемся с разработкой мобильных приложений. \n Конечно это очень сложно, но не на столько, что бы с этим нельзя было справиться!",
//            published = "18 сентября в 10:13",
//            likes = 1,
//            likedByMe = false,
//            share = 999,
//        ),
//
//
//        Post(
//            id = nextId++,
//            author = "Нетология. Университет интернет-профессий будущего",
//            content = "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия — помочь встать на путь роста и начать цепочку перемен → http://netolo.gy/fyb",
//            published = "21 мая в 18:36",
//            likes = 999,
//            likedByMe = false
//        ),
//    ).reversed()


    private val data = MutableLiveData(posts)

    init {
      prefs.getString(key, null)?.let {
posts = gson.fromJson(it, type)
          nextId = posts.maxOfOrNull { it.id }?.inc() ?: 1
          data.value = posts

      }
    }

    override fun getAll(): LiveData<List<Post>> = data

    override fun likeById(id: Long) {
        posts = posts.map {
            if (it.id != id) it else it.copy(
                likedByMe = !it.likedByMe,
                likes = if (it.likedByMe) it.likes - 1 else it.likes + 1
            )
        }
        data.value = posts
    }


    override fun share(id: Long) {
        posts = posts.map {
            (if (it.id != id) it else {
                it.copy(share = it.share + 1)
            })
        }
        data.value = posts
    }

    override fun save(post: Post) {

        if (post.id == 0L) {
            posts = listOf(post.copy(id = nextId++, published = (SimpleDateFormat("dd.M.yyyy в hh:mm:ss").format(
                Date()
            )).toString(), author = "Netology")) + posts
        } else {
            posts = posts.map {
                if (it.id != post.id) it else it.copy(content = post.content)
            }

        }

        data.value = posts
    }

    override fun play(post: Post) {

    }


//    override fun cancell(post: Post) {    }


    override fun removeById(id: Long) {
        posts = posts.filter { it.id != id }
        data.value = posts

    }

    private fun sync() {
        prefs.edit().apply{
            putString(key,gson.toJson(posts))
            apply()

        }

    }
}