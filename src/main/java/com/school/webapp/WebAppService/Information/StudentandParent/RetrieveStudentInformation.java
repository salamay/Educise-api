package com.school.webapp.WebAppService.Information.StudentandParent;

import com.school.webapp.JDBC.JDBCConnection;
import com.school.webapp.WebAppService.MyException;

import java.sql.*;

public class RetrieveStudentInformation {
    private StudentInformationResponseEntity studentInformationResponseEntity;
    public static Blob studentblob;
    public static Blob fatherblob;
    public static Blob motherblob;
    public static Blob otherblob;
    public static Blob qrblob;

    public StudentInformationResponseEntity retrieveStudentInformation(String studentid) throws MyException {
        ResultSet resultSet;
        Connection connection= JDBCConnection.connector();
        String Query="Select * from studentinformation where id=?";
        if (connection!=null){
            studentInformationResponseEntity=new StudentInformationResponseEntity();
            try {
                PreparedStatement preparedStatement=connection.prepareStatement(Query);
                preparedStatement.setString(1,studentid);
                resultSet=preparedStatement.executeQuery();
                while (resultSet.next()) {
                    studentInformationResponseEntity.setId(resultSet.getString("id"));
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
                    studentblob = resultSet.getBlob("Picture");
                    fatherblob = resultSet.getBlob("Fatherpicture");
                    motherblob = resultSet.getBlob("Motherpicture");
                    otherblob = resultSet.getBlob("otherpicture");
                    qrblob=resultSet.getBlob("qrcode");
                    studentInformationResponseEntity.setStudent(studentblob.getBytes(1,(int) studentblob.length()));
                    studentInformationResponseEntity.setFather(fatherblob.getBytes(1,(int) fatherblob.length()));
                    studentInformationResponseEntity.setMother(motherblob.getBytes(1,(int) motherblob.length()));
                    studentInformationResponseEntity.setOther(otherblob.getBytes(1,(int) otherblob.length()));
                    studentInformationResponseEntity.setQrcode(qrblob.getBytes(1,(int) qrblob.length()));
                }
        }catch (SQLException e){
                System.out.println("[RetrieveStudentInformation]:Unable to get student information");
                e.printStackTrace();
                try {
                    connection.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    throw new MyException("Unable to get student information");
                }
                throw new MyException("Unable to get student information");
            }finally {
                try {
                    connection.close();
                } catch (SQLException e) {
                }
            }
        }else {
            throw new MyException("Data not available, please wait while we kindly fix the issue");
        }
        if(resultSet==null){
            throw new MyException("Student information not found");
        }else {
            System.out.println("[RetrieveStudentInformation]:Information retrieved");
            return studentInformationResponseEntity;
        }
    }
}
