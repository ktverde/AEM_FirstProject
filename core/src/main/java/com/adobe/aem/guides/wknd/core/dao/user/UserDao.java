package com.adobe.aem.guides.wknd.core.dao.user;

import com.adobe.aem.guides.wknd.core.models.User;

import java.util.List;

public interface UserDao{
    List<User> getUsers();
    void addUser(User user);
    User login(String username, String password);
    boolean delete(String username);
    boolean update(String username, User user);
    User getUserByUsername(String username);
}
