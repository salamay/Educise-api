package com.school.webapp.StudentScore;

import com.school.webapp.JDBC.JDBCConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

//this class insert subject into the subject column in the table
public class UpdateSubject {
    private int i;
    public String InsertSubject(UpdateSubjectRequestEntity updateSubjectRequestEntity){

        System.out.println("[UpdateSubject]:"+updateSubjectRequestEntity.getName());
        System.out.println("[UpdateSubject]:"+updateSubjectRequestEntity.getSubject());
        System.out.println("[UpdateSubject]:"+updateSubjectRequestEntity.getTable());
        System.out.println("[UpdateSubject]:"+updateSubjectRequestEntity.getTerm());
        Connection connection= JDBCConnection.connector();
        if (connection!=null){
            String Query="update "+ updateSubjectRequestEntity.getTable()+" set Subject=? where Studentname=? and Subject=? and term=?";
            try {

                PreparedStatement preparedStatement=connection.prepareStatement(Query);
                preparedStatement.setString(1, updateSubjectRequestEntity.getSubject());
                preparedStatement.setString(2, updateSubjectRequestEntity.getName());
                preparedStatement.setString(3, updateSubjectRequestEntity.getOldsubject());
                preparedStatement.setString(4,updateSubjectRequestEntity.getTerm());
                System.out.println("Oldsubject:-->"+ updateSubjectRequestEntity.getOldsubject());
                i=preparedStatement.executeUpdate();
                System.out.println("[SaveScoreThread]: "+ i);
            } catch (SQLException e) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                e.printStackTrace();
                return "null";
            }finally {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }else {
            return "null";
        }
        if (i==1){
            return "Success";
        }else {
            return null;
        }

    }
}
