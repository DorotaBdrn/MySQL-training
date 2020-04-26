package com.company;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.company.DBclass.callableExample;

public class Main {

    Main project1 = new Main();

    //Database  connection variables
    static Connection connection;
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
        createTable();


        new DBclass().setVisible(true);
        System.out.println("This is Stored procedure: \n");
        callableExample();

    }

    public static void selectData(Statement stmt) throws SQLException {
        ResultSet resultSet = stmt.executeQuery("SELECT * from Student");
        while (resultSet.next()) {
            String name = resultSet.getString("name");
            int id = resultSet.getInt("idStudent");
            System.out.println("id: " + id + " name: " + name);
        }
    }

    public static void createTable() {

        try {
            String q = "CREATE TABLE `StudentDatabase`.`workers` (`wname` VARCHAR(100) NULL,`wage` INT NULL)";
            Statement statement = connection.createStatement();
            statement.execute(q);
            System.out.println("Table Created");
            statement.close();

        } catch (SQLException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }

    }


}

