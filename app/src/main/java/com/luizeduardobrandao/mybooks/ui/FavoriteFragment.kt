package com.luizeduardobrandao.mybooks.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.luizeduardobrandao.mybooks.R
import com.luizeduardobrandao.mybooks.databinding.FragmentFavoriteBinding
import com.luizeduardobrandao.mybooks.helper.BookConstants
import com.luizeduardobrandao.mybooks.ui.adapter.BookAdapter
import com.luizeduardobrandao.mybooks.ui.listener.BookListener
import com.luizeduardobrandao.mybooks.viewmodels.FavoriteViewModel

class FavoriteFragment : Fragment() {

    private var _binding: FragmentFavoriteBinding? = null

    private val binding get() = _binding!!

    private val favoriteViewModel: FavoriteViewModel by viewModels()

    // Adapter que gerencia itens da RecyclerView (BookAdapter lida com a exibição de BookEntity)
    private val adapter: BookAdapter = BookAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)

        // Infla o layout e inicializa o binding
        binding.recyclerviewBooksFavorite.layoutManager = LinearLayoutManager(context)

        // Atribui o adapter à RecyclerView
        binding.recyclerviewBooksFavorite.adapter = adapter

        // Função para Navegação
        attachListener()
        setObservers()

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        // 5) Solicita ao ViewModel que busque todos os livros
        favoriteViewModel.getFavoriteBooks()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setObservers() {

        // Atualiza a lista de livros favoritos exibida pela RecyclerView
        favoriteViewModel.books.observe(viewLifecycleOwner){

            // verificação para ver se lista esta vazia
            if (it.isEmpty()){
                binding.recyclerviewBooksFavorite.visibility = View.GONE // deixando a recycler view invisível
                binding.textviewNoBooks.visibility = View.VISIBLE        // deixando texto visível
                binding.imageviewNoBooks.visibility = View.VISIBLE       // deixando imagem visível
            }
            else {
                binding.recyclerviewBooksFavorite.visibility = View.VISIBLE // deixando a recycler view visível
                binding.textviewNoBooks.visibility = View.GONE              // deixando texto invisível
                binding.imageviewNoBooks.visibility = View.GONE             // deixando imagem invisível
                adapter.updateBooks(it)                                     // exibe os livros
            }
        }
    }

    private fun attachListener() {
        adapter.attachListener(object: BookListener {
            override fun onClick(id: Int) {

                // navegação passando o ID do livro como parametro
                val bundle = Bundle()
                bundle.putInt(BookConstants.KEY.BOOK_ID, id)

                // navegação
                findNavController().navigate(R.id.navigation_details, bundle)
            }

            override fun onFavoriteClick(id: Int) {
                // clique para favoritar o livro
                favoriteViewModel.favorite(id)
                // atualiza a listagem com o livro marcado como favorito
                favoriteViewModel.getFavoriteBooks()
            }

        })
    }

    // Chamado pela Activity para iniciar busca em Favoritos.
    fun searchByTitle(query: String) {
        favoriteViewModel.searchByTitle(query)
    }

    // Restaura lista completa de Favoritos.
    fun resetList() {
        favoriteViewModel.getFavoriteBooks()
    }
}