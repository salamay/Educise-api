package com.school.webapp.RetrieveParentNames;

import com.school.webapp.JDBC.JDBCConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class RetrieveParentName {

    public ArrayList<String> getParentNames(String session){
        System.out.println("[RetrieveParentName]-->Retrieving parent names: Connecting to database");
        Connection conn = JDBCConnection.connector();
        if (conn != null) {
            ResultSet resultSet;
            ArrayList<String> list = new ArrayList<>();
            String Query = "Select Fathername,Mothername from " + session;
            try {
                PreparedStatement preparedStatement = conn.prepareStatement(Query);
                resultSet = preparedStatement.executeQuery();
                System.out.println("[RetrieveParentName]:--> Query executed");
                while (resultSet.next()) {
                    System.out.println("[RetrieveParentName]:--> getting Result.....");
                    list.add(resultSet.getString("Fathername"));
                    list.add(resultSet.getString("Mothername"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
            if (resultSet!=null){
                return list;
            }else {
                return null;
            }
        } else {
            return null;
        }
    }
}
