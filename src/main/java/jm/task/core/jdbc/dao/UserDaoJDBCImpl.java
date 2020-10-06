package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }

    private void closeConnection() {
        try {
            Util.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createUsersTable() {
        try {
            Util.getConnection().createStatement()
                    .executeUpdate("CREATE TABLE user " +
                            "(id BIGINT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(255), last_name VARCHAR(255), age INT)");
        } catch (SQLException e) {
//            e.printStackTrace();
        } finally {
            closeConnection();
        }
    }

    public void dropUsersTable() {
        try {
            Util.getConnection().createStatement().executeUpdate("DROP TABLE user");
        } catch (SQLException e) {
//            e.printStackTrace();
        } finally {
            closeConnection();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try {
            PreparedStatement pstm = Util.getConnection()
                    .prepareStatement("INSERT INTO user (name, last_name, age) VALUES (?, ?, ?)");
            pstm.setString(1, name);
            pstm.setString(2, lastName);
            pstm.setByte(3, age);
            if (pstm.executeUpdate() > 0) {
                System.out.println("User с именем – " + name + " добавлен в базу данных");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
    }

    public void removeUserById(long id) {
        try {
            PreparedStatement pstm = Util.getConnection().prepareStatement("DELETE FROM user WHERE id = ?");
            pstm.setLong(1, id);
            pstm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();

        try {
            ResultSet resultSet = Util.getConnection().createStatement().executeQuery("SELECT * FROM user");
            while(resultSet.next()) {
                User user = new User(resultSet.getString("name"),
                        resultSet.getString("last_name"), resultSet.getByte("age"));
                user.setId(resultSet.getLong("id"));
                users.add(user);
                System.out.println(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }

        return users;
    }

    public void cleanUsersTable() {
        try {
            Util.getConnection().createStatement().executeUpdate("TRUNCATE TABLE user");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
    }
}
