package com.luizeduardobrandao.mybooks.ui.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.luizeduardobrandao.mybooks.databinding.ItemBookBinding
import com.luizeduardobrandao.mybooks.entity.BookEntity

class BookViewHolder(private val item: ItemBookBinding): RecyclerView.ViewHolder(item.root) {

    // Função para implementação da lista de valores dos elementos (livros)
    fun bind(book: BookEntity){
        item.textviewTitle.text = book.title
        item.textviewAuthor.text = book.author
        item.textviewGenre.text = book.genre
    }
}