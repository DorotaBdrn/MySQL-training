package com.company;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Main {

    //Database  connectionvariables
    static Connection connection  = null;
    static String databaseNAme = "StudentDatabase";
    static String url = "jdbc:mysql://localhost:3306/StudentDatabase?useSSL=false=&serverTimezone=UTC";

    static String username = "root";
    static String password = "root";




    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
        connection = DriverManager.getConnection(url, username, password);
        PreparedStatement ps = connection.prepareStatement("INSERT INTO Student (idStudent, name) VALUES (1,'michael clark')");

        int status = ps.executeUpdate();

        if (status != 0){
            System.out.println("Database was connected ");
            System.out.println("Record was inserted");
        }

    }
}

