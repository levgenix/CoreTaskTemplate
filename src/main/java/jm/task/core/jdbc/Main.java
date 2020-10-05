package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();
//        userService.createUsersTable();
        userService.dropUsersTable();
        userService.createUsersTable();
        userService.cleanUsersTable();
        userService.saveUser("Name", "Last name", (byte) 45);
        userService.saveUser("Name 2", "Last name 2", (byte) 88);
//        System.out.println(userService.getAllUsers());
        for(User user : userService.getAllUsers()) {
            System.out.println(user);
        }
//        userService.removeUserById(1);
//        userService.cleanUsersTable();
    }
}
