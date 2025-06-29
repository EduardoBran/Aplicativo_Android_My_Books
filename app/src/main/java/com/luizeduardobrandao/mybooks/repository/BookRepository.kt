package com.luizeduardobrandao.mybooks.repository

import android.content.Context
import com.luizeduardobrandao.mybooks.entity.BookEntity

// Classe responsável por armazenar e manipular os livros via bd.
class BookRepository private constructor(context: Context) {

    // criando instancio para o banco de dados
    private var dataBase = BookDatabase.getDatabase(context).bookDAO()

    // Lista mutável que armazena os livros
    // private val books = mutableListOf<BookEntity>()

    // Padrão Singleton ("synchronized" previne que duas requisições cheguem ao mesmo tempo)
    companion object {
        private lateinit var instance: BookRepository

        fun getInstance(context: Context): BookRepository {
            synchronized(this) {
                if (!::instance.isInitialized){
                    instance = BookRepository(context)
                }
            }
            return instance
        }
    }

    // Retorna todos os livros armazenados pelo banco de dados
    fun getAllBooks(): List<BookEntity>{
        return dataBase.getAllBooks()
    }

    // Retorna todos os livros marcados como favoritos
    fun getFavoriteBooks(): List<BookEntity> {
        return dataBase.getFavoriteBooks()
    }

    // Busca um livro pelo ID
    fun getBookById(id: Int): BookEntity {
        return dataBase.getBookById(id)
    }

    // Alterna entre true e false o atributo 'favorite'
    fun toggleFavoriteStatus(id: Int) {
        val book = getBookById(id)
        book.favorite = !book.favorite // inverte o valor de favorito para não favorito
        dataBase.update(book)
    }

    // Remove um livro pelo ID
    // (coloca retorno como Booleano para verificação se removeu com sucesso (true) ou nao (false)
    fun deleteBook(id: Int): Boolean {
        val book = getBookById(id)
        return dataBase.delete(book) > 0
    }
}