package com.luizeduardobrandao.mybooks.repository

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.luizeduardobrandao.mybooks.entity.BookEntity

@Database(entities = [BookEntity::class], version = 1)
abstract class BookDatabase: RoomDatabase() {

    abstract fun bookDAO(): BookDAO

    // Singleton
    companion object {
        private lateinit var instance: BookDatabase
        private const val DATABASE_NAME = "books_db"

        fun getDatabase(context: Context): BookDatabase {
            if (!::instance.isInitialized) {
                synchronized(this) {

                    // construindo banco de ados
                    instance = Room.databaseBuilder(
                        context, BookDatabase::class.java, DATABASE_NAME
                    ).allowMainThreadQueries().build()
                }
            }
            return instance
        }
    }

    // Migrações
    private object Migrations {
        /**
         * Atualização de versão de banco de dados
         */
        val migrationFromV1ToV2: Migration = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                // Limpar o banco de dados
                db.execSQL("DELETE FROM Book")

                // Criar uma nova coluna, atualizar valores, etc etc
                // Qualquer tipo de lógica de banco de dados para garantir a atualização de versão
            }
        }
    }
}

// a classe precisa ser abstract

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