package com.luizeduardobrandao.mybooks.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.luizeduardobrandao.mybooks.entity.BookEntity
import com.luizeduardobrandao.mybooks.repository.BookRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

// ViewModel responsável por preparar e fornecer a lista de livros à UI,
// mantendo a separação entre lógica de apresentação e camada de dados.
class HomeViewModel(application: Application) : AndroidViewModel(application) {

    // Acesso ao banco de dados
    private val repository = BookRepository.getInstance(application.applicationContext)

    // 1) um StateFlow que guarda o último termo digitado (ou null, para lista completa)
    private val _searchQuery = MutableStateFlow<String?>(null)

    val bookList: LiveData<List<BookEntity>> = repository.getAllBooks().asLiveData()

    // 2) books é um LiveData que reflete o Flow original, mas filtrado se houver um query
    val books: LiveData<List<BookEntity>> = _searchQuery
        .flatMapLatest { query ->
            repository
                .getAllBooks()
                .map { list ->
                    // sempre ordena antes de tudo
                    val sorted = list.sortedBy { it.title }
                    // se não houver query, devolve tudo; senão, filtra sobre a lista já ordenada
                    if (query.isNullOrBlank()) sorted
                    else sorted.filter { it.title.contains(query, ignoreCase = true) }
                }
        }
        .asLiveData()

    // Dispara uma nova busca; passe `null` para voltar à lista completa
    fun searchByTitle(query: String?){
        _searchQuery.value = query
    }

    // Função para favoritar um livro sem observer()
    fun favorite(id: Int) {
        viewModelScope.launch {
            repository.toggleFavoriteStatus(id)
        }
    }

}