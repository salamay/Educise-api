package com.school.webapp.WebAppService.SaveSchoolFee;

import com.school.webapp.JDBC.JDBCConnection;
import com.school.webapp.WebAppService.MyException;
import com.school.webapp.WebAppService.SaveSchoolFee.GetSchoolFee.getSchoolFeeResponseEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SaveDataSchoolFeeTable {
    private int i;
    private getSchoolFeeResponseEntity schoolFeeResponseEntity=new getSchoolFeeResponseEntity();
    public getSchoolFeeResponseEntity saveDataToTable(String studentid, String column, String entity, String schoolid) throws MyException {
        Connection connection= JDBCConnection.connector();
        if (connection!=null){
            String QUERY="update schoolfee set "+column+"="+"? where id=? and schoolid=?";
            String QUERY2="Select * from schoolfee where id=? and schoolid=?";
            try {
                PreparedStatement preparedStatement=connection.prepareStatement(QUERY);
                preparedStatement.setString(1,entity);
                preparedStatement.setString(2,studentid);
                preparedStatement.setString(3,schoolid);
                i=preparedStatement.executeUpdate();
                if (i==1){
                    PreparedStatement preparedStatement1=connection.prepareStatement(QUERY2);
                    preparedStatement1.setString(1,studentid);
                    preparedStatement1.setString(2,schoolid);
                    ResultSet resultSet=preparedStatement1.executeQuery();
                    if (resultSet!=null){
                        while (resultSet.next()){
                            schoolFeeResponseEntity.setStudentid(resultSet.getString("id"));
                            schoolFeeResponseEntity.setStudentname(resultSet.getString("Studentname"));
                            schoolFeeResponseEntity.setAmount(resultSet.getInt("amount"));
                            schoolFeeResponseEntity.setClas(resultSet.getString("class"));
                            schoolFeeResponseEntity.setDate(resultSet.getString("paymentdate"));
                            schoolFeeResponseEntity.setDepositorname(resultSet.getString("depositor"));
                            schoolFeeResponseEntity.setYear(resultSet.getString("year"));
                            schoolFeeResponseEntity.setId(resultSet.getString("transactionid"));
                            schoolFeeResponseEntity.setModeofpayment(resultSet.getString("modeofpayment"));
                            schoolFeeResponseEntity.setTerm(resultSet.getString("term"));
                            schoolFeeResponseEntity.setTag(resultSet.getString("tag"));
                        }
                    }else {
                        throw new MyException("Empty");
                    }
                }
                return schoolFeeResponseEntity;
            } catch (SQLException | MyException e) {
                e.printStackTrace();
                throw new MyException("An error occured");
            }finally {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    throw new MyException("An error occured");
                }
            }

        }else {
            throw new MyException("An error occured please wait while we fix this issue");
        }
    }
}
