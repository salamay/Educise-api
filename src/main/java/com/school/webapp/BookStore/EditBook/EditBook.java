package com.school.webapp.BookStore.EditBook;

import com.school.webapp.JDBC.JDBCConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class EditBook {
    private boolean result;
    public boolean edit(EditBookRequest editBookRequest) {
        System.out.println("[EditBook]:Editing book-->Preparing connection");
        Connection connection= JDBCConnection.connector();
        if (connection!=null){
            String QUERY="update book_entity set "+editBookRequest.getColumn()+"=? where id=? ";

            try {
                PreparedStatement preparedStatement=connection.prepareStatement(QUERY);
                preparedStatement.setString(1,editBookRequest.getEntity());
                preparedStatement.setString(2,editBookRequest.getId());
                //if everything goes well,it returns false
                result=preparedStatement.execute();
                System.out.println("[EditBook]: Result-->"+result);
            } catch (SQLException e) {
                e.printStackTrace();
                try {
                    connection.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                return false;
            }finally {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (!result){
                return true;
            }else {
                return false;
            }
        }else {
            return false;
        }
    }
}
