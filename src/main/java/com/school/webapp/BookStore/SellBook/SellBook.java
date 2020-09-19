package com.school.webapp.BookStore.SellBook;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.school.webapp.Repository.BookHistory;
import com.school.webapp.BookStore.SaveBook.BookEntity;
import com.school.webapp.JDBC.JDBCConnection;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

//This class handles selling of books and also handles history of book sold
public class SellBook {
    private boolean i;
    private boolean j;

    //Book Entity instance contains the book information to be sold
    public boolean SellBook(String bookname, String term, String session, String buyer, BookEntity bookEntity){
        System.out.println("[Sellbook]:Selling books-->\r\n bookname: "+bookname+"\r\n session: "+session+"\r\n term: "+term);
        System.out.println("[SellBook]: preparing connection");
        Connection connection= JDBCConnection.connector();
        if (connection!=null){
            String QUERY="update book_entity set copies=copies-1 where year=? and  title=?  and term=? ";
            try {
                PreparedStatement preparedStatement=connection.prepareStatement(QUERY);
                preparedStatement.setString(1,session);
                preparedStatement.setString(2,bookname);
                preparedStatement.setString(3,term);
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
                return false;
            }
        }else {
            return false;
        }
        if (!i){
            ///Saving to book sold history
            String QUERY="insert into book_history(id,title,author,amountsold,buyer,datesold,term,year) values(?,?,?,?,?,?,?,?)";
            try {
                PreparedStatement preparedStatement=connection.prepareStatement(QUERY);
                preparedStatement.setInt(1,bookEntity.getId());
                preparedStatement.setString(2,bookEntity.getTitle());
                preparedStatement.setString(3,bookEntity.getAuthor());
                preparedStatement.setString(4,String.valueOf(bookEntity.getPrice()));
                preparedStatement.setString(5,buyer);
                preparedStatement.setString(6,bookEntity.getDate());
                preparedStatement.setString(7,bookEntity.getTerm());
                preparedStatement.setString(8,session);
                j=preparedStatement.execute();
                System.out.println("[SellBook]: Result2-->"+j);
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
            if (!i&&!j){
                return true;
            }else {return false;}
        }else {
            return false;
        }
    }
//    public ArrayList<BookHistoryResponse> getBookSoldeHistory(){
//        ArrayList<BookHistoryResponse> bookhistory=new ArrayList<>();
//        //bookHistoryRepository.findAll().forEach(bookhistory::add);
//        return null;
//    }
}
