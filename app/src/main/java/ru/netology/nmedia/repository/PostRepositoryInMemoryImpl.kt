package ru.netology.nmedia.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.dto.Post

class PostRepositoryInMemoryImpl : PostRepository {
    private var posts = listOf(
        Post(
            id = 3,
            author = "Нетология. Университет интернет-профессий будущего",
            content = "Знаний хватит на всех: на следующей неделе разбираемся с разработкой мобильных приложений. \n Конечно это очень сложно, но не на столько, что бы с этим нельзя было справиться!",
            published = "16 сентября в 10:13",
            likes = 1999,
            likedByMe = false,
            share = 1,
        ),
        Post(
            id = 2,
            author = "Нетология. Университет интернет-профессий будущего",
            content = "Знаний хватит на всех: на следующей неделе разбираемся с разработкой мобильных приложений. \n Конечно это очень сложно, но не на столько, что бы с этим нельзя было справиться!",
            published = "18 сентября в 10:13",
            likes = 1,
            likedByMe = false,
            share = 999,
        ),



         Post(
        id = 1,
        author = "Нетология. Университет интернет-профессий будущего",
        content = "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия — помочь встать на путь роста и начать цепочку перемен → http://netolo.gy/fyb",
        published = "21 мая в 18:36",
        likes = 999,
        likedByMe = false
    ),
    )
    private val data = MutableLiveData(posts)

    override fun getAll(): LiveData<List<Post>> = data

    override fun likeById(id: Long) {
        posts = posts.map {
         if (it.id != id) it else it.copy(likedByMe = !it.likedByMe, likes = if (it.likedByMe) it.likes-1 else it.likes+1)
        }
        data.value = posts
    }


    override fun share(id: Long) {
        posts = posts.map {
            (if (it.id!=id) it else {it.copy(share= it.share+1)})
        }
        data.value = posts
    }
}