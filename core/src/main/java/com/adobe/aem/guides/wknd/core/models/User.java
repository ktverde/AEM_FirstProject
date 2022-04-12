package com.adobe.aem.guides.wknd.core.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class User
{
    private String name;
    private String username;
    private String password;

    public User() { }

    public User(String username, String password, String name) {
        this.name = name;
        this.username = username;
        this.password = password;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    @Override
    public String toString() {
        return "User {" +
                "name = " + name +
                ", username = " + username +
                ", password = " + password +
                '}';
    }
}
