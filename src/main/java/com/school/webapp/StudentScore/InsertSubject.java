package com.school.webapp.StudentScore;

import com.school.webapp.JDBC.JDBCConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

//this class insert subject into the subject column in the table
public class InsertSubject {

    public String InsertSubject(InsertSubjectRequestEntity insertSubjectRequestEntity){

        System.out.println(insertSubjectRequestEntity.getName());
        System.out.println(insertSubjectRequestEntity.getSubject());
        System.out.println(insertSubjectRequestEntity.getTable());

        Connection connection= JDBCConnection.connector();
        if (connection!=null){
            String Query="insert into "+insertSubjectRequestEntity.getTable()+"(Studentname,Subject) values (?,?)";
            try {
                PreparedStatement preparedStatement=connection.prepareStatement(Query);
                preparedStatement.setString(1,insertSubjectRequestEntity.getName());
                preparedStatement.setString(2,insertSubjectRequestEntity.getSubject());
                int i=preparedStatement.executeUpdate();
                System.out.println("[SaveScoreThread]: "+ i);
            } catch (SQLException e) {
                e.printStackTrace();
                return "FAILED";
            }
        }else {
            return "Failed";
        }
        return "Success";
    }
}
