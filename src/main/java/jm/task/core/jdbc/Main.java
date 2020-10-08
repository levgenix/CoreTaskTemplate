package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;


import javax.transaction.UserTransaction;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();

//        userService.removeUserById(16);

//        userService.createUsersTable();
//        userService.saveUser("Гарик", "Харламов", (byte) 39);
//        userService.saveUser("Павел", "Воля", (byte) 41);
//        userService.saveUser("Руслан", "Белый", (byte) 40);
//        userService.saveUser("Гарик", "Мартиросян", (byte) 46);
//        userService.getAllUsers();
//        userService.cleanUsersTable();
//        userService.dropUsersTable();
    }
}
