package com.luizeduardobrandao.mybooks.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.luizeduardobrandao.mybooks.entity.BookEntity
import com.luizeduardobrandao.mybooks.repository.BookRepository

// ViewModel responsável por preparar e fornecer a lista de livros à UI,
// mantendo a separação entre lógica de apresentação e camada de dados.
class HomeViewModel(application: Application) : AndroidViewModel(application) {

    // _books é o MutableLiveData interno, permitimos mutação apenas aqui
    private val _books = MutableLiveData<List<BookEntity>>()
    // books é o LiveData público, somente leitura para a UI observar
    val books: LiveData<List<BookEntity>> = _books


    // Acesso ao banco de dados
    private val repository = BookRepository.getInstance(application.applicationContext)

    // iniciando lista de livros salva no repositorio
    init {
        // controle de fluxos para realizar inserção somente se repositorio for vazio
        if (repository.getAllBooks().isEmpty()){
            repository.loadInitialData()
        }
    }


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

    // Pesquisa livros cujo título contém 'query' (ignora maiúsc/minúsc).
    fun searchByTitle(query: String) {
        _books.value = repository
            .getAllBooks().filter { it.title.contains(query, ignoreCase = true) }
    }
}