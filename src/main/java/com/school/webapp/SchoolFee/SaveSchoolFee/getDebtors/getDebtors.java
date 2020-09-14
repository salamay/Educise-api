package com.school.webapp.SchoolFee.SaveSchoolFee.getDebtors;

import com.school.webapp.JDBC.JDBCConnection;
import com.school.webapp.SchoolFee.SaveSchoolFee.GetSchoolFee.getSchoolFeeResponseEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class getDebtors {
    private ArrayList<getSchoolFeeResponseEntity> debtores;
    public ArrayList<getSchoolFeeResponseEntity> getDebtorsList(String clas, String term, String year, int minimumfee, String tag) {
        System.out.println("[GetDebtors]:getting debtors-->Setting up connection");
        Connection connection= JDBCConnection.connector();
        ResultSet resultSet=null;
        if (connection!=null){
            debtores=new ArrayList<>();
            String QUERY="Select * from schoolfee where amount<? and class=? and term=? and year=? and tag=?";
            try {
                PreparedStatement preparedStatement=connection.prepareStatement(QUERY);
                preparedStatement.setInt(1,minimumfee);
                preparedStatement.setString(2,clas);
                preparedStatement.setString(3,term);
                preparedStatement.setString(4,year);
                preparedStatement.setString(5,tag);
                resultSet=preparedStatement.executeQuery();
                while (resultSet.next()){
                    getSchoolFeeResponseEntity getSchoolFeeResponseEntity=new getSchoolFeeResponseEntity();
                    getSchoolFeeResponseEntity.setStudentname(resultSet.getString("Studentname"));
                    getSchoolFeeResponseEntity.setModeofpayment(resultSet.getString("modeofpayment"));
                    getSchoolFeeResponseEntity.setTag(resultSet.getString("tag"));
                    getSchoolFeeResponseEntity.setId(resultSet.getString("transactionid"));
                    getSchoolFeeResponseEntity.setDepositorname(resultSet.getString("depositor"));
                    String amount=resultSet.getString("amount");
                    getSchoolFeeResponseEntity.setAmount(Integer.parseInt(amount));
                    getSchoolFeeResponseEntity.setTerm(resultSet.getString("term"));
                    getSchoolFeeResponseEntity.setYear(resultSet.getString("year"));
                    System.out.println(resultSet.getString("Studentname"));
                    getSchoolFeeResponseEntity.setClas(resultSet.getString("Class"));
                    debtores.add(getSchoolFeeResponseEntity);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }else {
            return null;
        }
        if (resultSet!=null){
            return debtores;
        }else {
            return null;
        }
    }
}
