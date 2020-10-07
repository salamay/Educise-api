package com.school.webapp.Information.EditInformation;

import com.school.webapp.JDBC.JDBCConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class EditInformation {
    private boolean result;
    public boolean edit(String newValue, String id, String column, String session) {
        Connection connection= JDBCConnection.connector();
        //Two Query are going tobe executed here,the normal Query and the qchanges its going to make in the school fee database
        if (connection!=null){
            String QUERY="update "+session+" set "+column+"=? where id=?";
            try {
                PreparedStatement preparedStatement=connection.prepareStatement(QUERY);
                preparedStatement.setString(1,newValue);
                preparedStatement.setString(2,id);
                result=preparedStatement.execute();
                System.out.println("[EditInformation]: EditingstudentInformation-->Result: "+result);

            } catch (SQLException e) {
                e.printStackTrace();
                try {
                    connection.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                return false;
            }finally {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            if (!result){
                return true;
            }else {
                return false;
            }
        }else {
            return false;
        }
    }
}
