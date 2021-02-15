package com.school.webapp.WebAppService.StudentScore;

import com.school.webapp.JDBC.JDBCConnection;
import com.school.webapp.WebAppService.MyException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeleteSubject {
    private int i;
    public void deleteSubject(String id) throws MyException {
        System.out.println("[DeleteSubject]-->Deleting Subject");
        Connection connection= JDBCConnection.connector();
        if (connection!=null){
            String QUERY="delete from studentscore where id=?";
            try {
                PreparedStatement preparedStatement=connection.prepareStatement(QUERY);
                preparedStatement.setString(1,id);
                i=preparedStatement.executeUpdate();
                System.out.println("[DeleteSubject]-->Deleting Subject: QUERY result: "+i);
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
        }else {
            throw new MyException("An error occur, please wait while we fix these issues");
        }
        if (i==1){

        }else {
            throw new MyException("An error occur");
        }
    }
}
