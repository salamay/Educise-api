package com.school.webapp.Information.EditInformation;

import com.school.webapp.JDBC.JDBCConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class EditInformation {
    private boolean result;
    public boolean edit(String newValue, String id, String column, String session) {
        Connection connection= JDBCConnection.connector();
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
                    int i=preparedStatement1.executeUpdate();
                    System.out.println("[EditInformation]: EditingstudentInformation-->Result2: "+i);
                }
                if (column.equals("Studentclass")){
                    String QUERY3="update schoolfee set class=? where id=?";
                    PreparedStatement preparedStatement2=connection.prepareStatement(QUERY3);
                    preparedStatement2.setString(1,newValue);
                    preparedStatement2.setString(2,id);
                    int i=preparedStatement2.executeUpdate();
                    System.out.println("[EditInformation]: EditingstudentInformation-->Result2: "+i);
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
