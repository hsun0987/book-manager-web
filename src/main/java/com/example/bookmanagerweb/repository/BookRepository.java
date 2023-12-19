package com.example.bookmanagerweb.repository;


import com.example.bookmanagerweb.book.Book;

import java.util.List;

public interface BookRepository {
    void addBook(Book book);
    Book getBook(Long id);
    List<Book> getBooks();
    void removeBook(Book book);
}