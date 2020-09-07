package com.school.webapp.SchoolFee.SaveSchoolFee.Term;

////This class insert the term into the the school fee table

import com.school.webapp.JDBC.JDBCConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SaveTerm  {
    private int i;
    public boolean Save(String studentname, String clas, String session, String tag, String term) {
        System.out.println("[SaveTerm]:Preparing connection --> Proceeding to database");
        Connection connection= JDBCConnection.connector();
        if (connection!=null){
            String QUERY="update schoolfee set term=? where studentname=? and class=? and year=? and tag=? ";
            try {
                PreparedStatement preparedStatement=connection.prepareStatement(QUERY);
                preparedStatement.setString(1,term);
                preparedStatement.setString(2,studentname);
                preparedStatement.setString(3,clas);
                preparedStatement.setString(4,session);
                preparedStatement.setString(5,tag);
                i=preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
                return false;

            }
        }else {
            return false;
        }
        if (i==1){
            return true;
        }else {
            return false;
        }
    }
}
