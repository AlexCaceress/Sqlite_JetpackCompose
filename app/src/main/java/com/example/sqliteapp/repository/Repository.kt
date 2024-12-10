package com.example.sqliteapp.repository

import com.example.sqliteapp.room.BookEntity
import com.example.sqliteapp.room.BooksDB

class Repository(val booksDB: BooksDB) {

    suspend fun addBooksToRoom(bookEntity: BookEntity) {
        booksDB.bookDao().addBook(bookEntity)
    }

    suspend fun deleteBooksFromRoom(bookEntity: BookEntity){
        booksDB.bookDao().deleteBook(bookEntity)
    }

    suspend fun updateBookFromRoom(bookEntity: BookEntity){
        booksDB.bookDao().updateBook(bookEntity)
    }

    fun getAllBooks() = booksDB.bookDao().getAllBooks()

}