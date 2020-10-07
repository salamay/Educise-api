package com.school.webapp.Information.EditInformation;

import com.school.webapp.JDBC.JDBCConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeleteStudent {
    private boolean result;
    public boolean delete(int id, String session) {
        System.out.println("[DeleteStudent]:Deleting student information-->Setting up connection");
        Connection connection= JDBCConnection.connector();
        if (connection!=null){
            String QUERY="delete from "+session+" where id=?";
            try {
                PreparedStatement preparedStatement=connection.prepareStatement(QUERY);
                preparedStatement.setInt(1,id);
                System.out.println("[DeleteStudent]:Deleting student information-->Deleting information");
                result=preparedStatement.execute();
                System.out.println("[DeleteStudent]:Deleting student information-->Result 1:"+result);

            } catch (SQLException e) {
                e.printStackTrace();
                try {
                    connection.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            if (!result){
                return true;
            }else {
                return  false;
            }
        }else {
            return false;
        }
    }
}
