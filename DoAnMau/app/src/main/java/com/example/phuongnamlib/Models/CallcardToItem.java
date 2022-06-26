package com.example.phuongnamlib.Models;

public class CallcardToItem {
    int id;
    String beginDate;
    String expiresDate;
    String bookName;
    String memberName;
    String idLibrarian;

    public CallcardToItem(int id, String beginDate, String expiresDate, String bookName, String memberName, String idLibrarian) {
        this.id = id;
        this.beginDate = beginDate;
        this.expiresDate = expiresDate;
        this.bookName = bookName;
        this.memberName = memberName;
        this.idLibrarian = idLibrarian;
    }

    public CallcardToItem(String beginDate, String expiresDate, String bookName, String memberName, String idLibrarian) {
        this.beginDate = beginDate;
        this.expiresDate = expiresDate;
        this.bookName = bookName;
        this.memberName = memberName;
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

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getIdLibrarian() {
        return idLibrarian;
    }

    public void setIdLibrarian(String idLibrarian) {
        this.idLibrarian = idLibrarian;
    }
}
