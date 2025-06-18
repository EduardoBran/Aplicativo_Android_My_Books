package com.luizeduardobrandao.mybooks.ui.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.luizeduardobrandao.mybooks.R
import com.luizeduardobrandao.mybooks.databinding.ItemBookBinding
import com.luizeduardobrandao.mybooks.entity.BookEntity
import com.luizeduardobrandao.mybooks.ui.listener.BookListener

class BookViewHolder(private val item: ItemBookBinding, private val listener: BookListener): RecyclerView.ViewHolder(item.root) {

    // Função para implementação da lista de valores dos elementos (livros)
    fun bind(book: BookEntity){
        item.textviewTitle.text = book.title
        item.textviewAuthor.text = book.author
        item.textviewGenre.text = book.genre

        // Navegação para fragment DetailsFragment (clicar em object para implementar os membros)
        item.textviewTitle.setOnClickListener(object: View.OnClickListener{
            override fun onClick(v: View?) {
                // chama onClick passando o id do livro
                listener.onClick(book.id)
            }

        })

        setGenreBackground(book.genre)
        updateFavoriteIcon(book.favorite)
    }

    // Lógica para alterar cor das tags de genero
    private fun setGenreBackground(genre: String){

        if (genre == "Aventura"){
            item.textviewGenre.setBackgroundResource(R.drawable.rounded_label_aventura)
        }
        if (genre == "Contos"){
            item.textviewGenre.setBackgroundResource(R.drawable.rounded_label_contos)
        }
        if (genre == "Distopia"){
            item.textviewGenre.setBackgroundResource(R.drawable.rounded_label_distopia)
        }
        if (genre == "Fantasia"){
            item.textviewGenre.setBackgroundResource(R.drawable.rounded_label_fantasy)
        }
        if (genre == "Ficção"){
            item.textviewGenre.setBackgroundResource(R.drawable.rounded_label_ficcao)
        }
        if (genre == "Infantil"){
            item.textviewGenre.setBackgroundResource(R.drawable.rounded_label_infantil)
        }
        if (genre == "Mistério"){
            item.textviewGenre.setBackgroundResource(R.drawable.rounded_label_misterio)
        }
        if (genre == "Romance"){
            item.textviewGenre.setBackgroundResource(R.drawable.rounded_label_romance)
        }
    }

    private fun updateFavoriteIcon(favorite: Boolean){
        if (favorite){
            item.imageviewFavorite.setImageResource(R.drawable.ic_favorite)
        }
        else {
            item.imageviewFavorite.setImageResource(R.drawable.ic_favorite_empty)
        }
    }
}