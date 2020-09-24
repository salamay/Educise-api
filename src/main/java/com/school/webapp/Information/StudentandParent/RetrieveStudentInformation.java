package com.school.webapp.Information.StudentandParent;

import com.school.webapp.JDBC.JDBCConnection;

import java.sql.*;

public class RetrieveStudentInformation {
    private StudentInformationResponseEntity studentInformationResponseEntity;
    public static Blob studentblob;
    public static Blob fatherblob;
    public static Blob motherblob;


    public StudentInformationResponseEntity retrieveStudentInformation(String name,String classselected){
        System.out.println(name);
        System.out.println(classselected);
        ResultSet resultSet;
        Connection connection= JDBCConnection.connector();
        String Query="Select * from "+classselected+" where StudentName=?";
        if (connection!=null){
            studentInformationResponseEntity=new StudentInformationResponseEntity();
            try {
                PreparedStatement preparedStatement=connection.prepareStatement(Query);
                preparedStatement.setString(1,name);
                resultSet=preparedStatement.executeQuery();
                while (resultSet.next()) {
                    studentInformationResponseEntity.setId(resultSet.getInt("id"));
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
                    studentInformationResponseEntity.setClas(resultSet.getString("studentclass"));
                    studentInformationResponseEntity.setTag(resultSet.getString("tag"));
                    studentblob = resultSet.getBlob("Picture");
                    fatherblob = resultSet.getBlob("Fatherpicture");
                    motherblob = resultSet.getBlob("Motherpicture");
                    studentInformationResponseEntity.setStudent(studentblob.getBytes(1,(int) studentblob.length()));
                    studentInformationResponseEntity.setFather(fatherblob.getBytes(1,(int) fatherblob.length()));
                    studentInformationResponseEntity.setMother(motherblob.getBytes(1,(int) motherblob.length()));
                }

        }catch (SQLException e){
                System.out.println("[RetrieveStudentInformation]:Unable to get student information");
                e.printStackTrace();
                try {
                    connection.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                return null;
            }finally {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    return null;
                }
            }
        }else {
            return null;
        }
        if(resultSet==null){
            return null;
        }else {
            System.out.println("[RetrieveStudentInformation]:Information retrieved");
            return studentInformationResponseEntity;
        }
    }
}
