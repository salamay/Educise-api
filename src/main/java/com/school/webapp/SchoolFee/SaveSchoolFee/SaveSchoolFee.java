package com.school.webapp.SchoolFee.SaveSchoolFee;

import com.school.webapp.JDBC.JDBCConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SaveSchoolFee {
    private boolean result;

    public boolean saveStudentnameToSchoolFee(SaveSchoolFeeRequestEntity saveSchoolFeeRequestEntity) {
        System.out.println("[SaveSchoolFee]: Preparing connection");
        Connection connection= JDBCConnection.connector();
        if (connection!=null){
            String QUERY="insert into schoolfee (studentname,class,term,year,tag,amount) values(?,?,?,?,?,?)";
            try {
                PreparedStatement preparedStatement=connection.prepareStatement(QUERY);
                preparedStatement.setString(1,saveSchoolFeeRequestEntity.getStudentname());
                preparedStatement.setString(2,saveSchoolFeeRequestEntity.getClas());
                preparedStatement.setString(3,saveSchoolFeeRequestEntity.getTerm());
                preparedStatement.setString(4,saveSchoolFeeRequestEntity.getYear());
                preparedStatement.setString(5,saveSchoolFeeRequestEntity.getTag());
                preparedStatement.setString(6,"0");
                result=preparedStatement.execute();
                System.out.println("[SaveSchoolFee]: Result: "+result);
            } catch (SQLException e) {
                e.printStackTrace();
                try {
                    connection.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                return  false;
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
