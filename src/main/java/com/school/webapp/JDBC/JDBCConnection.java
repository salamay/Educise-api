package com.school.webapp.JDBC;

import java.sql.Connection;
import java.sql.DriverManager;


public class JDBCConnection {

    public static Connection connector(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url=String.format("jdbc:mysql://localhost:3306/my_spring_user");
            Connection conn= DriverManager.getConnection(url,"root","salamay");
            System.out.println("connecting");
            return conn;
        }catch (Exception e){
            System.out.println("Failed to connect");
            e.printStackTrace();
            return null;
        }
    }

}
