package com.database.access;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import org.jasypt.util.password.PasswordEncryptor;
import org.jasypt.util.password.StrongPasswordEncryptor;

public class UpdateSecurePassword {

    public static void main(String[] args) throws Exception {

        String loginUser = "root";
        String loginPasswd = "admin";
        String loginUrl = "jdbc:mysql://ec2-54-219-171-102.us-west-1.compute.amazonaws.com:3306/moviedb"; //orig
        //String loginUrl = "jdbc:mysql://ec2-54-215-233-110.us-west-1.compute.amazonaws.com:3306/moviedb";
        //String loginUrl = "jdbc:mysql://localhost:3306/moviedb";
        //String loginUrl ="";
        
        Class.forName("com.mysql.jdbc.Driver").newInstance();
        Connection connection = DriverManager.getConnection(loginUrl, loginUser, loginPasswd);
        Statement statement = connection.createStatement();

        String alterQuery = "ALTER TABLE customers MODIFY COLUMN password VARCHAR(128)";
        //String alterQuery = "ALTER TABLE employees MODIFY COLUMN password VARCHAR(128)";
        int alterResult = statement.executeUpdate(alterQuery);
        System.out.println("altering customers table schema completed, " + alterResult + " rows affected");

        String query = "SELECT id, password from customers";
        //String query = "SELECT email, password from employees";
        ResultSet rs = statement.executeQuery(query);

        //  it internally use SHA-256 algorithm and 10,000 iterations to calculate the encrypted password
        PasswordEncryptor passwordEncryptor = new StrongPasswordEncryptor();

        ArrayList<String> updateQueryList = new ArrayList<>();

        System.out.println("encrypting password (this might take a while)");
        while (rs.next()) {
        	String id = rs.getString("id");
            //String id = rs.getString("email");
            String password = rs.getString("password");
            
            String encryptedPassword = passwordEncryptor.encryptPassword(password);

            String updateQuery = String.format("UPDATE customers SET password='%s' WHERE id=%s;", encryptedPassword,
                    id);
//            String updateQuery = String.format("UPDATE employees SET password='%s' WHERE email='%s';", encryptedPassword,
//                    id);
            updateQueryList.add(updateQuery);
        }
        rs.close();

        System.out.println("updating password");
        int count = 0;
        for (String updateQuery : updateQueryList) {
            int updateResult = statement.executeUpdate(updateQuery);
            count += updateResult;
        }
        System.out.println("updating password completed, " + count + " rows affected");

        statement.close();
        connection.close();

        System.out.println("finished");

    }

}
