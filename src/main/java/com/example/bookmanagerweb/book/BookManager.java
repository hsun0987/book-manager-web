package com.example.bookmanagerweb.book;

import java.io.IOException;

public abstract class BookManager {
    abstract void init();
    abstract void interactWithUser() throws IOException;
    abstract void addBook();
    abstract void printAllBook();
    abstract void updateBook();
    abstract void removeBook();
}