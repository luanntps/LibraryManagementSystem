package com.example.phuongnamlib.Models;

public class CallCard {
    int id;
    String beginDate;
    String expiresDate;
    int idBook;
    int idMember;
    String idLibrarian;

    public CallCard(int id, String beginDate, String expiresDate, int idBook, int idMember, String idLibrarian) {
        this.id = id;
        this.beginDate = beginDate;
        this.expiresDate = expiresDate;
        this.idBook = idBook;
        this.idMember = idMember;
        this.idLibrarian = idLibrarian;
    }

    public CallCard(String beginDate, String expiresDate, int idBook, int idMember, String idLibrarian) {
        this.beginDate = beginDate;
        this.expiresDate = expiresDate;
        this.idBook = idBook;
        this.idMember = idMember;
        this.idLibrarian = idLibrarian;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public String getExpiresDate() {
        return expiresDate;
    }

    public void setExpiresDate(String expiresDate) {
        this.expiresDate = expiresDate;
    }

    public int getIdBook() {
        return idBook;
    }

    public void setIdBook(int idBook) {
        this.idBook = idBook;
    }

    public int getIdMember() {
        return idMember;
    }

    public void setIdMember(int idMember) {
        this.idMember = idMember;
    }

    public String getIdLibrarian() {
        return idLibrarian;
    }

    public void setIdLibrarian(String idLibrarian) {
        this.idLibrarian = idLibrarian;
    }
}
