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

class HomeFragment : Fragment() {

    // Guarda a instância do binding de forma opcional
    private var _binding: FragmentHomeBinding? = null

    // Getter não-nulo para usar o binding dentro das chamadas de ciclo de vida válidas
    // (entre onCreateView e onDestroyView)
    private val binding get() = _binding!!

    // 1) Obter o ViewModel associado a este Fragment
    //    - ViewModelProvider(this) cria ou recupera um HomeViewModel atrelado ao ciclo de vida do Fragment
    private val homeViewModel: HomeViewModel by viewModels()

    // adapter para RecyclerView
    private val adapter: BookAdapter = BookAdapter()

    // Responsável por criar o layout
    override fun onCreateView(
        inflater: LayoutInflater,     // Inflater para criar a View a partir do XML
        container: ViewGroup?,        // Container pai que irá receber este Fragment
        savedInstanceState: Bundle?   // Estado anterior, se houver
    ): View {

        // 3) atribuição do layout de recycle view e dizer como se comporta
        binding.recyclerviewBooks.layoutManager = LinearLayoutManager(context)

        // 4) Inflar o layout usando ViewBinding
        //    - FragmentHomeBinding foi gerado automaticamente a partir de fragment_home.xml
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        // instanciando o adapter
        binding.recyclerviewBooks.adapter = adapter

        // 5) Retornar a View raiz para que o sistema exiba o fragment
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // 6) Limpar o binding para evitar memory leaks
        _binding = null
    }
}