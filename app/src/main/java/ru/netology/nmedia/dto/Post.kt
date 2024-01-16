package ru.netology.nmedia.dto

data class Post(

    val id: Int,
    val autor: String,
    val published: String,
    val content: String,
    var likes: Int = 0,
    var likedByMe: Boolean = false,
    var share: Int = 0,


)