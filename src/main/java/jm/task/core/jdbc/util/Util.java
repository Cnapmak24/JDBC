package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    private static final String URL = "jdbc:mysql://localhost:3306/myfirstdb";
    private static final String userName = "root";
    private static final String password = "root";
    private static Connection connection;

    public static Connection getConnection() {
        try {
            connection = DriverManager.getConnection(URL, userName, password);
        } catch(SQLException e){
            e.printStackTrace();
        }
        return connection;
    }
}
