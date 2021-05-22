package com.school.webapp.WebAppService.RetrievNameFromSession;

import com.school.webapp.JDBC.JDBCConnection;
import com.school.webapp.WebAppService.MyException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class RetrieveName {
    ArrayList<RetrieveNameResponse> list;
    public ArrayList<RetrieveNameResponse> retrieveName(String classname, String sessionselected, String schoolid) throws MyException {

        System.out.println("[RetrieveName]: Setting up connection");
        Connection connection= JDBCConnection.connector();
        if (connection!=null){
            list=new ArrayList<>();
            System.out.println("[RetrieveName]: Connected successfully");
            String Query = "Select StudentName,id from studentinformation Where Studentclass=? and academicsession=? and schoolid=? order by StudentName";
            ResultSet resultSet = null;
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(Query);
                preparedStatement.setString(1,classname);
                preparedStatement.setString(2,sessionselected);
                preparedStatement.setString(3,schoolid);
                resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    System.out.println(resultSet.getString("id"));
                    RetrieveNameResponse retrieveNameResponse=new RetrieveNameResponse();
                    retrieveNameResponse.setId(resultSet.getString("id"));
                    retrieveNameResponse.setName(resultSet.getString("Studentname"));
                    list.add(retrieveNameResponse);
                }
                System.out.println(list);
            } catch (SQLException e) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    throw new MyException("An error occur while trying t retrieve student names. please wait while we fix this issue");
                }
                e.printStackTrace();
                throw new MyException("An error occur while trying t retrieve student names");
            }finally {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    throw new MyException("An error occur while trying t retrieve student names. please wait while we fix this issue");
                }
            }
            if (resultSet!=null){
                return list;
            }else {
                throw new MyException("Student names not found");
            }
        }else {
            throw new MyException("An error occur while trying t retrieve student names. please wait while we fix this issue");
        }
    }
}
