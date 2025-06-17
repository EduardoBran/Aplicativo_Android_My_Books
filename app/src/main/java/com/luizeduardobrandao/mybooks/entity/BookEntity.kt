package com.luizeduardobrandao.mybooks.entity

data class BookEntity (
    val id: Int,
    val title: String,
    val author: String,
    var favorite: Boolean,
    val genre: String
)

// var para favorite porque o usuário PODE alterar o favorito.