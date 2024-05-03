package org.example;

import java.sql.*;

public class DatabaseManager {
    private Connection connection;
    //private String url = "jdbc:mysql://localhost:3306/mydatabase"; 
    private String url = "jdbc:mysql://localhost:3306/mydatabase?useUnicode=true&characterEncoding=UTF-8";
    private String username = "root"; // השם של משתמש במסד הנתונים
    private String password = "!S054910Yoni"; // הסיסמה של משתמש במסד הנתונים

    // קונסטרקטור
    public DatabaseManager() {
        try {
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // פונקציה להכנסת רשומה חדשה לטבלת הגברים
    public void insertMenRecord(Men men) {
        String query = "INSERT INTO men (Status, FirstName, LastName, Age, Height, Location, Style, Seeking) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            //preparedStatement.setInt(1, men.getPersonID());
            preparedStatement.setString(1, men.getStatus());
            preparedStatement.setString(2, men.getFirstName());
            preparedStatement.setString(3, men.getLastName());
            preparedStatement.setInt(4, men.getAge());
            preparedStatement.setFloat(5, men.getHeight());
            preparedStatement.setString(6, men.getLocation());
            preparedStatement.setString(7, men.getStyle());
            preparedStatement.setString(8, men.getSeeking());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    // פונקציה להכנסת רשומה חדשה לטבלת הנשים
    public void insertWomenRecord(Women women) {
        String query = "INSERT INTO women (PersonID, FirstName, LastName, Age, Location, Style, Seeking) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, women.getPersonID());
            preparedStatement.setString(2, women.getFirstName());
            preparedStatement.setString(3, women.getLastName());
            preparedStatement.setInt(4, women.getAge());
            preparedStatement.setString(5, women.getLocation());
            preparedStatement.setString(6, women.getStyle());
            preparedStatement.setString(7, women.getSeeking());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}

