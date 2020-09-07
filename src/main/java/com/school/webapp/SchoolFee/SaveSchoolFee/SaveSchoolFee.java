package com.school.webapp.SchoolFee.SaveSchoolFee;

import com.school.webapp.JDBC.JDBCConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SaveSchoolFee {

    private int i;
    public boolean SaveSchoolFee(SaveSchoolFeeRequestEntity saveSchoolFeeRequestEntity) {
        System.out.println("[Controller]:Saving School fee--> Preparing Connection");
        Connection connection= JDBCConnection.connector();
        if (connection!=null){
            String QUERY="insert into schoolfee (studentname,depositor,class,term,year,modeofpayment,paymentdate,transactionid,amount) values(?,?,?,?,?,?,?,?,?)";
            try {
                PreparedStatement preparedStatement=connection.prepareStatement(QUERY);
                preparedStatement.setString(1,saveSchoolFeeRequestEntity.getStudentname());
                preparedStatement.setString(2,saveSchoolFeeRequestEntity.getDepositorname());
                preparedStatement.setString(3,saveSchoolFeeRequestEntity.getClas());
                preparedStatement.setString(4,saveSchoolFeeRequestEntity.getTerm());
                preparedStatement.setString(5,saveSchoolFeeRequestEntity.getYear());
                preparedStatement.setString(6,saveSchoolFeeRequestEntity.getModeofpayment());
                preparedStatement.setString(7,saveSchoolFeeRequestEntity.getDate());
                preparedStatement.setString(8,saveSchoolFeeRequestEntity.getId());
                preparedStatement.setString(9,saveSchoolFeeRequestEntity.getAmount());
                i=preparedStatement.executeUpdate();
                System.out.println("[Controller]:Saving School fee--> Result= "+i);
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
