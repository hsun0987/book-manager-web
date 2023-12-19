package com.example.bookmanagerweb.book;

import java.time.LocalDate;
import java.util.Objects;

public class Book {
    private Long id;
    private String name;
    private String author;
    private Long isbn;
    private LocalDate publishedDate;


    public Book(Long id, String name, String author, Long isbn, LocalDate publishedDate) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.isbn = isbn;
        this.publishedDate = publishedDate;
    }

    public Book() { }

    @Override
    public boolean equals(Object object) {
        // instanceof : 객체 타입 확인
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Book book = (Book) object;
        return Objects.equals(id, book.id) && Objects.equals(name, book.name) && Objects.equals(author, book.author) && Objects.equals(isbn, book.isbn) && Objects.equals(publishedDate, book.publishedDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, author, isbn);
    }

    @Override
    public String toString() {
        return  id +
                "," + name  +
                "," + author +
                "," + isbn +
                "," + publishedDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() { return author; }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Long getIsbn() {
        return isbn;
    }

    public void setIsbn(Long isbn) {
        this.isbn = isbn;
    }

    public LocalDate getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(LocalDate publishedDate) {
        this.publishedDate = publishedDate;
    }

}