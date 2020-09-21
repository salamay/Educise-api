package com.school.webapp.RetrieveParentNames;

import com.school.webapp.JDBC.JDBCConnection;
import com.school.webapp.RetrievNameFromSession.RetrieveNameResponse;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class RetrieveParentName {

    public ArrayList<RetrieveParentNameResponse> getParentNames(String session){
        System.out.println("[RetrieveParentName]-->Retrieving parent names: Connecting to database");
        Connection conn = JDBCConnection.connector();
        if (conn != null) {
            ResultSet resultSet;
            ArrayList<RetrieveParentNameResponse> list = new ArrayList<>();
            String Query = "Select Fathername,Mothername from " + session+" order by Fathername and Mothername";
            try {
                PreparedStatement preparedStatement = conn.prepareStatement(Query);
                resultSet = preparedStatement.executeQuery();
                System.out.println("[RetrieveParentName]:--> Query executed");
                while (resultSet.next()) {
                    RetrieveParentNameResponse retrieveParentNameResponse=new RetrieveParentNameResponse();
                    retrieveParentNameResponse.setFathername(resultSet.getString("Fathername"));
                    retrieveParentNameResponse.setMothername(resultSet.getString("Mothername"));
                    System.out.println("[RetrieveParentName]:--> getting Result.....");
                    list.add(retrieveParentNameResponse);
                }
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }finally {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    return null;
                }
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
