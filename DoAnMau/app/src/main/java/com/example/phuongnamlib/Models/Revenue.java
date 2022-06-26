package com.example.phuongnamlib.Models;

public class Revenue {
    int date;
    int revenue;

    public Revenue(int date, int revenue) {
        this.date = date;
        this.revenue = revenue;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public int getRevenue() {
        return revenue;
    }

    public void setRevenue(int revenue) {
        this.revenue = revenue;
    }
}
