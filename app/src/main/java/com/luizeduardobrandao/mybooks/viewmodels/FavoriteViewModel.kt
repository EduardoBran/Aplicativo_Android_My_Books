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
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.map

class FavoriteViewModel(application: Application) : AndroidViewModel(application) {

    // Instância do repositório que fornece os dados (fonte única de verdade)
    private val repository = BookRepository.getInstance(application.applicationContext)

    // controla o texto de busca (null ou vazio = sem filtro)
    private val _query = MutableStateFlow<String?>(null)

    // toda vez que o Flow de favoritos ou _query mudar, reemitimos a lista corretamente filtrada e ordenada
    val books: LiveData<List<BookEntity>> = _query
        .flatMapLatest { q ->
            repository
                .getFavoriteBooks()       // Flow<List<BookEntity>>
                .map { list ->
                    // ordena antes de filtrar
                    val sorted = list.sortedBy { it.title }
                    if (q.isNullOrBlank()) sorted
                    else sorted.filter { it.title.contains(q, ignoreCase = true) }
                }
        }
        .asLiveData()

    // Marca/desmarca favorito
    fun favorite(id: Int) = viewModelScope.launch {
        repository.toggleFavoriteStatus(id)
    }

    // Atualiza o filtro de busca; null ou "" = sem filtro
    fun searchByTitle(query: String?) {
        _query.value = query
    }
}