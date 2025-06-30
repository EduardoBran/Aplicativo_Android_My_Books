package com.luizeduardobrandao.mybooks.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.luizeduardobrandao.mybooks.R
import com.luizeduardobrandao.mybooks.databinding.FragmentHomeBinding
import com.luizeduardobrandao.mybooks.helper.BookConstants
import com.luizeduardobrandao.mybooks.ui.adapter.BookAdapter
import com.luizeduardobrandao.mybooks.ui.listener.BookListener
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

        // 1) Configura o LayoutManager da RecyclerView
        //    - LinearLayoutManager exibe itens em lista vertical
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        // 2) Infla o layout e inicializa o binding
        binding.recyclerviewBooks.layoutManager = LinearLayoutManager(context)

        // 3) Atribui o adapter à RecyclerView
        binding.recyclerviewBooks.adapter = adapter

        // 4) Função para Navegação
        attachListener()

        // 6) Define observadores para reagir a mudanças nos dados
        setObservers()

        // 7) Retorna a raiz inflada para ser exibida
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        // 5) Solicita ao ViewModel que busque todos os livros
        homeViewModel.getAllBooks()
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
        homeViewModel.books.observe(viewLifecycleOwner){ list ->
            if (list.isEmpty()){
                // não encontrou nada
                binding.recyclerviewBooks.visibility = View.GONE
                binding.textviewNoResults.visibility = View.VISIBLE
            }
            else {
                // encontrou resultados
                binding.textviewNoResults.visibility = View.GONE
                binding.recyclerviewBooks.visibility = View.VISIBLE
                adapter.updateBooks(list)
            }

        }
    }

    // Função para navegação  (clicar em object para implementar os membros)
    private fun attachListener() {
        adapter.attachListener(object: BookListener{
            override fun onClick(id: Int) {

                // navegação passando o ID do livro como parametro
                val bundle = Bundle()
                bundle.putInt(BookConstants.KEY.BOOK_ID, id)

                // navegação
                findNavController().navigate(R.id.navigation_details, bundle)
            }

            override fun onFavoriteClick(id: Int) {
                // clique para favoritar o livro
                homeViewModel.favorite(id)
                // atualiza a listagem com o livro marcado como favorito
                homeViewModel.getAllBooks()
            }

        })
    }

    // Chamado pela Activity para iniciar a busca.
    fun searchByTitle(query: String) {
        homeViewModel.searchByTitle(query)
    }

    // Restaura a lista completa de livros e esconde a mensagem de "nenhum resultado".
    fun resetList() {
        homeViewModel.getAllBooks()
        binding.textviewNoResults.visibility = View.GONE
        binding.recyclerviewBooks.visibility = View.VISIBLE
    }
}

/*

*** Navegação

- 1. Começar por "BookViewHolder" criando o "setOnClickListener()"
- 2. Criar variável "bookListener" e função "attachListement" em "BookAdapter"
- 3. Criar e chamar função "attachListement" aqui em "HomeFragment"
- 4. Dentro de "attachListenent() / "onClick" usar mét0do de navegação "findNavController().navigate()"
     e passar "R.id.navigation_details" que foi criado em "mobile_navigation"

* Explicando

- função "attachListener" é chamado dentro de onCreateView e é passado por
  parâmetro para "BookAdapter"
- o "BookAdapter" passa a "BookViewHolder"
- o "BookViewHolder" recebe o código e quando houver um clique no item "textviewTitle" dispara o
  "listenner.onClick()" que será chamado aqui em "HomeFragment" com "onClick" de "attachListener()"

 */