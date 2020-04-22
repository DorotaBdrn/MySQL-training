package com.company;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.awt.FlowLayout.*;

public class DBclass extends JFrame implements ActionListener {

    static Connection connection = null;
    static String databaseNAme = "StudentDatabase";
    static String url = "jdbc:mysql://localhost:3306/StudentDatabase?useSSL=false=&serverTimezone=UTC";

    static String username = "root";
    static String password = "root";


    public JButton insert;
    public JTextField input;
    public JTextField teacherNameInput;

    public JButton refresh;
    public JPanel jPanel;
    public JTable jt;
    public JTextArea studentName;
    public JTextArea teacherName;


    //    public static void main(String[] args) {
//
//        new DBclass().setVisible(true);
//
//    }
    public DBclass() {
        super("DataBase");
        setTitle("school");
        createConnection();
        setSize(600, 600);
        setResizable(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

//        String[] columns = {"idTeacher", "TeacherName"};
//        String[][] data = {{}};


        refresh = new JButton("refresh");
        jPanel = new JPanel();
        add(jPanel);


//        jPanel.add(jt);


        insert = new JButton("insert");
        studentName = new JTextArea("Student Name");
        input = new JTextField(10);
        teacherName = new JTextArea("Teacher Name");
        teacherNameInput = new JTextField(10);
        teacherName.setBackground(Color.yellow);
        studentName.setBackground(Color.blue);


        String[] columns1 = {"idTeachers", "TeacherName"};
        String[][] data1 = {};

        jt = new JTable(data1, columns1);
        jt.setPreferredScrollableViewportSize(new Dimension(470, 65));
        jt.setFillsViewportHeight(true);


        JScrollPane jScrollPane = new JScrollPane(jt);
        jPanel.add(jScrollPane);


        refresh = new JButton("refresh");

        insert.addActionListener(this);
        refresh.addActionListener(this::actionPerformed1);


        add(studentName);
        add(input);
        add(teacherName);
        add(teacherNameInput);
        jPanel.add(refresh);
        add(insert);
    }

    @Override
    public void actionPerformed(ActionEvent event) {


        try {
            if (event.getActionCommand().equals("studentName")) {
                String name = input.getText();
                System.out.println(name);
                PreparedStatement statement = connection.prepareStatement(" INSERT INTO Student (name) VALUES(?)");
                statement.setString(1, name);
//            Statement statement = connection.createStatement();
//            String dbop = " INSERT INTO Student (name) VALUES ('" + name + "')";
                statement.execute();
                statement.close();
            } else if (event.getActionCommand().equals("teacherName")) {
                String teacherName = teacherNameInput.getText();
                PreparedStatement statement2 = connection.prepareStatement(" INSERT INTO Teachers (teacherName) VALUES(?)");
                statement2.setString(1, teacherName);
                statement2.execute();
                statement2.close();

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void actionPerformed1(ActionEvent event) {


        String[] columns1 = {"idTeachers", "TeacherName"};
        String[][] data1 = {};
        DefaultTableModel tableModel = new DefaultTableModel(data1, columns1);
        jt.setModel(tableModel);


        try {
            System.out.println("Hello");
            Statement stmt = connection.createStatement();
            ResultSet resultSet = stmt.executeQuery("SELECT * from Teachers");
            while (resultSet.next()) {
                String name = resultSet.getString("TeacherName");
                int id = resultSet.getInt("idTeachers");
                System.out.println("id: " + id + " name: " + name);
                tableModel.addRow(new Object[]{id, name});
            }
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    void createConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            connection = DriverManager.getConnection(url, username, password);
        } catch (InstantiationException e) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, e);
        } catch (IllegalAccessException e) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, e);
        } catch (ClassNotFoundException e) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, e);
        } catch (SQLException e) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, e);
        }
    }
}
