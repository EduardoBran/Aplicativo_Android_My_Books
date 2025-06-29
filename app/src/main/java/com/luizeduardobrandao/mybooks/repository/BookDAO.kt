package com.luizeduardobrandao.mybooks.repository

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.luizeduardobrandao.mybooks.entity.BookEntity

// fazer consultas

@Dao
interface BookDAO {

    // retornar todos os livros
    @Query("SELECT * FROM Book")
    fun getAllBooks(): List<BookEntity>

    // retornar livros favoritos
    @Query("SELECT * FROM Book WHERE favorite = 1")
    fun getFavoriteBooks(): List<BookEntity>

    // retorna livros pelo id
    @Query("SELECT * FROM Book WHERE id = :id")
    fun getBookById(id: Int): BookEntity

    // atualizacao de livros favoritos
    @Update
    fun update(book: BookEntity)

    // remoção de livros
    @Delete
    fun delete(book: BookEntity): Int  // retornar : Int para saber quantos livros foram removidos.

    // inserir todos os livros no momento da criação
    @Insert
    fun insert(book: BookEntity)
}