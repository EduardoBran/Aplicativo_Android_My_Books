package com.luizeduardobrandao.mybooks.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.luizeduardobrandao.mybooks.entity.BookEntity
import com.luizeduardobrandao.mybooks.repository.BookRepository

// ViewModel responsável por preparar e fornecer a lista de livros à UI,
// mantendo a separação entre lógica de apresentação e camada de dados.
class HomeViewModel : ViewModel() {

    // _books é o MutableLiveData interno, permitimos mutação apenas aqui
    private val _books = MutableLiveData<List<BookEntity>>()
    // books é o LiveData público, somente leitura para a UI observar
    val books: LiveData<List<BookEntity>> = _books

    // Instância do repositório que fornece os dados (fonte única de verdade)
    private val repository = BookRepository.getInstance()

    // Chamada pela UI (HomeFragment) para carregar todos os livros.
    // Ao final, atualiza _books, disparando a notificação a qualquer observador.
    fun getAllBooks() {

        // Busca a lista de livros no repositório
        val lista = repository.getAllBooks()
        // Atribui ao LiveData interno, acionando observers na UI
        _books.value = lista
    }

    // Função para favoritar um livro sem observer()
    fun favorite(id: Int) {
        repository.toggleFavoriteStatus(id)
    }
}