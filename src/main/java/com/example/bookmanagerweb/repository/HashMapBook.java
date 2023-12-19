package com.example.bookmanagerweb.repository;

import com.example.bookmanagerweb.book.Book;
import java.util.*;

public class HashMapBook implements BookRepository{
    private HashMap<Long, Book> books = new HashMap<Long, Book>();

    public void addBook(Book book){
        books.put(book.getId(), book);
    }
    public Book getBook(Long id){ return books.get(id); }
    public List<Book> getBooks(){ return new ArrayList<>(books.values()); }
    public void removeBook(Book book){ books.remove(book.getId()); }
}
