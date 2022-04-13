package com.adobe.aem.guides.wknd.core.dao;

import com.adobe.aem.guides.wknd.core.models.User;
import com.adobe.aem.guides.wknd.core.service.db.DatabaseService;
import com.adobe.aem.guides.wknd.core.service.db.DatabaseServiceImpl;
import com.adobe.aem.guides.wknd.core.service.user.UserService;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component(immediate = true, service = UserDao.class)
public class UserDaoImpl implements UserDao
{
    private static final long serialVersionUID = 3L;
    private static final List<User> userList = new ArrayList<>();

    static{
        userList.add(new User("ktverde", "12345", "Paulo"));
        userList.add(new User("admin", "admin", "Admin"));
    }

    @Reference
    private DatabaseService databaseService;

    public List<User> getUsers() {
        //List<User> listUsers = new ArrayList<>();
        try (Connection con = databaseService.getConnection()) {
            String sql = "SELECT * FROM users";

            PreparedStatement pst = con.prepareStatement(sql);
            try {
                pst.execute();
                try (ResultSet rs = pst.getResultSet()) {
                    while (rs.next()) {
                        User user = new User(rs.getString(1),rs.getString(2),rs.getString(3));
                        userList.add(user);
                    }
                }
                catch (Exception e) {
                    throw new RuntimeException(e.getMessage() + "1");
                }
            }
            catch (Exception e) {
                throw new RuntimeException(e.getMessage() + "2");
            }
        }
        catch (Exception e) {
            throw new RuntimeException(e.getMessage() + "3");
        }
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
