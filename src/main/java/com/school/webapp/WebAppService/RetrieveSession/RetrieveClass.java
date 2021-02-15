package com.school.webapp.WebAppService.RetrieveSession;

import com.school.webapp.JDBC.JDBCConnection;
import com.school.webapp.WebAppService.MyException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class RetrieveClass {

    public ArrayList<String> retrieve(String schoolid) throws MyException {
        ResultSet resultSet=null;
        ArrayList<String> list= new ArrayList<>();
        System.out.println("[Retrieving Classess]: Retrieving Classess");
        System.out.println("School id: "+schoolid);
        System.out.println("[Retrieving Classess]: Preparing connection");
        Connection connection = JDBCConnection.connector();
        if (connection!=null){
            System.out.println("[Retrieving Classess]: preparing query");
            String QUERY = "Select classes from classstorage where schoolid=?";
            resultSet = null;
            PreparedStatement prs;
            try {
                prs = connection.prepareStatement(QUERY);
                prs.setString(1, schoolid);
                resultSet = prs.executeQuery();
                while (resultSet.next()) {
                    list.add(resultSet.getString("classes"));
                }
            } catch (SQLException e) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    throw new MyException("An error occur when getting class");
                }
                e.printStackTrace();
                throw new MyException("Unable to get classess");
            }finally {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }else {
            throw new MyException("Unable to get classess,please wait while we fix this issue");
        }
        if (resultSet!=null){
            return list;
        }else {
            throw new MyException("Unable to get classess");
        }

    }
}
