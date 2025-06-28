package com.luizeduardobrandao.mybooks.repository

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.luizeduardobrandao.mybooks.helper.DatabaseConstants

class BookDatabaseHelper(context: Context): SQLiteOpenHelper(
    context, DATABASE_NAME, null, DATABASE_VERSION
){
    // é chamado apenas no primeiro acesso ao banco de dados, uma vez chamado nunca mais é chamado.
    // É o momento da criação do banco de dados.
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE_TABLE_BOOKS)
    }

    // quando altera a versão do banco de dados, este mét0do é chamado.
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }

    companion object {
        private const val DATABASE_NAME = "booksDB"
        private const val DATABASE_VERSION = 1

        // código sql para criar o banco de dados
        private const val CREATE_TABLE_BOOKS = """
            CREATE TABLE ${DatabaseConstants.BOOK.TABLE_NAME}(
            ${DatabaseConstants.BOOK.COLUMNS.ID} INTEGER PRIMARY KEY AUTOINCREMENT,
            ${DatabaseConstants.BOOK.COLUMNS.TITLE} TEXT NOT NULL,
            ${DatabaseConstants.BOOK.COLUMNS.AUTHOR} TEXT NOT NULL,
            ${DatabaseConstants.BOOK.COLUMNS.GENRE} TEXT NOT NULL,
            ${DatabaseConstants.BOOK.COLUMNS.FAVORITE} INTEGER NOT NULL,  
            );
        """
    }

}