package com.luizeduardobrandao.mybooks.ui.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.luizeduardobrandao.mybooks.databinding.ItemBookBinding
import com.luizeduardobrandao.mybooks.entity.BookEntity

class BookViewHolder(private val item: ItemBookBinding): RecyclerView.ViewHolder(item.root) {

    fun bind(book: BookEntity){
        // código para obter os valores de BookEntity
    }
}