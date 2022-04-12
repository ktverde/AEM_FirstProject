package com.adobe.aem.guides.wknd.core.dao;

import com.adobe.aem.guides.wknd.core.models.User;

import java.util.ArrayList;
import java.util.List;

public class UserDao
{
    private static final List<User> userList = new ArrayList<>();

    static{
        userList.add(new User("ktverde", "12345", "Paulo"));
        userList.add(new User("admin", "admin", "Admin"));
    }

    public List<User> getUsers() {
        return userList;
    }

    public void addUser(User user) {
        userList.add(user);
    }

    public User login(String username, String password) {
        for (User u:userList) {
            if(u.getUsername().equals(username) && u.getPassword().equals(password))
                return u;
        }
        return null;
    }

    public boolean delete(String username) {
        return userList.removeIf(u -> u.getUsername().equals(username));
    }
}
