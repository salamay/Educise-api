package com.school.webapp.RegisterTeacher;

import com.school.webapp.JDBC.JDBCConnection;
import com.school.webapp.WebAppService.MyException;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;


public class RegisterTeacher {
    private int a;
    public String Registerteacher(RegisterTeacherRequestEntity registerTeacherRequestEntity, String schoolid) throws MyException {
        System.out.println(registerTeacherRequestEntity.getFirstname());
        System.out.println(registerTeacherRequestEntity.getLastname());
        System.out.println(registerTeacherRequestEntity.getMiddlename());
        System.out.println(registerTeacherRequestEntity.getClas());
        System.out.println(registerTeacherRequestEntity.getSubjectone());
        System.out.println(registerTeacherRequestEntity.getSubjecttwo());
        System.out.println(registerTeacherRequestEntity.getSubjectthree());
        System.out.println(registerTeacherRequestEntity.getSubjectfour());
        System.out.println(registerTeacherRequestEntity.getEmail());
        System.out.println(registerTeacherRequestEntity.getAddress());
        System.out.println(registerTeacherRequestEntity.getEntryyear());
        System.out.println(registerTeacherRequestEntity.getGender());
        System.out.println(registerTeacherRequestEntity.getPhoneno());
        System.out.println(registerTeacherRequestEntity.getSchoolattended());
        System.out.println(registerTeacherRequestEntity.getCourse());
        System.out.println(registerTeacherRequestEntity.getMaritalstatus());
        Connection connection= JDBCConnection.connector();
        if (connection!=null){
            String Query="insert into TeacherInformation(FirstName,LastName,MiddleName,Class,SubjectOne,SubjectTwo,SubjectThree,SubjectFour,Email,Address,EntryYear,Gender,PhoneNo,SchoolAttended,Course,MaritalStatus,Picture,schoolid,id) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement preparedStatement= null;
            try {
                preparedStatement = connection.prepareStatement(Query);
                preparedStatement.setString(1,registerTeacherRequestEntity.getFirstname());
                preparedStatement.setString(2,registerTeacherRequestEntity.getLastname());
                preparedStatement.setString(3,registerTeacherRequestEntity.getMiddlename());
                preparedStatement.setString(4,registerTeacherRequestEntity.getClas());
                preparedStatement.setString(5,registerTeacherRequestEntity.getSubjectone());
                preparedStatement.setString(6,registerTeacherRequestEntity.getSubjecttwo());
                preparedStatement.setString(7,registerTeacherRequestEntity.getSubjectthree());
                preparedStatement.setString(8,registerTeacherRequestEntity.getSubjectfour());
                preparedStatement.setString(9,registerTeacherRequestEntity.getEmail());
                preparedStatement.setString(10,registerTeacherRequestEntity.getAddress());
                preparedStatement.setString(11,registerTeacherRequestEntity.getEntryyear());
                preparedStatement.setString(12,registerTeacherRequestEntity.getGender());
                preparedStatement.setString(13,registerTeacherRequestEntity.getPhoneno());
                preparedStatement.setString(14,registerTeacherRequestEntity.getSchoolattended());
                preparedStatement.setString(15,registerTeacherRequestEntity.getCourse());
                preparedStatement.setString(16,registerTeacherRequestEntity.getMaritalstatus());
                Path path= Paths.get(System.getProperty("user.dir")+"/webapp");
                if (Files.exists(path)){

                }else {
                    Files.createDirectories(path);
                }
                //temporarily saving the file
                File teacherimage=new File(path+"/teacher");
                FileOutputStream outputStream=new FileOutputStream(teacherimage);
                outputStream.write(registerTeacherRequestEntity.getFile());
                //preparing to save to database
                FileInputStream inputStream=new FileInputStream(teacherimage);
                preparedStatement.setBinaryStream(17,inputStream,(int) teacherimage.length());
                preparedStatement.setString(18,schoolid);
                //Generate a unique id
                String id= UUID.randomUUID().toString();
                preparedStatement.setString(19,id);
                a=preparedStatement.executeUpdate();
            } catch (SQLException | FileNotFoundException e) {
                e.printStackTrace();
                throw new MyException("An error occur");
            } catch (IOException e) {
                e.printStackTrace();
                throw new MyException("An error occur");
            } finally {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    throw new MyException("An error occur");
                }
            }
        }else {
            throw new MyException("An error occur");
        }
        if (a==1){
            return "SUCCESS";
        }else {
            throw new MyException("An error occur");
        }
    }
}
