package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();

        userService.createUsersTable();
        userService.saveUser("Name", "Last name", (byte) 45);
        userService.saveUser("Герман", "Севостьянов", (byte) 32);
        userService.saveUser("Василий", "Иванов", (byte) 88);
        userService.saveUser("Михаил", "Петров", (byte) 28);
        userService.getAllUsers();
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
