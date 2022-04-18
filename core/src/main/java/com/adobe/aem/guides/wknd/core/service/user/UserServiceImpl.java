package com.adobe.aem.guides.wknd.core.service.user;

import com.adobe.aem.guides.wknd.core.dao.user.UserDao;
import com.adobe.aem.guides.wknd.core.models.User;
import com.adobe.aem.guides.wknd.core.service.db.DatabaseService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.sling.api.SlingHttpServletRequest;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@Component(immediate = true, service = UserService.class)
public class UserServiceImpl implements UserService
{
    @Reference
    private UserDao userDao;
    @Reference
    private DatabaseService databaseService;

    public int register(SlingHttpServletRequest request) throws IOException {
        int count = 0;
        try {
            BufferedReader reader = request.getReader();
            Type listType = new TypeToken<List<User>>() {}.getType();
            List<User> userList = new Gson().fromJson(reader, listType);

            for (User u : userList) {
                if (userDao.getUserByUsername(u.getUsername()) == null) {
                    userDao.add(u);
                    count++;
                }
            }
        }catch(Exception e){
            throw new RuntimeException(e);
        }
        return count;
    }

    public String list(String name) {
        List<User> userList = userDao.getAll();
        List<User> userTemp = new ArrayList<>();
        try {
            if (name == null || name.isEmpty() || name.isBlank()) userTemp = userList;
            else {
                User user = userDao.getUserByUsername(name);
                if (user != null) userTemp.add(user);
            }
        } catch(Exception e) {
            e.getStackTrace();
        }
        return new Gson().toJson(userTemp);
    }

    public int delete(SlingHttpServletRequest request) {
        int count = 0;
        try {
            BufferedReader reader = request.getReader();
            Type listType = new TypeToken<List<User>>() {}.getType();
            List<User> userList = new Gson().fromJson(reader, listType);

            for (User u : userList) {
                if (userDao.getUserByUsername(u.getUsername()) != null) {
                    userDao.delete(u.getUsername());
                    count++;
                }
            }
        }catch(Exception e){
            throw new RuntimeException(e);
        }
        return count;
    }

    public boolean update(String user, String username, String password, String name) {

        if (user == null || user.isEmpty() || user.isBlank()) return false;

        User u = userDao.getUserByUsername(user);
        if (u != null) {

            if(!u.getUsername().equals(username) && username != null) {
                if (!username.isEmpty() || !username.isBlank())
                    u.setUsername(username);
            }
            if(!u.getPassword().equals(password) && password != null) {
                if (!password.isEmpty() || !password.isBlank())
                    u.setPassword(password);
            }
            if(!u.getName().equals(name) && name != null) {
                if (!name.isEmpty() || !name.isBlank())
                    u.setName(name);
            }

            return userDao.update(user, u);
        }
        return false;
    }

    public User login(String username, String password) {
        return userDao.login(username, password);
    }
}
