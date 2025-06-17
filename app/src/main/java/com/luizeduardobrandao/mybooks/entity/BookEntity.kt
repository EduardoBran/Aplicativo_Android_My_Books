package com.luizeduardobrandao.mybooks.entity

data class BookEntity (
    val id: Int,
    val title: String,
    val author: String,
    val favorite: Boolean,
    val genre: String
)