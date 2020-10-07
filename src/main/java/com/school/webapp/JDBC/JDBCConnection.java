package com.school.webapp.JDBC;

import java.sql.Connection;
import java.sql.DriverManager;


public class JDBCConnection {

    public static Connection connector(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url=String.format("jdbc:mysql://db-mysql-shekinah-do-user-8092968-0.b.db.ondigitalocean.com:25060/defaultdb");
            Connection conn= DriverManager.getConnection(url,"Salamay","khm9amjota85tzu4");
            System.out.println("connecting");
            return conn;
        }catch (Exception e){
            System.out.println("Failed to connect");
            e.printStackTrace();
            return null;
        }
    }

}
