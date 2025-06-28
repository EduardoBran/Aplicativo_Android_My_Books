package com.luizeduardobrandao.mybooks.repository

import android.content.Context
import com.luizeduardobrandao.mybooks.entity.BookEntity

// Classe responsável por armazenar e manipular os livros.
// (Simula um banco de dados local usando uma lista mutável.)
class BookRepository private constructor(context: Context) {

    // criando instancio para o banco de dados
    private var database = BookDatabaseHelper(context)

    // Lista mutável que armazena os livros
    private val books = mutableListOf<BookEntity>()

    // Popula o repositório com os 10 livros iniciais
    init {
        database.readableDatabase
    }

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

    // Retorna todos os livros armazenados
    fun getAllBooks(): List<BookEntity>{
        return books
    }

    // Retorna todos os livros marcados como favoritos
    fun getFavoriteBooks(): List<BookEntity> {
        return books.filter { it.favorite }
    }

    // Busca um livro pelo ID
    fun getBookById(id: Int): BookEntity? {
        return books.find {it.id == id}
    }

    // Alterna entre true e false o atributo 'favorite'
    fun toggleFavoriteStatus(id: Int) {
        val book = books.find {it.id == id}

        if (book != null){
            book.favorite = !book.favorite    // Alterna entre true e false
        }

    }

    // Remove um livro pelo ID
    // (coloca retorno como Booleano para verificação se removeu com sucesso (true) ou nao (false)
    fun deleteBook(id: Int): Boolean {
        return books.removeIf { it.id == id }
    }
}

/*

*** Padrão Singleton

Em Kotlin, Singleton é um padrão de projeto que garante que uma classe tenha apenas uma única
instância em toda a aplicação e fornece um ponto global de acesso a ela.

- Por que usar Singleton?
    - Estado compartilhado: quando você precisa manter dados ou configurações que devem ser os
      mesmos em todas as telas.
    - Repositórios (em MVVM): por exemplo, um único objeto que faz todas as chamadas de rede ou
      acesso a banco de dados.
    - Economia de recursos: evita criar múltiplos objetos pesados desnecessariamente.

 */