package com.luizeduardobrandao.mybooks.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.luizeduardobrandao.mybooks.databinding.FragmentHomeBinding
import com.luizeduardobrandao.mybooks.ui.adapter.BookAdapter
import com.luizeduardobrandao.mybooks.viewmodels.HomeViewModel

// Fragmento responsável por exibir a lista de livros em uma RecyclerView.
// Segue o padrão MVVM: observa dados no ViewModel e atualiza a UI automaticamente.
class HomeFragment : Fragment() {

    // Binding do layout gerado automaticamente a partir de fragment_home.xml
    // _binding é nullable para permitir limpeza em onDestroyView()
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!              // acesso seguro entre onCreateView e onDestroyView

    // ViewModel do fragmento, criado uma única vez e atrelado ao ciclo de vida do Fragment
    private val homeViewModel: HomeViewModel by viewModels()

    // Adapter que gerencia itens da RecyclerView (BookAdapter lida com a exibição de BookEntity)
    private val adapter: BookAdapter = BookAdapter()

    // Chamado para criar a interface do fragmento.
    // Aqui configuramos ViewBinding, RecyclerView e observadores.
    override fun onCreateView(
        inflater: LayoutInflater,     // Inflater para criar a View a partir do XML
        container: ViewGroup?,        // Container pai que irá receber este Fragment
        savedInstanceState: Bundle?   // Estado anterior, se houver
    ): View {

        // 1) Infla o layout e inicializa o binding
        binding.recyclerviewBooks.layoutManager = LinearLayoutManager(context)

        // 2) Configura o LayoutManager da RecyclerView
        //    - LinearLayoutManager exibe itens em lista vertical
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        // 3) Atribui o adapter à RecyclerView
        binding.recyclerviewBooks.adapter = adapter

        // 4) Solicita ao ViewModel que busque todos os livros
        homeViewModel.getAllBooks()

        // 5) Define observadores para reagir a mudanças nos dados
        setObservers()

        // 6) Retorna a raiz inflada para ser exibida
        return binding.root
    }

    // Quando a view do fragmento é destruída, limpamos o binding para evitar vazamentos de memória.
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    // Observa o LiveData<List<BookEntity>> exposto pelo ViewModel
    // Sempre que a lista de livros for atualizada, atualiza os dados do adapter
    private fun setObservers() {

        // Atualiza a lista de livros exibida pela RecyclerView
        homeViewModel.books.observe(viewLifecycleOwner){
            adapter.updateBooks(it)
        }
    }
}