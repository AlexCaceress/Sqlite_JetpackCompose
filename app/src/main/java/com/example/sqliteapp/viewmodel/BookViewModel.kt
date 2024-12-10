package com.example.sqliteapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sqliteapp.repository.Repository
import com.example.sqliteapp.room.BookEntity
import kotlinx.coroutines.launch

class BookViewModel(val repository: Repository) : ViewModel() {

    fun addBook(book : BookEntity){
        viewModelScope.launch {
            repository.addBooksToRoom(book)
        }
    }

    fun deleteBook(book: BookEntity){
        viewModelScope.launch {
            repository.deleteBooksFromRoom(book)
        }
    }

    fun updateBook(book: BookEntity){
        viewModelScope.launch {
            repository.updateBookFromRoom(book)
        }
    }

    val books = repository.getAllBooks()

}