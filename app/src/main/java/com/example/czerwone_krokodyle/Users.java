package com.example.czerwone_krokodyle;

public class Users {
    String userId, name, profile;
    int money;
    public Users(String userId, String name, String profile,int money){
        this.userId = userId;
        this.name = name;
        this.profile = profile;
        this.money = money;
    }
    public Users(){

    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
    public void setUserMoney(int Money) {
        this.money = Money;
    }

    public String getProfile() {
        return profile;
    }

    public String getName() {
        return name;
    }

    public String getUserId() {
        return userId;
    }

    public int getMoney() {
        return money;
    }
}
