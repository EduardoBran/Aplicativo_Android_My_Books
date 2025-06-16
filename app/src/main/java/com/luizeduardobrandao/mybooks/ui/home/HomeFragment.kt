package com.luizeduardobrandao.mybooks.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.luizeduardobrandao.mybooks.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    // Guarda a instância do binding de forma opcional
    private var _binding: FragmentHomeBinding? = null

    // Getter não-nulo para usar o binding dentro das chamadas de ciclo de vida válidas
    // (entre onCreateView e onDestroyView)
    private val binding get() = _binding!!

    // Responsável por criar o layout
    override fun onCreateView(
        inflater: LayoutInflater,     // Inflater para criar a View a partir do XML
        container: ViewGroup?,        // Container pai que irá receber este Fragment
        savedInstanceState: Bundle?   // Estado anterior, se houver
    ): View {

        // 1) Obter o ViewModel associado a este Fragment
        //    - ViewModelProvider(this) cria ou recupera um HomeViewModel atrelado ao ciclo de vida do Fragment
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        // 2) Inflar o layout usando ViewBinding
        //    - FragmentHomeBinding foi gerado automaticamente a partir de fragment_home.xml
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        //    - binding.root é a raiz do layout inflado
        val root: View = binding.root

        // 3) Obter referência ao TextView que exibirá o texto vindo do ViewModel
        val textView: TextView = binding.textHome

        // 4) Observar o LiveData<String> “text” no ViewModel
        //    Sempre que “text” emitir um novo valor, este bloco será executado
        homeViewModel.text.observe(viewLifecycleOwner) {
            // 5) Atualizar o conteúdo do TextView com o valor recebido
            textView.text = it
        }
        // 6) Retornar a View raiz para que o sistema exiba o fragment
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // 7) Limpar o binding para evitar memory leaks
        _binding = null
    }
}