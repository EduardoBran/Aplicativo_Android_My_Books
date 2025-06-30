package com.luizeduardobrandao.mybooks.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageButton
import android.widget.PopupMenu
import android.widget.Toast
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

        // transforma toolbar em ActionBar
        setSupportActionBar(binding.toolbar)

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
        val navController = findNavController(R.id.nav_host_fragment_activity_main)

        // 5. Configura os destinos de topo (não mostrará seta de "voltar" para eles)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home,           // HomeFragment
                R.id.navigation_favorite,      // DashboardFragment
            )
        )

        // 6. Liga a ActionBar (Toolbar padrão) ao NavController
        //    - Atualiza o título conforme muda de Fragment
        //    - Controla a exibição da seta de back
        setupActionBarWithNavController(navController, appBarConfiguration)

        // Garante que o título seja sempre "MyBooks" e edita o Menu em DetailsFragment
        navController.addOnDestinationChangedListener { _, destination, _ ->
            // título fixo
            supportActionBar?.title = getString(R.string.app_name)

            // seta de back apenas fora de Home e Favoritos
            val topLevel = setOf(R.id.navigation_home, R.id.navigation_favorite)
            supportActionBar?.setDisplayHomeAsUpEnabled(!topLevel.contains(destination.id))

            // ⚠️ forçar Android a recriar o menu
            invalidateOptionsMenu()
        }

        // 7. Faz o BottomNavigationView reagir às mudanças de destino:
        //    - Quando o usuário clica em um item, navega para o Fragment
        //    - Quando a navegação ocorre por código ou Deep Link,
        //      o item selecionado na barra é atualizado
        navView.setupWithNavController(navController)

        // 8) Ao re-selecionar o item "Home", limpamos o campo de busca e restauramos a lista
        navView.setOnItemReselectedListener { item ->
            // 1) limpa o texto de busca em qualquer caso
            val searchItem = binding.toolbar.menu.findItem(R.id.action_search)
            val actionView = searchItem.actionView
            actionView?.findViewById<EditText>(R.id.etSearch)?.setText("")

            // 2) obtém o fragment ativo
            val current = supportFragmentManager
                .findFragmentById(R.id.nav_host_fragment_activity_main)
                ?.childFragmentManager
                ?.fragments
                ?.firstOrNull()

            // 3) chama o reset adequado
            when (item.itemId) {
                R.id.navigation_home -> (current as? HomeFragment)?.resetList()
                R.id.navigation_favorite -> (current as? FavoriteFragment)?.resetList()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)

        // 1) obtém view de action_search
        val searchItem = menu.findItem(R.id.action_search)
        val actionView = searchItem.actionView
        val etSearch = actionView?.findViewById<EditText>(R.id.etSearch)
        val btnSearch = actionView?.findViewById<ImageButton>(R.id.btnSearch)

        // 2) ao clicar na lupa, executa busca ou toast se vazio
        btnSearch?.setOnClickListener {
            val query = etSearch?.text.toString().trim()
            if (query.isEmpty()) {
                Toast.makeText(
                    this, getString(R.string.menu_empty_search), Toast.LENGTH_SHORT).show()
            }
            else {
                // 3) delega ao fragment ativo e fecha teclado
                etSearch?.clearFocus()
                val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(etSearch?.windowToken, 0)

                val current = supportFragmentManager
                    .findFragmentById(R.id.nav_host_fragment_activity_main)
                    ?.childFragmentManager
                    ?.fragments
                    ?.firstOrNull()

                when (current) {
                    is HomeFragment -> current.searchByTitle(query)
                    is FavoriteFragment -> current.searchByTitle(query)
                }
            }
        }

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_sort -> {
                val anchor = binding.toolbar.findViewById<View>(R.id.action_sort)
                val popup  = PopupMenu(this, anchor)

                // 1) Adicionar Livro + (usa o ID que criamos em ids.xml)
                popup.menu.add(
                    Menu.NONE,
                    R.id.action_add,
                    Menu.NONE,
                    getString(R.string.action_add_book)
                )
                // 2) Nome (A–Z)
                popup.menu.add(Menu.NONE, R.id.sort_name, Menu.NONE, getString(R.string.menu_name))
                // 3) Autor (A–Z)
                popup.menu.add(Menu.NONE, R.id.sort_author, Menu.NONE, getString(R.string.menu_author))
                // 4) Gênero (A–Z)
                popup.menu.add(Menu.NONE, R.id.sort_genre, Menu.NONE, getString(R.string.menu_genre))

                popup.setOnMenuItemClickListener { mi ->
                    // recupera fragment ativo
                    val current = supportFragmentManager
                        .findFragmentById(R.id.nav_host_fragment_activity_main)
                        ?.childFragmentManager
                        ?.fragments
                        ?.firstOrNull()

                    when (mi.itemId) {
                        R.id.action_add ->
                            findNavController(R.id.nav_host_fragment_activity_main)
                                .navigate(R.id.navigation_add_book)

                        R.id.sort_name ->
                            if (current is HomeFragment) current.sortByName()
                            else if (current is FavoriteFragment) current.sortByName()

                        R.id.sort_author ->
                            if (current is HomeFragment) current.sortByAuthor()
                            else if (current is FavoriteFragment) current.sortByAuthor()

                        R.id.sort_genre ->
                            if (current is HomeFragment) current.sortByGenre()
                            else if (current is FavoriteFragment) current.sortByGenre()
                    }
                    true
                }
                popup.show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        // identifica qual tela está ativa
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        val destId = navController.currentDestination?.id

        // só mostramos busca e ordenação em HOME e FAVORITOS
        val visible = destId == R.id.navigation_home || destId == R.id.navigation_favorite

        menu?.findItem(R.id.action_search)?.isVisible = visible
        menu?.findItem(R.id.action_sort)?.isVisible   = visible

        return super.onPrepareOptionsMenu(menu)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        val handled = navController.navigateUp()
        // idem: quando clica no back da Toolbar precisa invalidar menu
        invalidateOptionsMenu()
        return handled || super.onSupportNavigateUp()
    }
}