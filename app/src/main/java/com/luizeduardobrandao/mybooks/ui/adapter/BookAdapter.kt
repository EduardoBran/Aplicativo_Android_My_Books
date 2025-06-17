package com.luizeduardobrandao.mybooks.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.luizeduardobrandao.mybooks.databinding.ItemBookBinding
import com.luizeduardobrandao.mybooks.entity.BookEntity
import com.luizeduardobrandao.mybooks.ui.viewholder.BookViewHolder

class BookAdapter: RecyclerView.Adapter<BookViewHolder>() {

    private val bookList = mutableListOf<BookEntity>()

    // É responsável por criar o elemento de layout
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val view = ItemBookBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BookViewHolder(view)
    }

    // Retorna quantos elementos existem na Recycler View
    override fun getItemCount(): Int {
        return bookList.size
    }

    // É responsável por atribuir/obter os valores dos atributos (livros) para o layout
    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        holder.bind(bookList[position])
    }
}