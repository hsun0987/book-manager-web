package com.example.bookmanagerweb.book;

import java.time.LocalDate;
import java.util.Objects;

public class EBook extends Book{
    String filesize;

    EBook(Long id, String name, String author, Long isbn, LocalDate publishedDate, String filesize){
        super(id, name, author, isbn, publishedDate);
        this.filesize = filesize;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        if (!super.equals(object)) return false;
        EBook eBook = (EBook) object;
        return Objects.equals(filesize, eBook.filesize);
    }

    @Override
    public String toString() {
        return  super.toString() +
                "," + filesize;
    }

    public String getFilesize() {
        return filesize;
    }

    public void setFilesize(String filesize) {
        this.filesize = filesize;
    }
}