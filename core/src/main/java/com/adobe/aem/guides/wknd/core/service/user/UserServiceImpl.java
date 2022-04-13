package com.adobe.aem.guides.wknd.core.service.user;

import com.adobe.aem.guides.wknd.core.dao.UserDao;
import com.adobe.aem.guides.wknd.core.models.User;
import com.adobe.aem.guides.wknd.core.service.db.DatabaseService;
import com.google.gson.Gson;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component(immediate = true, service = UserService.class)
public class UserServiceImpl implements UserService
{
    @Reference
    private UserDao userDao;
    @Reference
    private DatabaseService databaseService;

    public void register(SlingHttpServletRequest request, SlingHttpServletResponse response) throws IOException {
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");


        BufferedReader reader = request.getReader();
        Gson gson = new Gson();

        User objUserConverter = gson.fromJson(reader, User.class);
        userDao.addUser(objUserConverter);
        response.setContentType("application/json");

        response.getWriter().println("Usuario cadastrado com sucesso! ");
    }

    public String list(String name) {
        List<User> userList = userDao.getUsers();
        List<User> userTemp = new ArrayList<>();
        try {

            if (name == null || name.isEmpty() || name.isBlank()) {
                userTemp = userList;
            }

            else {
                for (User u: userList) {
                    if (u.getUsername().toLowerCase().contains(name.toLowerCase())) {
                        userTemp.add(u);
                        break;
                    }
                }
            }
        } catch(Exception e) {
            e.getStackTrace();
        }

        String json = new Gson().toJson(userTemp);
        return json;
    }

    public boolean delete(String name) {
        return userDao.delete(name);
    }

    public User login(String username, String password) {
        return userDao.login(username, password);
    }
}
