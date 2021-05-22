package com.school.webapp.WebAppService.RetrieveSession;

import com.school.webapp.JDBC.JDBCConnection;
import com.school.webapp.RequestModel.AddClassModel;
import com.school.webapp.WebAppService.MyException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;

public class AddClass {
    private boolean result;
    public boolean add(AddClassModel clas, String schoolid) throws MyException {
        ResultSet resultSet=null;
        ArrayList<String> list= new ArrayList<>();
        System.out.println("[Adding Class]: Adding Class");
        System.out.println("School id: "+schoolid);
        System.out.println("[Adding Class]: Preparing connection");
        Connection connection = JDBCConnection.connector();
        if (connection!=null){
            String CheckForClass="select * from classstorage where classes=?";
            System.out.println("[Adding Class]: preparing query");
            String QUERY = "insert into classstorage (id,schoolid,classes) values(?,?,?)";
            PreparedStatement prs;
            try {
                PreparedStatement checkforClassPs=connection.prepareStatement(CheckForClass);
                checkforClassPs.setString(1,clas.getClassname());
                ResultSet r=checkforClassPs.executeQuery();
                String id = null;
                while (r.next()){
                    id=r.getString("id");
                }
                if (id!=null){
                    throw new MyException("Class already added");
                } else {
                    prs = connection.prepareStatement(QUERY);

                    prs.setString(1, UUID.randomUUID().toString());
                    prs.setString(2,schoolid);
                    prs.setString(3,clas.getClassname());
                    result=prs.execute();

                }
                if (!result){
                    return true;
                }else {
                    throw new MyException("An error occur when adding class");
                }

            } catch (SQLException e) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    throw new MyException("An error occur when adding class");
                }
                e.printStackTrace();
                throw new MyException("Unable to add class");
            }finally {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }else {
            throw new MyException("Unable to add class,please wait while we fix this issue");
        }
    }
}
