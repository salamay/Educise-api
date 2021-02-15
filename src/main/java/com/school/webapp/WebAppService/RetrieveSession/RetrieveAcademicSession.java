package com.school.webapp.WebAppService.RetrieveSession;

import com.school.webapp.JDBC.JDBCConnection;
import com.school.webapp.WebAppService.MyException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class RetrieveAcademicSession {

    public ArrayList<String> retrieve() throws MyException {
        ResultSet resultSet=null;
        ArrayList<String> list= new ArrayList<>();
        System.out.println("[Retrieving session]: Retrieving session");
        System.out.println("[Retrieving session]: Preparing connection");
        Connection connection = JDBCConnection.connector();
        if (connection!=null){
            System.out.println("[RetrievingScore session]: preparing query");
            String QUERY = "Select sessions from sessionstorage";
            resultSet = null;
            PreparedStatement prs;
            try {
                prs = connection.prepareStatement(QUERY);
                resultSet = prs.executeQuery();
                while (resultSet.next()) {
                    list.add(resultSet.getString("sessions"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
                try {
                    connection.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                throw new MyException("An error occur when getting session");
            }finally {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    throw new MyException("Unable to get session");
                }
            }
        }else {
            throw new MyException("Unable to get session,please wait while we fix this issue");
        }
        if (resultSet!=null){
            return list;
        }else {
            throw new MyException("Unable to get session");
        }

    }
}
