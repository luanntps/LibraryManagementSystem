package com.example.phuongnamlib.Models;

public class AuthorManager {
    int idAuthor;
    int idBook;

    public AuthorManager(int idAuthor, int idBook) {
        this.idAuthor = idAuthor;
        this.idBook = idBook;
    }

    public int getIdAuthor() {
        return idAuthor;
    }

    public void setIdAuthor(int idAuthor) {
        this.idAuthor = idAuthor;
    }

    public int getIdBook() {
        return idBook;
    }

    public void setIdBook(int idBook) {
        this.idBook = idBook;
    }
}
