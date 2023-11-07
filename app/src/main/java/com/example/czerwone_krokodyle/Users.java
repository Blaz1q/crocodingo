package com.example.czerwone_krokodyle;

public class Users {
    String userId, name, profile;
    public Users(String userId, String name, String profile){
        this.userId = userId;
        this.name = name;
        this.profile = profile;
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

    public String getProfile() {
        return profile;
    }

    public String getName() {
        return name;
    }

    public String getUserId() {
        return userId;
    }
}
