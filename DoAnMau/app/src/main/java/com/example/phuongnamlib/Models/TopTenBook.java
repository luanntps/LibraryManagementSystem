package com.example.phuongnamlib.Models;

public class TopTenBook {
    String name;
    int count;
    String kind;

    public TopTenBook(String name, int count, String kind) {
        this.name = name;
        this.count = count;
        this.kind = kind;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }
}
