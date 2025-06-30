package com.luizeduardobrandao.mybooks.repository

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.luizeduardobrandao.mybooks.entity.BookEntity
import kotlinx.coroutines.flow.Flow

// fazer consultas

@Dao
interface BookDAO {

    // retornar todos os livros
    @Query("SELECT * FROM Book")
    fun getAllBooks(): Flow<List<BookEntity>>

    // retornar livros favoritos
    @Query("SELECT * FROM Book WHERE favorite = 1")
    fun getFavoriteBooks(): Flow<List<BookEntity>>

    // retorna livros pelo id
    @Query("SELECT * FROM Book WHERE id = :id")
    suspend fun getBookById(id: Int): BookEntity

    // atualizacao de livros favoritos
    @Update
    suspend fun update(book: BookEntity)

    // remoção de livros
    @Delete
    suspend fun delete(book: BookEntity): Int  // retornar : Int para saber quantos livros foram removidos.

    // inserir todos os livros no momento da criação
    @Insert
    suspend fun insert(book: List<BookEntity>)

    // inserir um único livro
    @Insert
    suspend fun insertOne(book: BookEntity): Long
}