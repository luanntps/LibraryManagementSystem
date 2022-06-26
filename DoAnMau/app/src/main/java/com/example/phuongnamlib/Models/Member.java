package com.example.phuongnamlib.Models;

public class Member {
    int id;
    String name;
    int countTime;
    int idMemberType;

    public Member(int id, String name, int countTime, int idMemberType) {
        this.id = id;
        this.name = name;
        this.countTime = countTime;
        this.idMemberType = idMemberType;
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

    public int getCountTime() {
        return countTime;
    }

    public void setCountTime(int countTime) {
        this.countTime = countTime;
    }

    public int getIdMemberType() {
        return idMemberType;
    }

    public void setIdMemberType(int idMemberType) {
        this.idMemberType = idMemberType;
    }
}
