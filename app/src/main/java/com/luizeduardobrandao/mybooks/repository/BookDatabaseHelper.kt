package com.luizeduardobrandao.mybooks.repository

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.luizeduardobrandao.mybooks.entity.BookEntity
import com.luizeduardobrandao.mybooks.helper.DatabaseConstants

// NÃO SERÁ MAIS USADO

//class BookDatabaseHelper(context: Context): SQLiteOpenHelper(
//    context, DATABASE_NAME, null, DATABASE_VERSION
//){
//    // é chamado apenas no primeiro acesso ao banco de dados, uma vez chamado nunca mais é chamado.
//    // É o momento da criação do banco de dados.
//    override fun onCreate(db: SQLiteDatabase) {
//        db.execSQL(CREATE_TABLE_BOOKS)
//
//        // função para inserir os livros no banco de dados
//        insertBooks(db)
//    }
//
//    // quando altera a versão do banco de dados, este mét0do é chamado.
//    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
//        TODO("Not yet implemented")
//    }
//
//    companion object {
//        private const val DATABASE_NAME = "booksDB"
//        private const val DATABASE_VERSION = 1
//
//        // código sql para criar o banco de dados
//        private const val CREATE_TABLE_BOOKS = """
//            CREATE TABLE ${DatabaseConstants.BOOK.TABLE_NAME}(
//            ${DatabaseConstants.BOOK.COLUMNS.ID} INTEGER PRIMARY KEY AUTOINCREMENT,
//            ${DatabaseConstants.BOOK.COLUMNS.TITLE} TEXT NOT NULL,
//            ${DatabaseConstants.BOOK.COLUMNS.AUTHOR} TEXT NOT NULL,
//            ${DatabaseConstants.BOOK.COLUMNS.GENRE} TEXT NOT NULL,
//            ${DatabaseConstants.BOOK.COLUMNS.FAVORITE} INTEGER NOT NULL
//            );
//        """
//    }
//
//    // função para inserir os livros no banco de dados
//    private fun insertBooks(db: SQLiteDatabase){
//        val books = getInitialBooks()
//
//        // para usar ContentValues precisa passar no formato de chave e valor
//        for (book in books){
//            val values = ContentValues().apply {
//                put(DatabaseConstants.BOOK.COLUMNS.TITLE, book.title)
//                put(DatabaseConstants.BOOK.COLUMNS.AUTHOR, book.author)
//                put(DatabaseConstants.BOOK.COLUMNS.GENRE, book.genre)
//                put(DatabaseConstants.BOOK.COLUMNS.FAVORITE, if (book.favorite) 1 else 0)
//            }
//            // este código pode ser usado com a biblioteca Room, mas no momento não usaremos.
//            db.insert(DatabaseConstants.BOOK.TABLE_NAME, null, values)
//        }
//    }
//
//    // Cria uma lista inicial de livros para popular o repositório
//    private fun getInitialBooks(): List<BookEntity> {
//
//        return listOf(
//            BookEntity(1, "To Kill a Mockingbird", "Harper Lee", false, "Ficção"),
//            BookEntity(2, "Dom Casmurro", "Machado de Assis", false, "Romance"),
//            BookEntity(3, "O Hobbit", "J.R.R. Tolkien", false, "Fantasia"),
//            BookEntity(4, "Senhor dos Anéis", "J.R.R. Tolkien", false, "Fantasia"),
//            BookEntity(5, "O Pequeno Príncipe", "Antoine de Saint-Exupéry", false, "Fantasia"),
//            BookEntity(6, "Crime e Castigo", "Fiódor Dostoiévski", false, "Ficção"),
//            BookEntity(7, "Frankenstein", "Mary Shelley", false, "Ficção"),
//            BookEntity(8, "Harry Potter e a Pedra Filosofal", "J.K. Rowling", false, "Fantasia"),
//            BookEntity(9, "Harry Potter e a Câmara Secreta", "J.K. Rowling", false, "Fantasia"),
//            BookEntity(10, "Harry Potter e o Prisioneiro de Azkaban", "J.K. Rowling", false, "Fantasia"),
//            BookEntity(11, "Harry Potter e o Cálice de Fogo", "J.K. Rowling", false, "Fantasia"),
//            BookEntity(12, "Harry Potter e a Ordem da Fênix", "J.K. Rowling", false, "Fantasia"),
//            BookEntity(13, "Harry Potter e o Enigma do Príncipe", "J.K. Rowling", false, "Fantasia"),
//            BookEntity(14, "Harry Potter e as Relíquias da Morte", "J.K. Rowling", false, "Fantasia"),
//            BookEntity(15, "Neuromancer", "William Gibson", false, "Ficção"),
//            BookEntity(16, "Cem Anos de Solidão", "Gabriel García Márquez", false, "Romance"),
//            BookEntity(17, "Drácula", "Bram Stoker", false, "Ficção"),
//            BookEntity(18, "Orgulho e Preconceito", "Jane Austen", false, "Romance"),
//            BookEntity(19, "As Crônicas de Nárnia", "C.S. Lewis", false, "Fantasia"),
//            BookEntity(20, "O Código Da Vinci", "Dan Brown", false, "Mistério"),
//            BookEntity(21, "Moby Dick", "Herman Melville", false, "Aventura"),
//            BookEntity(22, "O Nome do Vento", "Patrick Rothfuss", false, "Fantasia"),
//            BookEntity(23, "O Conde de Monte Cristo", "Alexandre Dumas", false, "Aventura"),
//            BookEntity(24, "Os Miseráveis", "Victor Hugo", false, "Romance"),
//            BookEntity(25, "1984", "George Orwell", false, "Distopia"),
//            BookEntity(26, "A Revolução dos Bichos", "George Orwell", false, "Distopia"),
//            BookEntity(27, "O Apanhador no Campo de Centeio", "J.D. Salinger", false, "Romance"),
//            BookEntity(28, "Guerra e Paz", "Liev Tolstói", false, "Ficção"),
//            BookEntity(29, "Anna Kariênina", "Liev Tolstói", false, "Romance"),
//            BookEntity(30, "O Retrato de Dorian Gray", "Oscar Wilde", false, "Ficção"),
//            BookEntity(31, "Jane Eyre", "Charlotte Brontë", false, "Romance"),
//            BookEntity(32, "O Morro dos Ventos Uivantes", "Emily Brontë", false, "Romance"),
//            BookEntity(33, "O Velho e o Mar", "Ernest Hemingway", false, "Ficção"),
//            BookEntity(34, "As Aventuras de Tom Sawyer", "Mark Twain", false, "Aventura"),
//            BookEntity(35, "Mansfield Park", "Jane Austen", false, "Romance"),
//            BookEntity(36, "Drácula de Bram Stoker", "Bram Stoker", false, "Ficção"),
//            BookEntity(37, "Hamlet", "William Shakespeare", false, "Ficção"),
//            BookEntity(38, "Macbeth", "William Shakespeare", false, "Ficção"),
//            BookEntity(39, "O Sol é para Todos", "Harper Lee", false, "Ficção"),
//            BookEntity(40, "O Nome da Rosa", "Umberto Eco", false, "Mistério"),
//            BookEntity(41, "A Metamorfose", "Franz Kafka", false, "Ficção"),
//            BookEntity(42, "Cem Anos de Solidão – Edição Especial", "Gabriel García Márquez", false, "Ficção"),
//            BookEntity(43, "Percy Jackson e o Ladrão de Raios", "Rick Riordan", false, "Fantasia"),
//            BookEntity(44, "Memórias Póstumas de Brás Cubas", "Machado de Assis", false, "Romance"),
//            BookEntity(45, "Quincas Borba", "Machado de Assis", false, "Romance"),
//            BookEntity(46, "O Alienista", "Machado de Assis", false, "Romance"),
//            BookEntity(47, "Grande Sertão: Veredas", "Guimarães Rosa", false, "Ficção"),
//            BookEntity(48, "Vidas Secas", "Graciliano Ramos", false, "Ficção"),
//            BookEntity(49, "Dom Quixote", "Miguel de Cervantes", false, "Aventura"),
//            BookEntity(50, "Iracema", "José de Alencar", false, "Romance"),
//            BookEntity(51, "O Cortiço", "Aluísio Azevedo", false, "Ficção"),
//            BookEntity(52, "O Guarani", "José de Alencar", false, "Romance"),
//            BookEntity(53, "Senhora", "José de Alencar", false, "Romance"),
//            BookEntity(54, "Triste Fim de Policarpo Quaresma", "Lima Barreto", false, "Romance"),
//            BookEntity(55, "A Moreninha", "Joaquim Manuel de Macedo", false, "Romance"),
//            BookEntity(56, "Dona Flor e Seus Dois Maridos", "Jorge Amado", false, "Romance"),
//            BookEntity(57, "Capitães da Areia", "Jorge Amado", false, "Romance"),
//            BookEntity(58, "Macunaíma", "Mário de Andrade", false, "Romance"),
//            BookEntity(59, "O Auto da Compadecida", "Ariano Suassuna", false, "Ficção"),
//            BookEntity(60, "O Tempo e o Vento", "Érico Veríssimo", false, "Romance"),
//            BookEntity(61, "Olhai os Lírios do Campo", "Érico Veríssimo", false, "Romance"),
//            BookEntity(62, "Incidente em Antares", "Érico Veríssimo", false, "Ficção"),
//            BookEntity(63, "Primeiras Estórias", "Guimarães Rosa", false, "Contos"),
//            BookEntity(64, "A Rosa do Povo", "Carlos Drummond de Andrade", false, "Contos"),
//            BookEntity(65, "Dois Irmãos", "Milton Hatoum", false, "Romance"),
//            BookEntity(66, "Menino de Engenho", "José Lins do Rego", false, "Romance"),
//            BookEntity(67, "São Bernardo", "Graciliano Ramos", false, "Romance"),
//            BookEntity(68, "O Seminarista", "Bernardo Guimarães", false, "Romance"),
//            BookEntity(69, "O Quinze", "Rachel de Queiroz", false, "Romance"),
//            BookEntity(70, "O Menino Maluquinho", "Ziraldo", false, "Infantil"),
//            BookEntity(71, "A Turma da Mônica", "Mauricio de Sousa", false, "Infantil"),
//            BookEntity(72, "Marcelo, Marmelo, Martelo", "Ruth Rocha", false, "Infantil"),
//            BookEntity(73, "Chapeuzinho Amarelo", "Chico Buarque", false, "Infantil"),
//            BookEntity(74, "O Gato Malhado e a Andorinha Sinhá", "Jorge Amado", false, "Infantil"),
//            BookEntity(75, "A Bolsa Amarela", "Lygia Bojunga", false, "Infantil"),
//            BookEntity(76, "O Sítio do Picapau Amarelo", "Monteiro Lobato", false, "Infantil"),
//            BookEntity(77, "Reinações de Narizinho", "Monteiro Lobato", false, "Infantil"),
//            BookEntity(78, "Caçadas de Pedrinho", "Monteiro Lobato", false, "Infantil"),
//            BookEntity(79, "Emília no País da Gramática", "Monteiro Loboto", false, "Infantil"),
//            BookEntity(80, "Menina Bonita do Laço de Fita", "Ana Maria Machado", false, "Infantil"),
//            BookEntity(81, "Flicts", "Ziraldo", false, "Infantil"),
//            BookEntity(82, "A Arca de Noé", "Vinicius de Moraes", false, "Infantil"),
//            BookEntity(83, "O Reizinho Mandão", "Odilon Moraes", false, "Infantil"),
//            BookEntity(84, "O Saci", "Monteiro Loboto", false, "Infantil"),
//            BookEntity(85, "O Rapto do Garoto de Ouro", "Moacyr Scliar", false, "Infantil"),
//            BookEntity(86, "Boneca de Pano", "Ana Maria Machado", false, "Infantil"),
//            BookEntity(87, "A Bruxa Salomé", "Eva Furnari", false, "Infantil"),
//            BookEntity(88, "O Curupira Não Deixa", "Alcy Cheuiche", false, "Infantil"),
//            BookEntity(89, "Histórias de Tia Nastácia", "Monteiro Loboto", false, "Infantil"),
//            BookEntity(90, "Alice no País das Maravilhas", "Lewis Carroll", false, "Fantasia"),
//            BookEntity(91, "Pinóquio", "Carlo Collodi", false, "Fantasia"),
//            BookEntity(92, "Peter Pan", "J.M. Barrie", false, "Fantasia"),
//            BookEntity(93, "Heidi", "Johanna Spyri", false, "Fantasia"),
//            BookEntity(94, "O Mágico de Oz", "L. Frank Baum", false, "Fantasia"),
//            BookEntity(95, "A Teia de Charlotte", "E.B. White", false, "Fantasia"),
//            BookEntity(96, "Matilda", "Roald Dahl", false, "Fantasia"),
//            BookEntity(97, "O Jardim Secreto", "Frances Hodgson Burnett", false, "Fantasia"),
//            BookEntity(98, "O Livro da Selva", "Rudyard Kipling", false, "Fantasia"),
//            BookEntity(99, "Ursinho Pooh", "A.A. Milne", false, "Fantasia"),
//            BookEntity(100, "Cinderela", "Charles Perrault", false, "Fantasia"),
//            BookEntity(101, "Branca de Neve e os Sete Anões", "Irmãos Grimm", false, "Fantasia"),
//            BookEntity(102, "A Bela Adormecida", "Charles Perrault", false, "Fantasia"),
//            BookEntity(103, "A Bela e a Fera", "Jeanne-Marie Leprince de Beaumont", false, "Fantasia"),
//            BookEntity(104, "A Pequena Sereia", "Hans Christian Andersen", false, "Fantasia"),
//            BookEntity(105, "Dumbo, o Elefante Voador", "Helen Aberson", false, "Fantasia"),
//            BookEntity(106, "101 Dálmatas", "Dodie Smith", false, "Infantil"),
//            BookEntity(107, "A Espada Era a Lei", "T.H. White", false, "Fantasia"),
//            BookEntity(108, "Oliver Twist", "Charles Dickens", false, "Romance"),
//            BookEntity(109, "As Viagens de Gulliver", "Jonathan Swift", false, "Aventura"),
//            BookEntity(110, "A Ilha do Tesouro", "Robert Louis Stevenson", false, "Aventura")
//        )
//    }
//
//}