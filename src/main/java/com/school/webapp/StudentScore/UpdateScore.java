package com.school.webapp.StudentScore;

import com.school.webapp.JDBC.JDBCConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UpdateScore {
    private  int i;
    public String updateScore(UpdateScoreRequestEntity updateScoreRequestEntity) {
        System.out.println("[UpdateScore]:"+updateScoreRequestEntity.getTable());
        System.out.println("[UpdateScore]:"+updateScoreRequestEntity.getId());
        System.out.println("[UpdateScore]:"+updateScoreRequestEntity.getCa());
        System.out.println("[UpdateScore]:"+updateScoreRequestEntity.getScore());

        Connection connection= JDBCConnection.connector();
        String Query="update "+updateScoreRequestEntity.getTable()+" set "+updateScoreRequestEntity.getCa()+"=?"+" where id=?";
        if (connection!=null){
            try {
                PreparedStatement preparedStatement=connection.prepareStatement(Query);
                preparedStatement.setString(1,updateScoreRequestEntity.getScore());
                preparedStatement.setString(2,updateScoreRequestEntity.getId());
                i=preparedStatement.executeUpdate();
                System.out.println("[UpdateScore]: "+ i);

            } catch (SQLException e) {
                e.printStackTrace();
                try {
                    connection.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    return null;
                }
            }finally {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    return null;
                }
            }
        }else {
            return null;
        }
        if(i==1){
            return "SUCCESS";
        }else {
            return null;
        }

    }
}
