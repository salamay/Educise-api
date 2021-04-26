package com.school.webapp.BookStore.getBookSoldHistory;

import com.school.webapp.JDBC.JDBCConnection;
import com.school.webapp.Repository.BookHistory;
import com.school.webapp.WebAppService.MyException;
import com.school.webapp.WebAppService.SaveSchoolFee.getDebtors.Jasperprintdoc;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class getBookSoldHistory {
    private ArrayList<BookHistory> bookHistories;
    public ArrayList<BookHistory> getHistories(String session, int term, String date, String schoolid) throws MyException {
        Connection connection= JDBCConnection.connector();
        if (connection!=null){
            ResultSet resultSet;
            String QUERY="select * from book_history where datesold=? and  year=? and term=? and schoolid=?";
            try {
                PreparedStatement preparedStatement=connection.prepareStatement(QUERY);
                preparedStatement.setString(1,date);
                preparedStatement.setString(2,session);
                preparedStatement.setInt(3,term);
                preparedStatement.setString(4,schoolid);
                resultSet=preparedStatement.executeQuery();
                bookHistories=new ArrayList<>();
                while (resultSet.next()){
                    BookHistory bookHistory=new BookHistory();
                    bookHistory.setBuyer(resultSet.getString("buyer"));
                    bookHistory.setAmountsold(resultSet.getString("amountsold"));
                    bookHistory.setAuthor(resultSet.getString("author"));
                    bookHistory.setDate(resultSet.getString("datesold"));
                    bookHistory.setId(resultSet.getString("id"));
                    bookHistory.setTerm(resultSet.getString("term"));
                    bookHistory.setSession(resultSet.getString("year"));
                    bookHistory.setTitle(resultSet.getString("title"));
                    bookHistories.add(bookHistory);
                }
                try {
                    JasperDesign jd= JRXmlLoader.load("src/main/java/com/school/webapp/BookStore/booksoldhistory.jrxml");
                    JRDesignQuery jrDesignQuery=new JRDesignQuery();
                    String sql="select * from book_history where datesold='"+date+"'"+" and  year='"+session+"'"+" and term='"+term+"' and schoolid='"+schoolid+"'";
                    jrDesignQuery.setText(sql);
                    jd.setQuery(jrDesignQuery);
                    JasperReport report= JasperCompileManager.compileReport(jd);
                    JasperPrint print= JasperFillManager.fillReport(report,null,connection);
                    Path path= Paths.get(System.getProperty("user.dir")+"/webapp");
                    File file=new File(path+"/booksold.ser");
                    file.createNewFile();
                    Jasperprintdoc jasperprintdoc=new Jasperprintdoc();
                    jasperprintdoc.jasperPrint=print;
                    FileOutputStream fileOutputStream=new FileOutputStream(file);
                    ObjectOutputStream objectOutputStream=new ObjectOutputStream(fileOutputStream);
                    objectOutputStream.writeObject(jasperprintdoc);
                    objectOutputStream.close();
                    fileOutputStream.close();
                    //JasperExportManager.exportReportToPdfStream(print,new FileOutputStream(file));
                    if (!bookHistories.isEmpty()){
                        //the printable document is deserialized and sent as a response
                        bookHistories.get(bookHistories.size()-1).setPdfdocumentbytes(Files.readAllBytes(file.toPath()));
                    }
                } catch (JRException e) {
                    e.printStackTrace();
                    throw new MyException("An error occured");
                } catch (IOException e) {
                    e.printStackTrace();
                    throw new MyException("An error occured");
                }
                if (bookHistories.isEmpty()){
                    throw new MyException("Not found");
                }
                return bookHistories;
            } catch (SQLException e) {
                e.printStackTrace();
                try {
                    connection.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
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
