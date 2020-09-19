package com.school.webapp.RetrieveSession;

import com.school.webapp.JDBC.JDBCConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class RetrieveScoreSession {

    public ArrayList<String> retrieve() {
        ResultSet resultSet=null;
        ArrayList<String> list= new ArrayList<>();
        System.out.println("[RetrievingScore session]: Retrieving score session");
        System.out.println("[RetrievingScore session]: Preparing connection");
        Connection connection = JDBCConnection.connector();
        if (connection!=null){
            System.out.println("[RetrievingScore session]: preparing query");
            String QUERY = "Select * from sessionscore where 1";
            resultSet = null;
            PreparedStatement prs;
            try {
                prs = connection.prepareStatement(QUERY);
                resultSet = prs.executeQuery();
                while (resultSet.next()) {
                    list.add(resultSet.getString("sessionscore"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
                try {
                    connection.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                return null;
            }finally {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }else {
            return null;
        }
        if (resultSet!=null){
            return list;
        }else {
            return null;
        }

    }
}
