package com.school.webapp.StudentScore;

import com.school.webapp.JDBC.JDBCConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UpdateScore {

    public String updateScore(UpdateScoreRequestEntity updateScoreRequestEntity){
        System.out.println(updateScoreRequestEntity.getName());
        System.out.println(updateScoreRequestEntity.getCa());
        System.out.println(updateScoreRequestEntity.getScore());
        System.out.println(updateScoreRequestEntity.getSubject());

        Connection connection= JDBCConnection.connector();
        String Query="update "+updateScoreRequestEntity.getTable()+" set "+updateScoreRequestEntity.getCa()+" = ? "+"where studentname=? and Subject=?";
        System.out.println(Query);
        if (connection!=null){
            try {

                PreparedStatement preparedStatement=connection.prepareStatement(Query);
                preparedStatement.setString(1,updateScoreRequestEntity.getScore());
                preparedStatement.setString(2,updateScoreRequestEntity.getName());
                preparedStatement.setString(3,updateScoreRequestEntity.getSubject());
                int i=preparedStatement.executeUpdate();
                System.out.println("[SaveScoreThread]: "+ i);

            } catch (SQLException e) {
                try {

                    connection.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    return "Failed";
                }
                e.printStackTrace();
            }

            return "SUCESS";
        }else {
            return "FAiLED";
        }

    }
}
