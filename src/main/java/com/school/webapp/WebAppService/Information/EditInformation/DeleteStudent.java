package com.school.webapp.WebAppService.Information.EditInformation;

import com.school.webapp.JDBC.JDBCConnection;
import com.school.webapp.WebAppService.MyException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeleteStudent {
    public void delete(String id) throws MyException {
        System.out.println("[DeleteStudent]:Deleting student information-->Setting up connection");
        Connection connection= JDBCConnection.connector();
        if (connection!=null){
            String QUERY="delete from studentinformation where id=?";
            try {
                PreparedStatement preparedStatement=connection.prepareStatement(QUERY);
                preparedStatement.setString(1,id);
                System.out.println("[DeleteStudent]:Deleting student information-->Deleting information");
                boolean result=preparedStatement.execute();
                System.out.println("[DeleteStudent]:Deleting student information-->Result :"+result);

            } catch (SQLException e) {
                e.printStackTrace();
                try {
                    connection.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    throw new MyException("An error occured when deleting student");
                }
                throw new MyException("An error occured when deleting student");
            }
        }else {
            throw new MyException("Cannot delete student at the moment, please wait while we fix this issue");
        }
    }
}
