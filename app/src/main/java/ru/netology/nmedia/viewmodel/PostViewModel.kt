package ru.netology.nmedia.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.repository.PostRepository
import ru.netology.nmedia.repository.PostRepositoryInMemoryImpl


val empty = Post(
    id = 0,
    author = "",
    published = "",
    content = "",

    )

class PostViewModel : ViewModel() {
    // упрощённый вариант
    private val repository: PostRepository = PostRepositoryInMemoryImpl()
    val data = repository.getAll()
    var edited = MutableLiveData(empty)


    fun changeContentAndSave(content: String) {
        edited.value?.let {
            if (content != it.content) {
                repository.save(it.copy(content = content))
            }
            edited.value = empty
        }

    }

    fun likeById(id: Long) = repository.likeById(id)

    fun share(id: Long) = repository.share(id)

    fun play(post: Post) = repository.play(post)
    fun removeById(id: Long) = repository.removeById(id)

    fun cancell() {
        edited.value = empty
    }

    fun edit(post: Post) {
        edited.value = post
    }
}