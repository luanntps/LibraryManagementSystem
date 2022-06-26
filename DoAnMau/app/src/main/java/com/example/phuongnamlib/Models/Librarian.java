package com.example.phuongnamlib.Models;

public class Librarian {
    String id, name, password;
    int idRole;

    public Librarian(String id, String name, String password, int idRole) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.idRole = idRole;
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

    public int getIdRole() {
        return idRole;
    }

    public void setIdRole(int idRole) {
        this.idRole = idRole;
    }
}
