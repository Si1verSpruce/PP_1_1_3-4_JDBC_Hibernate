package jm.task.core.jdbc;

import jm.task.core.jdbc.service.*;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();
        userService.createUsersTable();
        SaveThenPrintUser(userService, "Name1", "LastName2", (byte) 22);
        SaveThenPrintUser(userService, "Name2", "LastName2", (byte) 25);
        SaveThenPrintUser(userService, "Name3", "LastName3", (byte) 31);
        SaveThenPrintUser(userService, "Name4", "LastName4", (byte) 38);
        System.out.println(userService.getAllUsers().toString());
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }

    private static void SaveThenPrintUser(UserService userService, String name, String lastName, byte age) {
        userService.saveUser(name, lastName, age);
        System.out.printf("User с именем — %s добавлен в базу данных%n", name);
    }
}
