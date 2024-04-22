package com.royshruti.q24;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DatabaseConnector {

    static Connection conn;

    static {
        // registering driver and establishing connection
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/test";
            String user = "guest";
            String pass = "guest";
            conn = DriverManager.getConnection(url, user, pass);

        } catch (Exception e) {
            System.out.println("Couldn't connect to database");
            e.printStackTrace();

        }

    }

    String getCredentials(String username) {
        try {
            String query = "SELECT Password FROM be21131_Credentials WHERE Username = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, username);
            ResultSet rs = preparedStatement.executeQuery();
            String password = "";

            if (rs.next()) {
                password = rs.getString("Password");
            }

            return password;

        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
            return "";

        }
    }

}
