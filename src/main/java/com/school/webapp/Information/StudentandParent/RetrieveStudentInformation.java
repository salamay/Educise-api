package com.school.webapp.Information.StudentandParent;

import com.school.webapp.JDBC.JDBCConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RetrieveStudentInformation {
    private ResultSet resultSet;
    private StudentInformationResponseEntity studentInformationResponseEntity;


    public StudentInformationResponseEntity retrieveStudentInformation(StudentInformationRequestEntity studentInformationRequestEntity){
        System.out.println(studentInformationRequestEntity.getClas());
        System.out.println(studentInformationRequestEntity.getName());
        Connection connection= JDBCConnection.connector();
        String Query="Select * from "+studentInformationRequestEntity.getClas()+" where studentname=?";
        if (connection!=null){
            studentInformationResponseEntity=new StudentInformationResponseEntity();
            try {
                PreparedStatement preparedStatement=connection.prepareStatement(Query);
                preparedStatement.setString(1,studentInformationRequestEntity.getName());
                resultSet=preparedStatement.executeQuery();
                while (resultSet.next()) {
                    studentInformationResponseEntity.setStudentname(resultSet.getString("StudentName"));
                    studentInformationResponseEntity.setAge(resultSet.getInt("Age"));
                    studentInformationResponseEntity.setPhoneno(resultSet.getString("Phoneno"));
                    studentInformationResponseEntity.setFathername(resultSet.getString("fathername"));
                    studentInformationResponseEntity.setMothername(resultSet.getString("mothername"));
                    studentInformationResponseEntity.setAddress(resultSet.getString("Address"));
                    studentInformationResponseEntity.setNextofkin(resultSet.getString("NextOfKin"));
                    studentInformationResponseEntity.setGender(resultSet.getString("Gender"));
                    studentInformationResponseEntity.setClub(resultSet.getString("club"));
                    studentInformationResponseEntity.setRolemodel(resultSet.getString("RoleModel"));
                    studentInformationResponseEntity.setNickname(resultSet.getString("nickname"));
                    studentInformationResponseEntity.setNextofkin(resultSet.getString("nextofkin"));
                    studentInformationResponseEntity.setHobbies(resultSet.getString("hobbies"));
                    studentInformationResponseEntity.setFutureambition(resultSet.getString("futureambition"));
                    studentInformationResponseEntity.setTurnon(resultSet.getString("turnon"));
                    studentInformationResponseEntity.setTurnoff(resultSet.getString("turnoff"));
                }
        }catch (SQLException e){
                e.printStackTrace();
            }
        }
        if (resultSet!=null){
            return studentInformationResponseEntity;
        }else {
            return studentInformationResponseEntity;
        }
    }
}
