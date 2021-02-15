package com.school.webapp.BookStore.SellBook;


import com.school.webapp.BookStore.SaveBook.BookEntity;
import com.school.webapp.JDBC.JDBCConnection;
import com.school.webapp.WebAppService.MyException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

//This class handles selling of books and also handles history of book sold
public class SellBook {
    private boolean i;
    private boolean j;

    //Book Entity instance contains the book information to be sold
    public void SellBook(String bookid,String schoolid, String buyer, BookEntity bookEntity,String session) throws MyException {
        System.out.println("[SellBook]: preparing connection");
        Connection connection= JDBCConnection.connector();
        if (connection!=null){
            String QUERY="update book_entity set copies=copies-1 where id=? and schoolid=?";
            try {
                PreparedStatement preparedStatement=connection.prepareStatement(QUERY);
                preparedStatement.setString(1,bookid);
                preparedStatement.setString(2,schoolid);
                //if everything goes well,it return false
                i=preparedStatement.execute();
                System.out.println("[SellBook]: Result1-->"+i);
            } catch (SQLException e) {
                e.printStackTrace();
                try {
                    connection.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }else {
            throw new MyException("An error occured, please wait while we fix this issue");
        }
        if (!i){
            ///Saving to book sold history
            String QUERY="insert into book_history(title,author,amountsold,buyer,datesold,term,year,schoolid) values(?,?,?,?,?,?,?,?)";
            try {
                PreparedStatement preparedStatement=connection.prepareStatement(QUERY);
                preparedStatement.setString(1,bookEntity.getTitle());
                preparedStatement.setString(2,bookEntity.getAuthor());
                preparedStatement.setString(3,String.valueOf(bookEntity.getPrice()));
                preparedStatement.setString(4,buyer);
                preparedStatement.setString(5,bookEntity.getDate());
                preparedStatement.setString(6,bookEntity.getTerm());
                preparedStatement.setString(7,session);
                preparedStatement.setString(8,schoolid);
                j=preparedStatement.execute();
                System.out.println("[SellBook]: Result2-->"+j);
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
    }
   }
}
