package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;


public class Main {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();

        userService.createUsersTable();
        userService.saveUser("Гарик", "Харламов", (byte) 39);
        userService.saveUser("Павел", "Воля", (byte) 41);
        userService.saveUser("Руслан", "Белый", (byte) 40);
        userService.saveUser("Гарик", "Мартиросян", (byte) 46);
        userService.removeUserById(2);
        userService.getAllUsers();
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
