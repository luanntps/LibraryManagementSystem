package com.example.phuongnamlib.Models;

public class MemberToItem {
    int id;
    String name;
    int countTime;
    String nameMemberType;

    public MemberToItem(int id, String name, int countTime, String nameMemberType) {
        this.id = id;
        this.name = name;
        this.countTime = countTime;
        this.nameMemberType = nameMemberType;
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

    public String getNameMemberType() {
        return nameMemberType;
    }

    public void setNameMemberType(String nameMemberType) {
        this.nameMemberType = nameMemberType;
    }
}
