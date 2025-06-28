package com.luizeduardobrandao.mybooks.repository

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class BookDatabaseHelper(context: Context): SQLiteOpenHelper(
    context, "BookDB", null, 1
){
    // é chamado apenas no primeiro acesso ao banco de dados, uma vez chamado nunca mais é chamado.
    // É o momento da criação do banco de dados.
    override fun onCreate(db: SQLiteDatabase?) {
        TODO("Not yet implemented")
    }

    // quando altera a versão do banco de dados, este mét0do é chamado.
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }

}