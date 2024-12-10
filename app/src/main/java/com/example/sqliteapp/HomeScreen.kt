package com.example.sqliteapp

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.sqliteapp.repository.Repository
import com.example.sqliteapp.room.BooksDB
import com.example.sqliteapp.viewmodel.BookViewModel

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val db = BooksDB.getInstance(context)
    val repository = Repository(db)
    val myViewModel = BookViewModel(repository)

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "Main") {
        composable("Main"){ MainScreen(viewModel = myViewModel, navController = navController) }
        composable("Card/{bookId}/{bookName}"){
            CardScreen(
                navController = navController,
                viewModel = myViewModel,
                bookId = it.arguments?.getString("bookId"),
                bookName = it.arguments?.getString("bookName")
            )
        }
    }
}