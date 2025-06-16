package com.luizeduardobrandao.mybooks.ui

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.luizeduardobrandao.mybooks.R
import com.luizeduardobrandao.mybooks.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    // View Binding: referência às views definidas em activity_main.xml
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // chamando o EdgeToEdge
        enableEdgeToEdge()

        // 1. Infla o layout e inicializa o binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        // 2. Define a raiz do binding como conteúdo da Activity
        setContentView(binding.root)

        // Detectando as bordas do sistema ajusta o top e o bottom
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // chamando função de navegação
        setUpNavigation()
    }

    private fun setUpNavigation(){
        // 3. Obtém a referência ao BottomNavigationView (barra inferior)
        val navView: BottomNavigationView = binding.navView

        // 4. Encontra o NavController associado ao NavHostFragment
        //    O NavHostFragment é o container que troca os Fragments

        // 5. Configura os destinos de topo (não mostrará seta de "voltar" para eles)
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home,           // HomeFragment
                R.id.navigation_dashboard,      // DashboardFragment
            )
        )

        // 6. Liga a ActionBar (Toolbar padrão) ao NavController
        //    - Atualiza o título conforme muda de Fragment
        //    - Controla a exibição da seta de back
        setupActionBarWithNavController(navController, appBarConfiguration)

        // 7. Faz o BottomNavigationView reagir às mudanças de destino:
        //    - Quando o usuário clica em um item, navega para o Fragment
        //    - Quando a navegação ocorre por código ou Deep Link,
        //      o item selecionado na barra é atualizado
        navView.setupWithNavController(navController)
    }
}