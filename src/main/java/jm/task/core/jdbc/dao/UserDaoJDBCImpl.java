package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

//TODO: delete IOException
public class UserDaoJDBCImpl implements UserDao {
    private Connection conn;

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try {
            conn = Util.getConnection();
            Statement statement = conn.createStatement();
            statement.executeUpdate("CREATE TABLE user (id INT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(255), last_name VARCHAR(255), age INT)");
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        } finally {
            Util.close(conn);
        }
    }

    public void dropUsersTable() {
        try {
            conn = Util.getConnection();
            Statement statement = conn.createStatement();
            statement.executeUpdate("DROP TABLE user");
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        } finally {
            Util.close(conn);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try {
            conn = Util.getConnection();
            PreparedStatement pstm = conn.prepareStatement("INSERT INTO user (name, last_name, age) VALUES (?, ?, ?)");
            pstm.setString(1, name);
            pstm.setString(2, lastName);
            pstm.setByte(3, age);
            int count = pstm.executeUpdate();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        } finally {
            Util.close(conn);
        }
    }

    public void removeUserById(long id) {
        try {
            conn = Util.getConnection();
            PreparedStatement pstm = conn.prepareStatement("DELETE FROM user WHERE id = ?");
            pstm.setLong(1, id);
            int count = pstm.executeUpdate();
//            System.out.println("Удалено " + count);
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        } finally {
            Util.close(conn);
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();

        try {
            conn = Util.getConnection();
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM user"); //TODO: as Stream?
            while(resultSet.next()) {
                User user = new User(resultSet.getString("name"), resultSet.getString("last_name"), resultSet.getByte("age"));
                user.setId(resultSet.getLong("id"));
                users.add(user);
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        } finally {
            Util.close(conn);
        }

        return users;
    }

    public void cleanUsersTable() {
        try {
            conn = Util.getConnection();
            Statement statement = conn.createStatement();
            statement.executeUpdate("TRUNCATE TABLE user");
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        } finally {
            Util.close(conn);
        }
    }
}
