package com.school.webapp.BookStore.DeletBook;

import com.school.webapp.JDBC.JDBCConnection;
import com.school.webapp.WebAppService.MyException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeleteBook {
    private boolean result;
    public void delete(int id) throws MyException {
        System.out.println("[DeleteBook]:Deleting book-->Settingg up connection");
        Connection connection= JDBCConnection.connector();
        if (connection!=null){
            String QUERY="delete from book_entity where id=?";
            try {
                PreparedStatement preparedStatement=connection.prepareStatement(QUERY);
                preparedStatement.setInt(1,id);
                result=preparedStatement.execute();
                System.out.println("[DeleteBook]:Deleting book-->Result:"+result);
            } catch (SQLException e) {
                e.printStackTrace();
                try {
                    connection.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    throw new MyException("An error occured");
                }
                throw new MyException("An error occured");
            }finally {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    throw new MyException("An error occured");
                }
            }
        }else {
            throw new MyException("An error occured, please wait while we fix this issues");
        }
    }
}
