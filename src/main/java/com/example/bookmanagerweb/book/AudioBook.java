package com.example.bookmanagerweb.book;


import java.time.LocalDate;
import java.util.Objects;

public class AudioBook extends Book{
    String filesize;
    String language;
    int playTime;

    AudioBook(Long id, String name, String author, Long isbn, LocalDate publishedDate, String filesize, String language, int playTime){
        super(id, name, author, isbn, publishedDate);
        this.filesize = filesize;
        this.language = language;
        this.playTime = playTime;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        if (!super.equals(object)) return false;
        AudioBook audioBook = (AudioBook) object;
        return playTime == audioBook.playTime && Objects.equals(filesize, audioBook.filesize) && Objects.equals(language, audioBook.language);
    }

    @Override
    public String toString() {
        return  super.toString() +
                "," + filesize +
                "," + language +
                "," + playTime;
    }

    public String getFilesize() {
        return filesize;
    }

    public void setFilesize(String filesize) {
        this.filesize = filesize;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public int getPlayTime() {
        return playTime;
    }

    public void setPlayTime(int playTime) {
        this.playTime = playTime;
    }
}