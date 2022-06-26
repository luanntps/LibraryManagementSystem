package com.example.phuongnamlib.Models;

public class LibrarianToItem {
    String id, name, password;
    String nameRole;

    public LibrarianToItem(String id, String name, String password, String nameRole) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.nameRole = nameRole;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNameRole() {
        return nameRole;
    }

    public void setNameRole(String nameRole) {
        this.nameRole = nameRole;
    }
}
