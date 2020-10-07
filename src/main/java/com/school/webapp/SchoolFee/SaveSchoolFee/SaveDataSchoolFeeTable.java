package com.school.webapp.SchoolFee.SaveSchoolFee;

import com.school.webapp.JDBC.JDBCConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SaveDataSchoolFeeTable {
    private int i;
    public boolean saveDataToTable(String studentid, String column, String entity) {
        Connection connection= JDBCConnection.connector();
        if (connection!=null){
            String QUERY="update schoolfee set "+column+"="+"? where id=?";
            try {
                PreparedStatement preparedStatement=connection.prepareStatement(QUERY);
                preparedStatement.setString(1,entity);
                preparedStatement.setString(2,studentid);
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
