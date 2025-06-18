package com.luizeduardobrandao.mybooks.ui

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.luizeduardobrandao.mybooks.viewmodels.DetailsViewModel
import com.luizeduardobrandao.mybooks.databinding.FragmentDetailsBinding
import com.luizeduardobrandao.mybooks.helper.BookConstants

class DetailsFragment : Fragment() {

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: DetailsViewModel by viewModels()

    // Variável para recuperação de valor passado como informação (Id do Livro)
    private var bookId = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentDetailsBinding.inflate(inflater, container, false)

        // Reage aos listenners
        setListeners()

        // Define observadores para reagir a mudanças nos dados
        setObservers()

        // Recuperando dados passado como informação (id do livro)
        bookId = arguments?.getInt(BookConstants.KEY.BOOK_ID) ?: 0
        // Chamando metodo criado em "DetailsViewModel" para usar viewModel
        // e recuperar o valor passado (id do livro)
        viewModel.getBooksById(bookId)


        return binding.root
    }

    // Quando a view do fragmento é destruída, limpamos o binding para evitar vazamentos de memória.
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    // Observa o LiveData exposto pelo ViewModel através de "getBooksId()" de "DetailsViewModel"
    private fun setObservers(){
        // itens que serão observados
        viewModel.book.observe(viewLifecycleOwner){
            // o que acontece quando receber o id
            binding.textviewTitle.text = it.title
            binding.textviewAuthorValue.text = it.author
            binding.textviewGenreValue.text = it.genre
            binding.checkboxFavorite.isChecked = it.favorite
        }
    }

    // Função para reagir aos listeners
    private fun setListeners(){

        // implementando lógica do botão de voltar (também serve para arrastar o dedo para voltar)
        binding.imageviewBack.setOnClickListener{
            requireActivity().supportFragmentManager.popBackStack()
        }
    }
}