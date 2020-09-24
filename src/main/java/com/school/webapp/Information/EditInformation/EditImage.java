package com.school.webapp.Information.EditInformation;

import com.school.webapp.JDBC.JDBCConnection;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;

public class EditImage {
    private boolean result;
    private FileInputStream fileInputStream;
    public boolean editImages(EditInformationImageRequestEntity editInformationImageRequestEntity) {

        System.out.println("[EditImages]: "+editInformationImageRequestEntity.getTag());
        System.out.println("[EditImages]: "+editInformationImageRequestEntity.getSession());
        System.out.println("[EditImages]: "+editInformationImageRequestEntity.getStudentname());
        System.out.println("[EditImages]: "+editInformationImageRequestEntity.getClas());
        System.out.println("[EditImages]: "+ Arrays.toString(editInformationImageRequestEntity.getImage()));
        Path path= Paths.get(System.getProperty("user.dir")+"/webapp");
        System.out.println("[EditImages]: "+"saving file temporarily");
        File picture=new File(path+"rawimage");
        try {
            FileOutputStream outputStream=new FileOutputStream(picture);
            outputStream.write(editInformationImageRequestEntity.getImage());
            fileInputStream=new FileInputStream(picture);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        System.out.println("[Editimages]:Editing information images-->Setting up connection");
        Connection connection= JDBCConnection.connector();
        if (connection!=null){
            if (editInformationImageRequestEntity.getTag().equals("student")){
                String QUERY="update "+editInformationImageRequestEntity.getSession()+" set Picture=? where studentname=? and studentclass=?";
                try {
                    PreparedStatement preparedStatement=connection.prepareStatement(QUERY);
                    preparedStatement.setBinaryStream(1,fileInputStream,(int)picture.length());
                    preparedStatement.setString(2,editInformationImageRequestEntity.getStudentname());
                    preparedStatement.setString(3,editInformationImageRequestEntity.getClas());
                    result=preparedStatement.execute();
                    System.out.println("[Editimages]: Edit Information images--> Result: "+result);
                } catch (SQLException e) {
                    e.printStackTrace();
                    try {
                        connection.close();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            }
            ////////////////////////////Chcking if tag is father/////////////////////////////
            if (editInformationImageRequestEntity.getTag().equals("father")){
                String QUERY="update "+editInformationImageRequestEntity.getSession()+" set Fatherpicture=? where studentname=? and studentclass=?";
                try {
                    PreparedStatement preparedStatement=connection.prepareStatement(QUERY);
                    preparedStatement.setBinaryStream(1,fileInputStream,(int)picture.length());
                    preparedStatement.setString(2,editInformationImageRequestEntity.getStudentname());
                    preparedStatement.setString(3,editInformationImageRequestEntity.getClas());
                    result=preparedStatement.execute();
                    System.out.println("[Editimages]: Edit Information images--> Result: "+result);
                } catch (SQLException e) {
                    e.printStackTrace();
                    try {
                        connection.close();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            }
            //////////////////////////////////Checking if tag is mother////////////////////////////////////////
            if (editInformationImageRequestEntity.getTag().equals("mother")){
                String QUERY="update "+editInformationImageRequestEntity.getSession()+" set Motherpicture=? where studentname=? and studentclass=?";
                try {
                    PreparedStatement preparedStatement=connection.prepareStatement(QUERY);
                    preparedStatement.setBinaryStream(1,fileInputStream,(int)picture.length());
                    preparedStatement.setString(2,editInformationImageRequestEntity.getStudentname());
                    preparedStatement.setString(3,editInformationImageRequestEntity.getClas());
                    result=preparedStatement.execute();
                    System.out.println("[Editimages]: Edit Information images--> Result: "+result);
                } catch (SQLException e) {
                    e.printStackTrace();
                    try {
                        connection.close();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            }

        }else {
            return false;
        }
        if (!result){
            return true;
        }else {
            return false;
        }
    }

}
