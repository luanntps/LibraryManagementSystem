package com.example.phuongnamlib.Models;

public class BookToItem {
    int id;
    String name;
    int price;
    int copiesQuantity;
    String nameKind;
    String namePublisher;
    String nameLanguage;

    public BookToItem(int id, String name, int price, int copiesQuantity, String nameKind, String namePublisher, String nameLanguage) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.copiesQuantity = copiesQuantity;
        this.nameKind = nameKind;
        this.namePublisher = namePublisher;
        this.nameLanguage = nameLanguage;
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

    public String getNameKind() {
        return nameKind;
    }

    public void setNameKind(String nameKind) {
        this.nameKind = nameKind;
    }

    public String getNamePublisher() {
        return namePublisher;
    }

    public void setNamePublisher(String namePublisher) {
        this.namePublisher = namePublisher;
    }

    public String getNameLanguage() {
        return nameLanguage;
    }

    public void setNameLanguage(String nameLanguage) {
        this.nameLanguage = nameLanguage;
    }
}
