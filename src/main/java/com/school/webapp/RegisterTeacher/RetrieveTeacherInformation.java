package com.school.webapp.RegisterTeacher;

import com.school.webapp.JDBC.JDBCConnection;
import com.school.webapp.WebAppService.MyException;

import java.sql.*;

public class RetrieveTeacherInformation {
    private RegisterTeacherRequestEntity registerTeacherRequestEntity;
    public RegisterTeacherRequestEntity getTeacherInformation(String schoolid, String teaherid) throws MyException {
        Connection connection= JDBCConnection.connector();
        if (connection!=null){
            String QUERY="Select * from teacherinformation where id=? and schoolid=?";
            try {
                PreparedStatement preparedStatement=connection.prepareStatement(QUERY);
                preparedStatement.setString(1,teaherid);
                preparedStatement.setString(2,schoolid);
                ResultSet resultSet=preparedStatement.executeQuery();
                registerTeacherRequestEntity=new RegisterTeacherRequestEntity();
                while (resultSet.next()){
                    registerTeacherRequestEntity.setFirstname(resultSet.getString("FirstName"));
                    registerTeacherRequestEntity.setMiddlename(resultSet.getString("LastName"));
                    registerTeacherRequestEntity.setLastname(resultSet.getString("MiddleName"));
                    registerTeacherRequestEntity.setClas(resultSet.getString("Class"));
                    registerTeacherRequestEntity.setSubjectone(resultSet.getString("SubjectOne"));
                    registerTeacherRequestEntity.setSubjecttwo(resultSet.getString("SubjectTwo"));
                    registerTeacherRequestEntity.setSubjectthree(resultSet.getString("SubjectThree"));
                    registerTeacherRequestEntity.setSubjectfour(resultSet.getString("SubjectFour"));
                    registerTeacherRequestEntity.setAddress(resultSet.getString("Address"));
                    registerTeacherRequestEntity.setEntryyear(resultSet.getString("EntryYear"));
                    registerTeacherRequestEntity.setGender(resultSet.getString("Gender"));
                    registerTeacherRequestEntity.setPhoneno(resultSet.getString("PhoneNo"));
                    registerTeacherRequestEntity.setSchoolattended(resultSet.getString("SchoolAttended"));
                    registerTeacherRequestEntity.setCourse(resultSet.getString("Course"));
                    registerTeacherRequestEntity.setMaritalstatus(resultSet.getString("MaritalStatus"));
                    Blob blobPicture=resultSet.getBlob("Picture");
                    registerTeacherRequestEntity.setFile(blobPicture.getBytes(1,(int) blobPicture.length()));
                    registerTeacherRequestEntity.setBankname(resultSet.getString("bankname"));
                    registerTeacherRequestEntity.setBankaccountnumber(resultSet.getString("accountnumber"));
                    registerTeacherRequestEntity.setAccountname(resultSet.getString("bankaccountname"));
                }
                return registerTeacherRequestEntity;
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

        }else{
            throw new MyException("An error occured, please wait while we fix this issue");
        }
    }
}
