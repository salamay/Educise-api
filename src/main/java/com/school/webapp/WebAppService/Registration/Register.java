package com.school.webapp.WebAppService.Registration;

import com.school.webapp.JDBC.JDBCConnection;
import com.school.webapp.WebAppService.MyException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@Service
public class Register {
    private FileInputStream s;
    private FileInputStream f;
    private FileInputStream m;
    private FileInputStream o;
    private int queryresponse;
    public String Register(RegistrationModel registrationModel, MultipartFile studentpicture, MultipartFile fatherpicture, MultipartFile motherpicture, MultipartFile otherpicture, String schoolid) throws MyException {
        //Saving the file temporarily
        Path path= Paths.get(System.getProperty("user.dir")+"/webapp");
        System.out.println("[Registering]: "+"saving file temporarily");
        System.out.println("[Registering]: "+"studentpicture ="+studentpicture.getOriginalFilename());
        File student=new File(path+"/"+studentpicture.getOriginalFilename());

        try {
            student.createNewFile();
            FileOutputStream sout=new FileOutputStream(student);
            sout.write(studentpicture.getBytes());
            sout.close();
        } catch (IOException e) {
            System.out.println("[Registering]: "+"unable to create student image file");
            e.printStackTrace();
        }
        System.out.println("[Registering]: "+"fatherpicture =+"+fatherpicture.getOriginalFilename());
        File father=new File(path+fatherpicture.getOriginalFilename());
        try {
            father.createNewFile();
            FileOutputStream fout=new FileOutputStream(father);
            fout.write(fatherpicture.getBytes());
            fout.close();
        } catch (IOException e) {
            System.out.println("[Registering]: "+"unable to create father image file");
            e.printStackTrace();
        }
        System.out.println("[Registering]: "+"motherpicture =+"+motherpicture.getOriginalFilename());
        File mother=new File(path+motherpicture.getOriginalFilename());
        try {
            mother.createNewFile();
            FileOutputStream mout=new FileOutputStream(mother);
            mout.write(motherpicture.getBytes());
        } catch (IOException e) {
            System.out.println("[Registering]: "+"unable to create mother image file");
            e.printStackTrace();
        }

        System.out.println("[Registering]: "+"other picture ="+otherpicture.getOriginalFilename());
        File other=new File(path+"/"+otherpicture.getOriginalFilename());
        try {
            other.createNewFile();
            FileOutputStream sout=new FileOutputStream(other);
            sout.write(otherpicture.getBytes());
            sout.close();
        } catch (IOException e) {
            System.out.println("[Registering]: "+"unable to create other image file");
            e.printStackTrace();
        }
        ///////////////////preparing input stream////////////////////////

        try {
            s=new FileInputStream(student);
            f=new FileInputStream(father);
            m=new FileInputStream(mother);
            o=new FileInputStream(other);
        } catch (FileNotFoundException e) {
            System.out.println("[Registering]: Unable to convert file to input stream");
            e.printStackTrace();
        }
        ///////////////////preparing input stream end////////////////////
        System.out.println("[Registering]: "+"Creating database connector");
        Connection connection= JDBCConnection.connector();
        System.out.println(registrationModel.getStudentname());
        System.out.println(registrationModel.getPhoneno());
        System.out.println(registrationModel.getNickname());
        System.out.println(registrationModel.getHobbies());
        System.out.println(registrationModel.getTurnon());
        System.out.println(registrationModel.getTurnoff());
        System.out.println(registrationModel.getClub());
        System.out.println(registrationModel.getRolemodel());
        System.out.println(registrationModel.getFutureambition());
        System.out.println(registrationModel.getAge());
        System.out.println(registrationModel.getFathername());
        System.out.println(registrationModel.getMothername());
        System.out.println(registrationModel.getNextofkin());
        System.out.println(registrationModel.getAddress());
        System.out.println(registrationModel.getParentphonenumber());
        System.out.println(registrationModel.getGender());
        System.out.println(registrationModel.getClas());
        System.out.println(registrationModel.getSession());
        if (connection!=null){
            System.out.println("[Registering]: "+"Preparing Query");
            String SaveNameQuery="INSERT INTO studentinformation (Studentname,Phoneno,parentphonenumber,nickname,hobbies,turnon,turnoff,club,rolemodel,futureambition,age,fathername,mothername,nextofkin,address,Gender,picture,Fatherpicture,motherpicture,otherpicture,studentclass,tag,schoolid,guardianname,academicsession) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement preparedStatement= null;
            try {
                    preparedStatement = connection.prepareStatement(SaveNameQuery);
                    preparedStatement.setString(1,registrationModel.getStudentname());
                    preparedStatement.setString(2,registrationModel.getPhoneno());
                    preparedStatement.setString(3,registrationModel.getParentphonenumber());
                    preparedStatement.setString(4,registrationModel.getNickname());
                    preparedStatement.setString(5,registrationModel.getHobbies());
                    preparedStatement.setString(6,registrationModel.getTurnon());
                    preparedStatement.setString(7,registrationModel.getTurnoff());
                    preparedStatement.setString(8,registrationModel.getClub());
                    preparedStatement.setString(9,registrationModel.getRolemodel());
                    preparedStatement.setString(10,registrationModel.getFutureambition());
                    preparedStatement.setInt(11,registrationModel.getAge());
                    preparedStatement.setString(12,registrationModel.getFathername());
                    preparedStatement.setString(13,registrationModel.getMothername());
                    preparedStatement.setString(14,registrationModel.getNextofkin());
                    preparedStatement.setString(15,registrationModel.getAddress());
                    preparedStatement.setString(16,registrationModel.getGender());
                    preparedStatement.setBinaryStream(17,s,(int)student.length());
                    preparedStatement.setBinaryStream(18,f,(int)father.length());
                    preparedStatement.setBinaryStream(19,m,(int)mother.length());
                    preparedStatement.setBinaryStream(20,o,(int)other.length());
                    preparedStatement.setString(21,registrationModel.getClas());
                    preparedStatement.setString(22,registrationModel.getTag());
                    preparedStatement.setString(23, schoolid);
                    preparedStatement.setString(24,registrationModel.getGuardianname());
                    preparedStatement.setString(25,registrationModel.getSession());
                    queryresponse=preparedStatement.executeUpdate();
                    System.out.println("[QueryResponse]: "+queryresponse);
                } catch (SQLException e) {
                    e.printStackTrace();
                try {
                    connection.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    throw new MyException("An error occur when registering student");
                }
                throw new MyException("An error occur when registering student");
                }finally {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    throw new MyException("Unable to register student at the moment,please wait while we fix the issue");
                }
            }


        }else {
            throw new MyException("Unable to register student at the moment,please wait while we fix the issue");
        }
        if (queryresponse==1){
            return "SUCCESS";
        }else {
            throw new MyException("Unable to register student");
        }
    }
}
