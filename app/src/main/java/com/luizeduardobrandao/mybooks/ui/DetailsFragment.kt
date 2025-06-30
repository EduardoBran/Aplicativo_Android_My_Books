package com.luizeduardobrandao.mybooks.ui

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.luizeduardobrandao.mybooks.R
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
    private fun setObservers() {
        // itens que serão observados
        viewModel.book.observe(viewLifecycleOwner) {
            // o que acontece quando receber o id
            binding.textviewTitle.text = it.title
            binding.textviewAuthorValue.text = it.author
            binding.textviewGenreValue.text = it.genre
            binding.checkboxFavorite.isChecked = it.favorite

            // chamando função para trocar cor das tags de genero
            setGenreBackground(it.genre)

            // chamando função para remover livro
            viewModel.bookRemove.observe(viewLifecycleOwner) {
                if (it) {
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.msg_removed_successfully),
                        Toast.LENGTH_SHORT
                    ).show()

                    // Retonar a mesma página após confirmação de remoção ou não remoção
                    requireActivity().supportFragmentManager.popBackStack()
                }
            }
        }
    }

    // Função para reagir aos listeners
    private fun setListeners() {

        // implementando lógica do botão de voltar (também serve para arrastar o dedo para voltar)
//        binding.imageviewBack.setOnClickListener {
//            requireActivity().supportFragmentManager.popBackStack()
//        }

        // Botão "Remover livro" usando função "handleMove()"
        binding.buttonRemove.setOnClickListener { handleRemove() }

        // Checkbox para favoritar livro usando função "handleFavorite()"
        binding.checkboxFavorite.setOnClickListener { handleFavorite() }
    }

    // Função para favoritar um Livro usando função "favorite()" de "DetailsViewModel"
    private fun handleFavorite() {
        viewModel.favorite(bookId)
    }

    // Função para remover livro usando AlertDialog e função "deleteBook()" de "DetailsViewModel"
    private fun handleRemove() {
        val builder = AlertDialog.Builder(requireContext())

        // definir comportamento
        builder.setMessage(getString(R.string.dialog_message_delete_item))
            .setPositiveButton(getString(R.string.dialog_positive_button_yes)) { dialog, which ->
                viewModel.deleteBook(bookId)
            }
            .setNegativeButton(getString(R.string.dialog_negative_button_no)) { dialog, which ->
                dialog.dismiss()
            }

        // Exibir o dialog
        builder.create().show()
    }


    // Lógica para alterar cor das tags de genero
    private fun setGenreBackground(genre: String) {

        if (genre == "Aventura") {
            binding.textviewGenreValue.setBackgroundResource(R.drawable.rounded_label_aventura)
        }
        if (genre == "Contos") {
            binding.textviewGenreValue.setBackgroundResource(R.drawable.rounded_label_contos)
        }
        if (genre == "Distopia") {
            binding.textviewGenreValue.setBackgroundResource(R.drawable.rounded_label_distopia)
        }
        if (genre == "Fantasia") {
            binding.textviewGenreValue.setBackgroundResource(R.drawable.rounded_label_fantasy)
        }
        if (genre == "Ficção") {
            binding.textviewGenreValue.setBackgroundResource(R.drawable.rounded_label_ficcao)
        }
        if (genre == "Infantil") {
            binding.textviewGenreValue.setBackgroundResource(R.drawable.rounded_label_infantil)
        }
        if (genre == "Mistério") {
            binding.textviewGenreValue.setBackgroundResource(R.drawable.rounded_label_misterio)
        }
        if (genre == "Romance") {
            binding.textviewGenreValue.setBackgroundResource(R.drawable.rounded_label_romance)
        }
    }
}