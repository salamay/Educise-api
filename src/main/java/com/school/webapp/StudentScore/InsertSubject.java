package com.school.webapp.StudentScore;

import com.school.webapp.JDBC.JDBCConnection;

import javax.management.Query;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InsertSubject {
    private int i;
    public String insertSubject(String subject,String session,String studentname){
        Connection connection= JDBCConnection.connector();
        if (connection!=null){
            String QUERY="insert into "+session+" (Subject,Studentname) values(?,?)";
            try {
                PreparedStatement preparedStatement=connection.prepareStatement(QUERY);
                preparedStatement.setString(1,subject);
                preparedStatement.setString(2,studentname);
                i=preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
                try {
                    connection.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    return null;
                }
                return null;
            }finally {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    return null;
                }
            }
        }else {
            return  null;
        }
        if (i==1){
            System.out.println("[WebService]-->Subject Inserted");
            return "SUCCESS";
        }else {
            return null;
        }
    }
}
