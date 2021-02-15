package com.school.webapp.BookStore.DeleteBookHistory;

import com.school.webapp.JDBC.JDBCConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeleteBookHistory {
    private boolean result;
    public boolean deleteHistory(String id) {
        System.out.println("[DeleteBookHistory]:getting book sold history books-->Setting up connectcion");
        Connection connection= JDBCConnection.connector();
        if (connection!=null){
            String query="delete from book_history where id=?";
            try {
                PreparedStatement preparedStatement=connection.prepareStatement(query);
                preparedStatement.setInt(1,Integer.parseInt(id));
                result=preparedStatement.execute();
                System.out.println("[DeleteBookHistory]:Result: "+result);
            } catch (SQLException e) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    return false;
                }
                e.printStackTrace();
            }finally {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    return false;
                }
            }

        }else {
            return false;
        }
        if (!result){
            return true;
        }else {
            return false;
        }
    }
}
