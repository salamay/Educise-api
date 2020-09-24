package com.school.webapp.SchoolFee.SaveSchoolFee.GetSchoolFee;

import com.school.webapp.JDBC.JDBCConnection;
import kotlin.ResultKt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

//this class get the school fees from the database
//it returns a list of schoolfees
public class GetSchoolFee {
    private ArrayList<getSchoolFeeResponseEntity> list;

    public ArrayList<getSchoolFeeResponseEntity> getFee(String clas, String term, String year) {

        System.out.println("[GetSchoolFee]:getting School fee-->Setting up connection");
        Connection connection= JDBCConnection.connector();
        if (connection!=null){
            String QUERY="select * from schoolfee where class =? and term=? and year=? order by  studentname";
            ResultSet resultSet=null;
            try {
                PreparedStatement preparedStatement=connection.prepareStatement(QUERY);
                preparedStatement.setString(1,clas);
                preparedStatement.setString(2,term);
                preparedStatement.setString(3,year);
                resultSet=preparedStatement.executeQuery();
                list=new ArrayList<>();
                while (resultSet.next()){
                    getSchoolFeeResponseEntity getSchoolFeeResponseEntity=new getSchoolFeeResponseEntity();
                    getSchoolFeeResponseEntity.setStudentname(resultSet.getString("Studentname"));
                    System.out.println(resultSet.getString("Studentname"));
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
            } catch (SQLException e) {
                e.printStackTrace();
            }
            if (resultSet!=null){
                return list;
            }else {
                return null;
            }
        }else {
            return null;
        }
    }
}
