package com.school.webapp.RetrievNameFromSession;

import com.school.webapp.JDBC.JDBCConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class RetrieveName {
    ArrayList<String> list;
    public ArrayList<String> retrieveName(String classname){

        System.out.println("[RetrieveName]: Settiong up connection");
        Connection connection= JDBCConnection.connector();
        if (connection!=null){
            list=new ArrayList<>();
            System.out.println("[RetrieveName]: Connected successfully");
            String Query = "Select StudentName from " + classname + " Where 1";
            ResultSet resultSet = null;
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(Query);
                resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    list.add(resultSet.getString("Studentname"));
                }
                System.out.println(list);
            } catch (SQLException e) {
                try {
                    connection.close();
                    return null;
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
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
