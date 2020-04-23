package com.company;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBclass extends JFrame implements ActionListener {

    static Connection connection = null;
    static String databaseNAme = "StudentDatabase";
    static String url = "jdbc:mysql://localhost:3306/StudentDatabase?useSSL=false=&serverTimezone=UTC";

    static String username = "root";
    static String password = "root";


    public JButton insertStudent;
    public JButton insertTeacher;
    public JTextField input;
    public JTextField teacherNameInput;

    public JButton refresh;
    public JPanel jPanel;
    public JTable jt;

    public JButton update;
    public JButton edit;
    public JTextField updateStudentName;
    public JTextField updateTeacherNameInput;
    public JTextField updateTeacherID;


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


        insertStudent = new JButton("Add Student");
        insertTeacher = new JButton("Add Teacher");
        input = new JTextField(10);
        teacherNameInput = new JTextField(10);
        insertStudent.setBackground(Color.yellow);
        insertTeacher.setBackground(Color.blue);
        input.setBackground(Color.yellow);
        teacherNameInput.setBackground(Color.blue);
        insertTeacher.getMargin();


        String[] columns1 = {"idTeachers", "TeacherName"};
        String[][] data1 = {};

        jt = new JTable(data1, columns1);
        jt.setPreferredScrollableViewportSize(new Dimension(470, 65));
        jt.setFillsViewportHeight(true);


        JScrollPane jScrollPane = new JScrollPane(jt);
        jPanel.add(jScrollPane);


        refresh = new JButton("refresh");

        insertStudent.addActionListener(this);
        insertTeacher.addActionListener(this);
        refresh.addActionListener(this::actionPerformed1);


        add(teacherNameInput);
        add(insertTeacher);
        jPanel.add(refresh);
        add(insertStudent);
        add(input);

        update = new JButton("update");
        edit = new JButton("Edit");
        updateStudentName = new JTextField(10);
        updateTeacherNameInput = new JTextField(10);
        updateTeacherID = new JTextField(5);
        update.setLocation(100, 100);
        update.getMargin();


        //add(teacherNameInput);
        add(updateTeacherNameInput);
        add(updateTeacherID);
        update.addActionListener(this::actionPerformed3);
        edit.addActionListener(this::actionPerformed2);
        add(update);
        add(edit);
        //add(updateStudentName);
    }

    public void actionPerformed3(ActionEvent event) {

        try {
            int id = Integer.parseInt(updateTeacherID.getText());
            PreparedStatement statement = connection.prepareStatement("update Teachers set TeacherName =  ?  where idTeachers = ? ");
            statement.setString(1, updateTeacherNameInput.getText());
            statement.setInt(2, id);
            statement.executeUpdate();
            statement.close();
            System.out.println("Updated");


        } catch (SQLException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    public void actionPerformed2(ActionEvent event) {

        String[] columns1 = {"idTeachers", "TeacherName"};
        String[][] data1 = {};
        DefaultTableModel tableModel = new DefaultTableModel(data1, columns1);
//        jt.setModel(tableModel);
        jt.getModel();

        int row = jt.getSelectedRow();

        String idTeachers = jt.getValueAt(row, 0).toString();
        String TeacherName = jt.getValueAt(row, 1).toString();
        updateTeacherID.setText(idTeachers);
        updateTeacherNameInput.setText(TeacherName);
        System.out.println(idTeachers + " " + TeacherName);


    }


    @Override
    public void actionPerformed(ActionEvent event) {


        try {
            if (event.getActionCommand().equals("Add Student")) {
                String name = input.getText();
                System.out.println(name);
                PreparedStatement statement = connection.prepareStatement(" INSERT INTO Student (name) VALUES(?)");
                statement.setString(1, name);
//            Statement statement = connection.createStatement();
//            String dbop = " INSERT INTO Student (name) VALUES ('" + name + "')";
                statement.execute();
                statement.close();
            } else if (event.getActionCommand().equals("Add Teacher")) {
                String teacherName = teacherNameInput.getText();
                PreparedStatement statement2 = connection.prepareStatement(" INSERT INTO Teachers (teacherName) VALUES(?)");
                statement2.setString(1, teacherName);
                System.out.println(teacherName);
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
