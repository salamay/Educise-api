package com.school.webapp.WebAppService.RetrieveSession;

import com.school.webapp.JDBC.JDBCConnection;
import com.school.webapp.WebAppService.MyException;
import com.school.webapp.WebAppService.RetrievNameFromSession.RetrieveNameResponse;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DeleteClass {
    private boolean isDelete;
    public boolean deleteClass(String id, String schoolid) throws MyException {
        System.out.println("[RetrieveName]: Setting up connection");
        Connection connection= JDBCConnection.connector();
        if (connection!=null){
            System.out.println("[Deleting class]: Connected successfully");
            String Query = "delete from classstorage where id=?";
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(Query);
                preparedStatement.setString(1,id);
                isDelete = preparedStatement.execute();
                System.out.println(isDelete);
            } catch (SQLException e) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    throw new MyException("An error occur while trying to delete class. please wait while we fix this issue");
                }
                e.printStackTrace();
                throw new MyException("An error occur while trying to delete class.");
            }finally {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    throw new MyException("An error occur while trying to delete class. please wait while we fix this issue");
                }
            }

        }else {
            throw new MyException("An error occur while trying to delete class. please wait while we fix this issue");
        }
        if (!isDelete){
            return true;
        }else {
            return false;
        }
    }
    }
