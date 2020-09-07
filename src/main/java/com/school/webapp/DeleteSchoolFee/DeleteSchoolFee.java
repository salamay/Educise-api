package com.school.webapp.DeleteSchoolFee;

import com.school.webapp.JDBC.JDBCConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeleteSchoolFee {
    private int i;
    public boolean deleteSchoolFee(String clas, String session, String term, String studentname) {
        System.out.println("[DeleteSchoolFee]:Deleting Schoolfee from Schoolfee table-->Making database connection");
        Connection connection= JDBCConnection.connector();
        String QUERY="update schoolfee set depositor='', modeofpayment='', amount='', transactionid='', term='',paymentdate='' where studentname=? and class=? and year=? and term=?";
        try {
            PreparedStatement preparedStatement=connection.prepareStatement(QUERY);
            preparedStatement.setString(1,studentname);
            preparedStatement.setString(2,clas);
            preparedStatement.setString(3,session);
            preparedStatement.setString(4,term);
            i=preparedStatement.executeUpdate();
            System.out.println("[SQL RESULT]: "+i);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        if (connection!=null){


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
