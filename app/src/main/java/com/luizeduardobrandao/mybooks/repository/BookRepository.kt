package com.luizeduardobrandao.mybooks.repository

import android.content.Context
import com.luizeduardobrandao.mybooks.entity.BookEntity
import com.luizeduardobrandao.mybooks.helper.DatabaseConstants

// Classe responsável por armazenar e manipular os livros.
// (Simula um banco de dados local usando uma lista mutável.)
class BookRepository private constructor(context: Context) {

    // criando instancio para o banco de dados
    private var database = BookDatabaseHelper(context)

    // Lista mutável que armazena os livros
    private val books = mutableListOf<BookEntity>()

    // Popula o repositório com os 10 livros iniciais
    //init {
    //    database.readableDatabase
    //}

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
        val db = database.readableDatabase
        val books = mutableListOf<BookEntity>() // lista a ser preenchida

        // listagem para os livros (passo a passo)

        // cursor passando a query (navegando nos dados)
        val cursor = db.query(DatabaseConstants.BOOK.TABLE_NAME, null, null, null, null, null, null)



        // criar a lista (verificando se o cursor esta vazio e adicionando valores)
        if (cursor.moveToFirst()){ // retorna falso se está vazio
            do{
                // dados
                val id = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseConstants.BOOK.COLUMNS.ID))
                val title = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseConstants.BOOK.COLUMNS.TITLE))
                val author = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseConstants.BOOK.COLUMNS.AUTHOR))
                val genre = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseConstants.BOOK.COLUMNS.GENRE))
                val favorite: Boolean =  cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseConstants.BOOK.COLUMNS.FAVORITE)) == 1

                books.add(BookEntity(id, title, author, favorite, genre))
            } while (cursor.moveToNext())
        }

        // fechar db e cursor
        cursor.close()
        db.close()

        // return
        return books
    }

    // Retorna todos os livros marcados como favoritos
    fun getFavoriteBooks(): List<BookEntity> {
        val db = database.readableDatabase
        val books = mutableListOf<BookEntity>() // lista a ser preenchida

        // listagem para os livros (passo a passo)

        // cursor passando a query (navegando nos dados)
        val cursor = db.query(
            DatabaseConstants.BOOK.TABLE_NAME,
            null,
            "${DatabaseConstants.BOOK.COLUMNS.FAVORITE} = ?",
            arrayOf("1"),
            null,
            null,
            null)



        // criar a lista (verificando se o cursor esta vazio e adicionando valores)
        if (cursor.moveToFirst()){ // retorna falso se está vazio
            do{
                // dados
                val id = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseConstants.BOOK.COLUMNS.ID))
                val title = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseConstants.BOOK.COLUMNS.TITLE))
                val author = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseConstants.BOOK.COLUMNS.AUTHOR))
                val genre = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseConstants.BOOK.COLUMNS.GENRE))
                val favorite: Boolean =  cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseConstants.BOOK.COLUMNS.FAVORITE)) == 1

                books.add(BookEntity(id, title, author, favorite, genre))
            } while (cursor.moveToNext())
        }

        // fechar db e cursor
        cursor.close()
        db.close()

        // return
        return books
    }

    // Busca um livro pelo ID
    fun getBookById(id: Int): BookEntity? {
        val db = database.readableDatabase


        // listagem para os livros (passo a passo)

        // cursor passando a query (navegando nos dados)
        val cursor = db.query(
            DatabaseConstants.BOOK.TABLE_NAME,
            null,
            "${DatabaseConstants.BOOK.COLUMNS.ID} = ?",
            arrayOf(id.toString()),
            null,
            null,
            null)

        var book: BookEntity? = null
        // criar a lista (verificando se o cursor esta vazio e adicionando valores)
        if (cursor.moveToFirst()){ // retorna falso se está vazio
            // dados
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseConstants.BOOK.COLUMNS.ID))
            val title = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseConstants.BOOK.COLUMNS.TITLE))
            val author = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseConstants.BOOK.COLUMNS.AUTHOR))
            val genre = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseConstants.BOOK.COLUMNS.GENRE))
            val favorite: Boolean =  cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseConstants.BOOK.COLUMNS.FAVORITE)) == 1

            book = BookEntity(id, title, author, favorite, genre)
        }

        // fechar db e cursor
        cursor.close()
        db.close()

        // return
        return book
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