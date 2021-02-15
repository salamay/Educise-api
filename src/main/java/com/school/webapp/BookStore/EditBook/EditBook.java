package com.school.webapp.BookStore.EditBook;

import com.school.webapp.BookStore.Book;
import com.school.webapp.BookStore.SaveBook.BookEntity;
import com.school.webapp.JDBC.JDBCConnection;
import com.school.webapp.WebAppService.MyException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EditBook {
    private boolean result;
    public BookEntity edit(EditBookRequest editBookRequest, String schoolid) throws MyException {
        System.out.println("[EditBook]:Editing book-->Preparing connection");
        Connection connection= JDBCConnection.connector();
        if (connection!=null){
            BookEntity book=new BookEntity();
            String QUERY="update book_entity set "+editBookRequest.getColumn()+"=? where id=? and schoolid=?";
            String QUERY2="Select * from book_entity where id=? and schoolid=?";
            try {
                PreparedStatement preparedStatement=connection.prepareStatement(QUERY);
                preparedStatement.setString(1,editBookRequest.getEntity());
                preparedStatement.setString(2,editBookRequest.getId());
                preparedStatement.setString(3,schoolid);
                //if everything goes well,it returns false
                result=preparedStatement.execute();
                System.out.println("[EditBook]: Result-->"+result);
                if (!result){
                    PreparedStatement preparedStatement2=connection.prepareStatement(QUERY2);
                    preparedStatement2.setString(1,editBookRequest.getId());
                    preparedStatement2.setString(2,schoolid);
                    ResultSet resultSet=preparedStatement2.executeQuery();
                    if (resultSet==null){
                        throw new MyException("An error occur");
                    }
                    while (resultSet.next()){
                        book.setId(Integer.parseInt(resultSet.getString("id")));
                        book.setTitle(resultSet.getString("title"));
                        book.setAuthor(resultSet.getString("author"));
                        book.setPrice(resultSet.getInt("price"));
                        book.setCopies(resultSet.getInt("copies"));
                        book.setYear(resultSet.getString("year"));
                        book.setTerm(resultSet.getString("term"));
                    }
                }
                return book;
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
            throw new MyException("An error occured, please wait while we fix this issue");
        }
    }
}
