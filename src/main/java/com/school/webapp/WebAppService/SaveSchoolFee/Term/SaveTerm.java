package com.school.webapp.WebAppService.SaveSchoolFee.Term;

////This class insert the term into the the school fee table

import com.school.webapp.JDBC.JDBCConnection;
import com.school.webapp.WebAppService.MyException;
import com.school.webapp.WebAppService.SaveSchoolFee.GetSchoolFee.getSchoolFeeResponseEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SaveTerm  {
    private int i;
    public getSchoolFeeResponseEntity Save(String studentid, String term, String schoolid) throws MyException {
        System.out.println("[SaveTerm]:Preparing connection --> Proceeding to database");
        Connection connection= JDBCConnection.connector();
        if (connection!=null){
            String QUERY="update schoolfee set term=? where id=? and schoolid=?";
            String QUERY2="select * from schoolfee where id=? and schoolid=? and term=?";
            try {
                PreparedStatement preparedStatement=connection.prepareStatement(QUERY);
                preparedStatement.setString(1,term);
                preparedStatement.setString(2,studentid);
                preparedStatement.setString(3,schoolid);
                i=preparedStatement.executeUpdate();
                if (i==1){
                    PreparedStatement preparedStatement2=connection.prepareStatement(QUERY2);
                    preparedStatement2.setString(1,studentid);
                    preparedStatement2.setString(2,schoolid);
                    preparedStatement2.setString(3,term);
                    ResultSet resultSet=preparedStatement2.executeQuery();
                    while (resultSet.next()){
                        getSchoolFeeResponseEntity getSchoolFeeResponseEntity=new getSchoolFeeResponseEntity();
                        getSchoolFeeResponseEntity.setStudentid(resultSet.getString("id"));
                        getSchoolFeeResponseEntity.setStudentname(resultSet.getString("Studentname"));
                        getSchoolFeeResponseEntity.setAmount(resultSet.getInt("amount"));
                        getSchoolFeeResponseEntity.setClas(resultSet.getString("class"));
                        getSchoolFeeResponseEntity.setDate(resultSet.getString("paymentdate"));
                        getSchoolFeeResponseEntity.setDepositorname(resultSet.getString("depositor"));
                        getSchoolFeeResponseEntity.setYear(resultSet.getString("year"));
                        getSchoolFeeResponseEntity.setId(resultSet.getString("transactionid"));
                        getSchoolFeeResponseEntity.setModeofpayment(resultSet.getString("modeofpayment"));
                        getSchoolFeeResponseEntity.setTerm(resultSet.getString("term"));
                        getSchoolFeeResponseEntity.setTag(resultSet.getString("tag"));
                        return getSchoolFeeResponseEntity;
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
                try {
                    connection.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    throw new MyException("An error occur");
                }
                throw new MyException("An error occur");
            }finally {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    throw new MyException("An error occur");
                }
            }
        }else {
            throw new MyException("An error occur, please wait while we fix this issue");
        }
        return null;
    }
}
