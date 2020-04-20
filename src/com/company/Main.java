package com.company;

import java.sql.*;
import javax.swing.JFrame;

public class Main {

    Main project1 = new Main();

    //Database  connection variables
    static Connection connection = null;
    static String databaseNAme = "StudentDatabase";
    static String url = "jdbc:mysql://localhost:3306/StudentDatabase?useSSL=false=&serverTimezone=UTC";

    static String username = "root";
    static String password = "root";






    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException {


        Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
        connection = DriverManager.getConnection(url, username, password);
//        PreparedStatement ps = connection.prepareStatement("INSERT INTO Student (name) VALUES ('Dorota clark')");
        Statement stmt = connection.createStatement();
        selectData(stmt);


//        int status = ps.executeUpdate();
//
//        if (status != 0) {
//            System.out.println("Database was connected ");
//            System.out.println("Record was inserted");

//        }


//        BD2 data = new BD2();
//        data.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        data.setSize(275,180);
//        data.setVisible(true);


    }
    public static void selectData(Statement stmt) throws SQLException {
        ResultSet resultSet = stmt.executeQuery("SELECT * from Student");
        while (resultSet.next()){
            String name =resultSet.getString( "name");
            int id = resultSet.getInt("idStudent");
            System.out.println("id: " + id + " name: "+ name);

        }
    }
}

