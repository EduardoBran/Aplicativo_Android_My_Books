package com.luizeduardobrandao.mybooks.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.luizeduardobrandao.mybooks.repository.BookRepository
import kotlinx.coroutines.launch

class AddBookViewModel(application: Application): AndroidViewModel(application) {

    private val repo = BookRepository.getInstance(application)

    // LiveData para indicar sucesso/erro ao salvar
    private val _saveResult = MutableLiveData<Boolean>()
    val saveResult: LiveData<Boolean> = _saveResult

    /** Chama o repositório de forma assíncrona e publica o resultado em saveResult */
    fun saveBook(title: String, author: String, genre: String) {
        viewModelScope.launch {
            // repo.addBook é suspend, então aguardamos aqui
            val ok = repo.addBook(title, author, genre)
            _saveResult.postValue(ok)
        }
    }
}