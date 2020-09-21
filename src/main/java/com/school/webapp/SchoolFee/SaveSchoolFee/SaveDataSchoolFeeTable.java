package com.school.webapp.SchoolFee.SaveSchoolFee;

import com.school.webapp.JDBC.JDBCConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SaveDataSchoolFeeTable {
    private int i;
    public boolean saveDataToTable(String studentname, String clas, String session, String tag, String term, String column, String entity) {
        Connection connection= JDBCConnection.connector();
        if (connection!=null){
            String QUERY="update schoolfee set "+column+"="+"? where studentname=? and class=? and tag=?  and term=? and year=?";
            try {
                PreparedStatement preparedStatement=connection.prepareStatement(QUERY);
                preparedStatement.setString(1,entity);
                preparedStatement.setString(2,studentname);
                preparedStatement.setString(3,clas);
                preparedStatement.setString(4,tag);
                preparedStatement.setString(5,term);
                preparedStatement.setString(6,session);
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
        }else{
            return false;
        }
    }
}
