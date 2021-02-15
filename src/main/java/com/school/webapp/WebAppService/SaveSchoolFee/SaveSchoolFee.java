package com.school.webapp.WebAppService.SaveSchoolFee;

import com.school.webapp.JDBC.JDBCConnection;
import com.school.webapp.WebAppService.MyException;
import com.school.webapp.WebAppService.SaveSchoolFee.GetSchoolFee.getSchoolFeeResponseEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SaveSchoolFee {
    private boolean result;

    public getSchoolFeeResponseEntity saveStudentnameToSchoolFee(SaveSchoolFeeRequestEntity saveSchoolFeeRequestEntity, String schoolid) throws MyException {
        System.out.println("[SaveSchoolFee]: Preparing connection");
        Connection connection= JDBCConnection.connector();
        if (connection!=null){
            String QUERY="insert into schoolfee (studentname,class,term,year,tag,amount,schoolid) values(?,?,?,?,?,?,?)";
            String QUERY2="Select * from schoolfee where studentname=? and class=? and term=? and year=? and tag=?  and schoolid=?";
            try {
                PreparedStatement preparedStatement=connection.prepareStatement(QUERY);
                preparedStatement.setString(1,saveSchoolFeeRequestEntity.getStudentname());
                preparedStatement.setString(2,saveSchoolFeeRequestEntity.getClas());
                preparedStatement.setString(3,saveSchoolFeeRequestEntity.getTerm());
                preparedStatement.setString(4,saveSchoolFeeRequestEntity.getYear());
                preparedStatement.setString(5,saveSchoolFeeRequestEntity.getTag());
                preparedStatement.setString(6,"0");
                preparedStatement.setString(7,schoolid);
                result=preparedStatement.execute();
                System.out.println("[SaveSchoolFee]: Result: "+result);
                if (!result){
                    PreparedStatement preparedStatement2=connection.prepareStatement(QUERY2);
                    preparedStatement2.setString(1,saveSchoolFeeRequestEntity.getStudentname());
                    preparedStatement2.setString(2,saveSchoolFeeRequestEntity.getClas());
                    preparedStatement2.setString(3,saveSchoolFeeRequestEntity.getTerm());
                    preparedStatement2.setString(4,saveSchoolFeeRequestEntity.getYear());
                    preparedStatement2.setString(5,saveSchoolFeeRequestEntity.getTag());
                    preparedStatement2.setString(6,schoolid);
                    ResultSet resultSet=preparedStatement2.executeQuery();
                    getSchoolFeeResponseEntity schoolFeeResponseEntity=new getSchoolFeeResponseEntity();
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
                    return schoolFeeResponseEntity;
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
