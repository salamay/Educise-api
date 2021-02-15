package com.school.webapp.WebAppService.SaveSchoolFee.GetSchoolFee;

import com.school.webapp.JDBC.JDBCConnection;
import com.school.webapp.WebAppService.MyException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class getSchoolfeeWithoutTerm {
    private ArrayList<getSchoolFeeResponseEntity> list;
    public ArrayList<getSchoolFeeResponseEntity> getSchoolFeeWithoutTermFiltering(String clas, String session, String tag, String schoolid) throws MyException {
        System.out.println("[GetSchoolFee]:getting School fee without term-->Setting up connection");
        Connection connection= JDBCConnection.connector();
        if (connection!=null){
            String QUERY="select * from schoolfee where class =? and year=? and tag=? and schoolid=? order by studentname";
            ResultSet resultSet=null;
            try {
                PreparedStatement preparedStatement=connection.prepareStatement(QUERY);
                preparedStatement.setString(1,clas);
                preparedStatement.setString(2,session);
                preparedStatement.setString(3,tag);
                preparedStatement.setString(4,schoolid);
                resultSet=preparedStatement.executeQuery();
                list=new ArrayList<>();
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
                    list.add(getSchoolFeeResponseEntity);
                }
                return list;
            } catch (SQLException e) {
                e.printStackTrace();
                try {
                    connection.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    throw new MyException("An error occured");
                }
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
            throw new MyException("An error occured, please wait while we fix this issue");
        }
    }
}
