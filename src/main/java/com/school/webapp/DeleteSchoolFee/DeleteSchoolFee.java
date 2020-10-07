package com.school.webapp.DeleteSchoolFee;

import com.school.webapp.JDBC.JDBCConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeleteSchoolFee {
    private boolean result;
    public boolean deleteSchoolFee(String id) {
        System.out.println("[DeleteSchoolFee]:Deleting Schoolfee from Schoolfee table-->Making database connection");
        Connection connection= JDBCConnection.connector();
        if (connection!=null){
            String QUERY="delete from schoolfee where id=?";
            try {
                PreparedStatement preparedStatement=connection.prepareStatement(QUERY);
                preparedStatement.setString(1,id);
                result=preparedStatement.execute();
                System.out.println("[SQL RESULT]: "+result);
            } catch (SQLException e) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                e.printStackTrace();
                return false;
            }finally {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        }else {
            return false;
        }
        if (!result){
            return true;
        }else {
            return false;
        }
    }
}
