package com.school.webapp.WebAppService.DeleteSchoolFee;

import com.school.webapp.JDBC.JDBCConnection;
import com.school.webapp.WebAppService.MyException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeleteSchoolFee {
    private boolean result;
    public boolean deleteSchoolFee(String id, String schoolid) throws MyException {
        System.out.println("[DeleteSchoolFee]:Deleting Schoolfee from Schoolfee table-->Making database connection");
        Connection connection= JDBCConnection.connector();
        if (connection!=null){
            String QUERY="delete from schoolfee where id=? and schoolid=?";
            try {
                PreparedStatement preparedStatement=connection.prepareStatement(QUERY);
                preparedStatement.setString(1,id);
                preparedStatement.setString(2,schoolid);
                result=preparedStatement.execute();
                System.out.println("[SQL RESULT]: "+result);
            }catch (SQLException e){
                try {
                    connection.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    throw new MyException("An error occured");
                }
                e.printStackTrace();
            }finally{
                try {
                    connection.close();
                }catch (SQLException e){
                    e.printStackTrace();
                    throw new MyException("An error occured");
                }
            }
            if (!result){
                return true;
            }else{
                return false;
            }
        }else {
            throw new MyException("An error occured, please wait while we fix this issue");
        }
    }
}
