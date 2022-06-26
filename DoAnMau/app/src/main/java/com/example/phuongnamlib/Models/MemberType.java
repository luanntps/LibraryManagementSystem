package com.example.phuongnamlib.Models;

public class MemberType {
    int id;
    String name;
    int discountPercents;

    public MemberType(int id, String name, int discountPercents) {
        this.id = id;
        this.name = name;
        this.discountPercents = discountPercents;
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

    public int getDiscountPercents() {
        return discountPercents;
    }

    public void setDiscountPercents(int discountPercents) {
        this.discountPercents = discountPercents;
    }
}
