package com.luizeduardobrandao.mybooks.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.luizeduardobrandao.mybooks.repository.BookRepository

class AddBookViewModel(application: Application): AndroidViewModel(application) {

    private val repo = BookRepository.getInstance(application)

    // Retorna true se salvo com sucesso
    fun saveBook(title: String, author: String, genre: String): Boolean {
        return repo.addBook(title, author, genre)
    }
}