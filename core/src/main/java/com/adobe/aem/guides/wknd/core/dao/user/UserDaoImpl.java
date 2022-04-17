package com.adobe.aem.guides.wknd.core.dao.user;

import com.adobe.aem.guides.wknd.core.models.User;
import com.adobe.aem.guides.wknd.core.service.db.DatabaseService;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Component(immediate = true, service = UserDao.class)
public class UserDaoImpl implements UserDao
{
    private static final long serialVersionUID = 3L;

    @Reference
    private DatabaseService databaseService;

    public List<User> getUsers() {
        List<User> listUsers = new ArrayList<>();
        try (Connection con = databaseService.getConnection()) {
            String sql = "SELECT * FROM users";

            try(PreparedStatement pst = con.prepareStatement(sql)) {
                pst.execute();
                try (ResultSet rs = pst.getResultSet()) {
                    while (rs.next()) {
                        User user = new User(rs.getString(1),rs.getString(2),rs.getString(3));
                        listUsers.add(user);
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
        return listUsers;
    }

    public void addUser(User user) {
        try (Connection con = databaseService.getConnection()) {
            String sql = "INSERT INTO users (username, password, name) VALUES (?, ?, ?)";

            try(PreparedStatement pst = con.prepareStatement(sql)) {
                pst.setString(1, user.getUsername());
                pst.setString(2, user.getPassword());
                pst.setString(3, user.getName());
                pst.execute();
            }
            catch (Exception e) {
                throw new RuntimeException(e.getMessage() + "2");
            }
        }
        catch (Exception e) {
            throw new RuntimeException(e.getMessage() + "3");
        }
    }
    public User getUserByUsername(String username) {
        User user = null;
        try (Connection con = databaseService.getConnection()) {
            String sql = "SELECT * FROM users WHERE username = ?";

            try(PreparedStatement pst = con.prepareStatement(sql)) {
                pst.setString(1, username);
                pst.execute();

                try(ResultSet rs = pst.getResultSet()){
                    if(rs.next())
                        user = new User(rs.getString(1),rs.getString(2),rs.getString(3));

                }catch (Exception e){
                    throw new RuntimeException(e);
                }
            }
            catch (Exception e) {
                throw new RuntimeException(e.getMessage() + "2");
            }
        }
        catch (Exception e) {
            throw new RuntimeException(e.getMessage() + "3");
        }
        return user;
    }

    public User login(String username, String password) {
        List<User> userList = getUsers();
        for (User u : userList) {
            if(u.getUsername().equals(username) && u.getPassword().equals(password))
                return u;
        }
        return null;
    }

    public boolean delete(String username) {
        try (Connection con = databaseService.getConnection()) {
            String sql = "DELETE FROM users WHERE username = ?";

            try(PreparedStatement pst = con.prepareStatement(sql)) {
                pst.setString(1, username);

                pst.execute();
            }
            catch (Exception e) {
                throw new RuntimeException(e.getMessage() + "2");
            }
        }
        catch (Exception e) {
            throw new RuntimeException(e.getMessage() + "3");
        }
        return true;
    }

    public boolean update(String username, User user){
        try (Connection con = databaseService.getConnection()) {
            String sql = "UPDATE users SET username = ?, password = ?, name = ? WHERE username = ?";

            try(PreparedStatement pst = con.prepareStatement(sql)) {
                pst.setString(1, user.getUsername());
                pst.setString(2, user.getPassword());
                pst.setString(3, user.getName());
                pst.setString(4, username);

                pst.execute();
            }
            catch (Exception e) {
                throw new RuntimeException(e.getMessage() + "2");
            }
        }
        catch (Exception e) {
            throw new RuntimeException(e.getMessage() + "3");
        }
        return true;
    }
}