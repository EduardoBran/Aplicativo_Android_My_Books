package com.luizeduardobrandao.mybooks.repository

import android.content.Context
import com.luizeduardobrandao.mybooks.entity.BookEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

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
    fun getAllBooks(): Flow<List<BookEntity>> {
        return dataBase.getAllBooks()
    }

    // Retorna todos os livros marcados como favoritos
    fun getFavoriteBooks(): Flow<List<BookEntity>> {
        return dataBase.getFavoriteBooks()
    }

    // Busca um livro pelo ID
    suspend fun getBookById(id: Int): BookEntity {
        return dataBase.getBookById(id)
    }

    // Alterna entre true e false o atributo 'favorite'
    suspend fun toggleFavoriteStatus(id: Int) {
        val book = getBookById(id)
        book.favorite = !book.favorite // inverte o valor de favorito para não favorito
        dataBase.update(book)
    }

    // Remove um livro pelo ID
    // (coloca retorno como Booleano para verificação se removeu com sucesso (true) ou nao (false)
    suspend fun deleteBook(id: Int): Boolean {
        val book = getBookById(id)
        return dataBase.delete(book) > 0
    }

    // Insere um livro e retorna true se OK
    suspend fun addBook(title: String, author: String, genre: String): Boolean {
        // 1) coleta a lista atual de dentro do Flow
        val allBooks: List<BookEntity> = dataBase.getAllBooks().first()

        // 2) calcula o próximo ID
        val nextId = (allBooks.maxOfOrNull { book -> book.id } ?: 0) + 1

        // 3) cria e insere
        val book = BookEntity(nextId, title, author, false, genre)
        dataBase.insertOne(book)
        return true
    }
}