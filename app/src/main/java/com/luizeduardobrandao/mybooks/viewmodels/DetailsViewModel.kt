package com.luizeduardobrandao.mybooks.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.luizeduardobrandao.mybooks.entity.BookEntity
import com.luizeduardobrandao.mybooks.repository.BookRepository
import kotlinx.coroutines.launch

class DetailsViewModel(application: Application) : AndroidViewModel(application) {

    // variável usada para recuperar o valor do id do livro
    private val repository: BookRepository = BookRepository.getInstance(application.applicationContext)

    // variável para atribuir o valor do id para um item que pode ser observado
    private val _book = MutableLiveData<BookEntity>()
    val book: LiveData<BookEntity> = _book

    // variável para atribuir o valor do booleano para remoção de um livro
    private val _bookRemove = MutableLiveData<Boolean>()
    val bookRemove: LiveData<Boolean> = _bookRemove

    // Função para recuperar o valor passado por HomeFragment (id do livro) usando função
    // "getBooksById" implementada em "BookRepository"
    fun getBooksById(id: Int) {
        viewModelScope.launch {
            _book.value = repository.getBookById(id)
        }
    }

    // Função para excluir um livro da lista
    fun deleteBook(id: Int){
        viewModelScope.launch {
            _bookRemove.value = repository.deleteBook(id)
        }
    }

    // Função para favoritar um livro sem observer()
    fun favorite(id: Int) {
        viewModelScope.launch {
            repository.toggleFavoriteStatus(id)
        }
    }
}