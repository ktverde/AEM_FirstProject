package com.adobe.aem.guides.wknd.core.dao.user;

import com.adobe.aem.guides.wknd.core.models.User;

import java.util.List;

public interface UserDao{
    List<User> getAll();
    void add(User user);
    User login(String username, String password);
    void delete(String username);
    boolean update(String username, User user);
    User getUserByUsername(String username);
}
