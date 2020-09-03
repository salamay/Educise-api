package com.school.webapp.Information.EditInformation;

import com.school.webapp.JDBC.JDBCConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class EditInformation {
    private boolean result;
    private boolean result2;
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
                if (column.equals("Studentname")){
                    String QUERY2="update schoolfee set studentname=? where id=?";
                    PreparedStatement preparedStatement1=connection.prepareStatement(QUERY2);
                    preparedStatement1.setString(1,newValue);
                    preparedStatement1.setString(2,id);
                    result2=preparedStatement1.execute();
                    System.out.println("[EditInformation]: EditingstudentInformation-->Result2: "+result2);
                }

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

            if (!result||!result2){
                return true;
            }else {
                return false;
            }
        }else {
            return false;
        }
    }
}
