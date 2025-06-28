package com.luizeduardobrandao.mybooks.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.luizeduardobrandao.mybooks.entity.BookEntity
import com.luizeduardobrandao.mybooks.repository.BookRepository

class FavoriteViewModel(application: Application) : AndroidViewModel(application) {

    // _books é o MutableLiveData interno, permitimos mutação apenas aqui
    private val _books = MutableLiveData<List<BookEntity>>()
    // books é o LiveData público, somente leitura para a UI observar
    val books: LiveData<List<BookEntity>> = _books

    // Instância do repositório que fornece os dados (fonte única de verdade)
    private val repository = BookRepository.getInstance(application.applicationContext)

    // Chamada pela UI (HomeFragment) para carregar os livros favoritos.
    // Ao final, atualiza _books, disparando a notificação a qualquer observador.
    fun getFavoriteBooks() {

        // Busca a lista de livros no repositório
        val lista = repository.getFavoriteBooks()
        // Atribui ao LiveData interno, acionando observers na UI
        _books.value = lista
    }

    // Função para favoritar um livro sem observer()
    fun favorite(id: Int) {
        repository.toggleFavoriteStatus(id)
    }
}