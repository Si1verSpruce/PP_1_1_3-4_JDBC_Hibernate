package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    private static final String HOST = "jdbc:mysql://localhost:3306/kataacademyuserdb";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(HOST, USERNAME, PASSWORD);
        } catch (SQLException ex) {
            throw new RuntimeException("Cannot connect to the database", ex);
        }
    }
}
