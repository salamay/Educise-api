package com.school.webapp.WebAppService.SaveSchoolFee.GetSchoolFee;

import com.school.webapp.JDBC.JDBCConnection;
import com.school.webapp.WebAppService.MyException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

//this class get the school fees from the database
//it returns a list of schoolfees
public class GetSchoolFee {
    private ArrayList<getSchoolFeeResponseEntity> list;

    public ArrayList<getSchoolFeeResponseEntity> getFee(String clas, String term, String year, String tag, String schoolid) throws MyException {

        System.out.println("[GetSchoolFee]:getting School fee-->Setting up connection");
        Connection connection= JDBCConnection.connector();
        if (connection!=null){
            String QUERY="select * from schoolfee where class =? and term=? and year=? and tag=? and schoolid=?order by  studentname";
            ResultSet resultSet=null;
            try {
                PreparedStatement preparedStatement=connection.prepareStatement(QUERY);
                preparedStatement.setString(1,clas);
                preparedStatement.setString(2,term);
                preparedStatement.setString(3,year);
                preparedStatement.setString(4,tag);
                preparedStatement.setString(5,schoolid);
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
            } catch (SQLException e) {
                e.printStackTrace();
                try {
                    connection.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    throw new MyException("An error occur");
                }
                throw new MyException("An error occur");
            }
            if (resultSet==null){
                throw new MyException("Empty");
            }
            return list;
        }else {
            throw new MyException("An error occur, please wait while we fix this issue");
        }
    }
}
