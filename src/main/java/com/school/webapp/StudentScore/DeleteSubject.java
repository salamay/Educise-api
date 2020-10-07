package com.school.webapp.StudentScore;

import com.school.webapp.JDBC.JDBCConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeleteSubject {
    private int i;
    public boolean deleteSubject(String id, String session) {
        System.out.println("[DeleteSubject]-->Deleting Subject");
        Connection connection= JDBCConnection.connector();
        if (connection!=null){
            String QUERY="delete from "+session+" where id=?";
            try {
                PreparedStatement preparedStatement=connection.prepareStatement(QUERY);
                preparedStatement.setString(1,id);
                i=preparedStatement.executeUpdate();
                System.out.println("[DeleteSubject]-->Deleting Subject: QUERY result: "+i);
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }else {
            return false;
        }
        if (i==1){
            return true;
        }else {
            return false;
        }
    }
}
