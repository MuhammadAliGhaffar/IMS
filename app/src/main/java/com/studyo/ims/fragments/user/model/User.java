package com.studyo.ims.fragments.user.model;

public class User {
    String username,email,password,objectId;

    public User() {
    }

    public User(String username, String email, String password, String objectId) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.objectId = objectId;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
