package com.school.webapp.WebAppService.Information.EditInformation;

import com.school.webapp.JDBC.JDBCConnection;
import com.school.webapp.WebAppService.Information.StudentandParent.StudentInformationResponseEntity;
import com.school.webapp.WebAppService.MyException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EditInformation {
    private StudentInformationResponseEntity studentInformationResponseEntity;
    public StudentInformationResponseEntity edit(String newValue, String id, String column) throws MyException {
        Connection connection= JDBCConnection.connector();
        //Two query are going to be executed, the second query will get all info  to keep the UI updated
        if (connection!=null){
            studentInformationResponseEntity=new StudentInformationResponseEntity();
            String QUERY="update studentinformation set "+column+"=? where id=? ";
            String QUERY2="Select * from studentinformation where id=?";
            try {
                PreparedStatement preparedStatement=connection.prepareStatement(QUERY);
                preparedStatement.setString(1,newValue);
                preparedStatement.setString(2,id);
                boolean result=preparedStatement.execute();
                System.out.println("[EditInformation]: EditingstudentInformation-->Result: "+result);
                //if query executed
                if (!result){
                    PreparedStatement preparedStatement2=connection.prepareStatement(QUERY2);
                    preparedStatement2.setString(1,id);
                    ResultSet resultSet=preparedStatement2.executeQuery();
                    while (resultSet.next()){
                        studentInformationResponseEntity.setId(resultSet.getInt("id"));
                        studentInformationResponseEntity.setStudentname(resultSet.getString("StudentName"));
                        studentInformationResponseEntity.setAge(resultSet.getInt("Age"));
                        studentInformationResponseEntity.setPhoneno(resultSet.getString("Phoneno"));
                        studentInformationResponseEntity.setParentphoneno(resultSet.getString("parentphonenumber"));
                        studentInformationResponseEntity.setFathername(resultSet.getString("fathername"));
                        studentInformationResponseEntity.setMothername(resultSet.getString("mothername"));
                        studentInformationResponseEntity.setGuardianname(resultSet.getString("guardianname"));
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
                        studentInformationResponseEntity.setClas(resultSet.getString("studentclass"));
                        studentInformationResponseEntity.setSession(resultSet.getString("academicsession"));
                        studentInformationResponseEntity.setTag(resultSet.getString("tag"));
                    }
                }
                if (studentInformationResponseEntity==null){
                    throw new MyException("Failed to get a fresh student information");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                try {
                    connection.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    throw new MyException("An error occured while editing information");
                }
                throw new MyException("An error occured while editing information");
            }finally {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    throw new MyException("An error occured while editing information");
                }

            }

        }else {
            throw new MyException("Information cannot be edited at this moment, please wait while we fix this issue");
        }
        return studentInformationResponseEntity;
    }
}
