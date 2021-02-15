package com.school.webapp.RegisterTeacher;

import com.school.webapp.JDBC.JDBCConnection;
import com.school.webapp.WebAppService.MyException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class RetrieveTeacherName {
    private ArrayList<TeachernamesResponseEntity> teacherNames;
    private TeachernamesResponseEntity teachernamesResponseEntity;
    public ArrayList<TeachernamesResponseEntity> getTeachersName(String schoolid) throws MyException {
        Connection connection= JDBCConnection.connector();
        if (connection!=null){
            String QUERY="Select id,FirstName,MiddleName,LastName from teacherinformation where schoolid=?";
            try {
                PreparedStatement preparedStatement=connection.prepareStatement(QUERY);
                preparedStatement.setString(1,schoolid);
                ResultSet resultSet=preparedStatement.executeQuery();
                teacherNames=new ArrayList<>();
                teachernamesResponseEntity=new TeachernamesResponseEntity();
                while (resultSet.next()){
                    teachernamesResponseEntity.setFirstname(resultSet.getString("FirstName"));
                    teachernamesResponseEntity.setMiddlename(resultSet.getString("MiddleName"));
                    teachernamesResponseEntity.setLastname(resultSet.getString("LastName"));
                    teachernamesResponseEntity.setTeacherid(resultSet.getString("id"));
                    teacherNames.add(teachernamesResponseEntity);
                }
                return teacherNames;
            } catch (SQLException e) {
                e.printStackTrace();
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
            throw new MyException("Data not available, please wait while we kindly fix the issue");
        }
    }
}
