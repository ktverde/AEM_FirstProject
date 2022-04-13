package com.adobe.aem.guides.wknd.core.dao;

import com.adobe.aem.guides.wknd.core.models.User;

import java.util.List;

public interface UserDao{
    public List<User> getUsers();
    public void addUser(User user);
    public User login(String username, String password);
    public boolean delete(String username);
}
