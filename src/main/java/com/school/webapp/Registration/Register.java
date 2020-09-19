package com.school.webapp.Registration;

import com.school.webapp.JDBC.JDBCConnection;
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
    private int queryresponse;
    private int schoolfeetablequeryresponse;
    public String Register(RegistrationModel registrationModel, String session, MultipartFile studentpicture, MultipartFile fatherpicture, MultipartFile motherpicture) {
        //Saving the file temporarily
        Path path= Paths.get(System.getProperty("user.dir")+"/webapp");
        System.out.println("[Registering]: "+"saving file temporarily");
        System.out.println("[Registering]: "+"studentpicture =+"+studentpicture.getOriginalFilename());
        File student=new File(path+studentpicture.getOriginalFilename());

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
        ///////////////////preparing input stream////////////////////////

        try {
            s=new FileInputStream(student);
            f=new FileInputStream(father);
            m=new FileInputStream(mother);
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
        System.out.println(registrationModel.getGender());
        System.out.println(registrationModel.getClas());
        if (connection!=null){
            System.out.println("[Registering]: "+"Preparing Query");
            String SaveNameQuery="INSERT INTO "+session+"(Studentname,Phoneno,nickname,hobbies,turnon,turnoff,club,rolemodel,futureambition,age,fathername,mothername,nextofkin,address,Gender,picture,Fatherpicture,motherpicture,studentclass,tag) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

            ////////////////////////////////////////////////////////////////////////////////////////////////////////////
            ////////////////////////////////////////////////////////////////////////////////////////////////////////////
            ///This insert name into the school fee table so that the bursary department will work with it
            String savenametoschoolfee="insert into schoolfee (studentname,year,class,tag) values(?,?,?,?)";
            try {
                PreparedStatement schoolfeePreparedStatement=connection.prepareStatement(savenametoschoolfee);
                schoolfeePreparedStatement.setString(1,registrationModel.getStudentname());
                schoolfeePreparedStatement.setString(2,session);
                schoolfeePreparedStatement.setString(3,registrationModel.getClas());
                schoolfeePreparedStatement.setString(4,registrationModel.getTag());
                schoolfeetablequeryresponse=schoolfeePreparedStatement.executeUpdate();
                System.out.println("[QueryResponse]: "+schoolfeetablequeryresponse);
            } catch (SQLException e) {
                e.printStackTrace();
                try {
                    connection.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            PreparedStatement preparedStatement= null;
            try {
                    preparedStatement = connection.prepareStatement(SaveNameQuery);
                    preparedStatement.setString(1,registrationModel.getStudentname());
                    preparedStatement.setString(2,registrationModel.getPhoneno());
                    preparedStatement.setString(3,registrationModel.getNickname());
                    preparedStatement.setString(4,registrationModel.getHobbies());
                    preparedStatement.setString(5,registrationModel.getTurnon());
                    preparedStatement.setString(6,registrationModel.getTurnoff());
                    preparedStatement.setString(7,registrationModel.getClub());
                    preparedStatement.setString(8,registrationModel.getRolemodel());
                    preparedStatement.setString(9,registrationModel.getFutureambition());
                    preparedStatement.setInt(10,registrationModel.getAge());
                    preparedStatement.setString(11,registrationModel.getFathername());
                    preparedStatement.setString(12,registrationModel.getMothername());
                    preparedStatement.setString(13,registrationModel.getNextofkin());
                    preparedStatement.setString(14,registrationModel.getAddress());
                    preparedStatement.setString(15,registrationModel.getGender());
                    preparedStatement.setBinaryStream(16,s,(int)student.length());
                    preparedStatement.setBinaryStream(17,f,(int)father.length());
                    preparedStatement.setBinaryStream(18,m,(int)mother.length());
                    preparedStatement.setString(19,registrationModel.getClas());
                    preparedStatement.setString(20,registrationModel.getTag());
                    queryresponse=preparedStatement.executeUpdate();
                    System.out.println("[QueryResponse]: "+queryresponse);
                } catch (SQLException e) {
                    e.printStackTrace();
                try {
                    connection.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    try {
                        connection.close();
                    } catch (SQLException exc) {
                        exc.printStackTrace();
                    }
                    return null;
                }
                return null;
                }finally {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }


        }else {
            return null;
        }
        if (queryresponse==1&&schoolfeetablequeryresponse==1){
            return "SUCCESS";
        }else {
            return null;
        }
    }
}
