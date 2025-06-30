package com.luizeduardobrandao.mybooks.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.luizeduardobrandao.mybooks.R
import com.luizeduardobrandao.mybooks.databinding.FragmentAddBookBinding
import com.luizeduardobrandao.mybooks.viewmodels.AddBookViewModel

class AddBookFragment: Fragment() {

    private var _binding: FragmentAddBookBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AddBookViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddBookBinding.inflate(inflater, container, false)
        setHasOptionsMenu(false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // popula spinner
        val genres = resources.getStringArray(R.array.genres_array)
        binding.spinnerGenre.adapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, genres)

        // TextWatchers + listener de Spinner para habilitar o botão
        val watcher = object: TextWatcher {
            override fun afterTextChanged(s: Editable?) = checkInputs()
            override fun beforeTextChanged(s: CharSequence?, st: Int, c: Int, a: Int) {}
            override fun onTextChanged(s: CharSequence?, st: Int, b: Int, c: Int) {}
        }
        binding.etTitle.addTextChangedListener(watcher)
        binding.etAuthor.addTextChangedListener(watcher)
        binding.spinnerGenre.onItemSelectedListener =
            object: android.widget.AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: android.widget.AdapterView<*>, view: View?, pos: Int, id: Long
                ) = checkInputs()
                override fun onNothingSelected(parent: android.widget.AdapterView<*>) {}
            }

        // 1) dispara o salvamento
        binding.btnSave.setOnClickListener {
            val title = binding.etTitle.text.toString().trim()
            val author = binding.etAuthor.text.toString().trim()
            val genre = binding.spinnerGenre.selectedItem as String
            viewModel.saveBook(title, author, genre)
        }

        // 2) observa o resultado
        viewModel.saveResult.observe(viewLifecycleOwner) { success ->
            if (success) {
                Toast.makeText(requireContext(), "Livro salvo!", Toast.LENGTH_SHORT).show()
                findNavController().popBackStack()
            } else {
                Toast.makeText(requireContext(), "Erro ao salvar", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun checkInputs() {
        binding.btnSave.isEnabled =
            binding.etTitle.text.isNotBlank() &&
                    binding.etAuthor.text.isNotBlank() &&
                    binding.spinnerGenre.selectedItemPosition >= 0
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}