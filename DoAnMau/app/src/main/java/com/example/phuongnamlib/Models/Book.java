package com.example.phuongnamlib.Models;

public class Book {
    int id;
    String name;
    int price;
    int copiesQuantity;
    int idKind;
    int idPublisher;
    int idLanguage;

    public Book(int id, String name, int price, int copiesQuantity, int idKind, int idPublisher, int idLanguage) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.copiesQuantity = copiesQuantity;
        this.idKind = idKind;
        this.idPublisher = idPublisher;
        this.idLanguage = idLanguage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getCopiesQuantity() {
        return copiesQuantity;
    }

    public void setCopiesQuantity(int copiesQuantity) {
        this.copiesQuantity = copiesQuantity;
    }

    public int getIdKind() {
        return idKind;
    }

    public void setIdKind(int idKind) {
        this.idKind = idKind;
    }

    public int getIdPublisher() {
        return idPublisher;
    }

    public void setIdPublisher(int idPublisher) {
        this.idPublisher = idPublisher;
    }

    public int getIdLanguage() {
        return idLanguage;
    }

    public void setIdLanguage(int idLanguage) {
        this.idLanguage = idLanguage;
    }
}
