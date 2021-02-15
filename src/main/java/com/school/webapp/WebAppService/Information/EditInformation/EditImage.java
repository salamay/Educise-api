package com.school.webapp.WebAppService.Information.EditInformation;

import com.school.webapp.JDBC.JDBCConnection;
import com.school.webapp.WebAppService.MyException;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class EditImage {
    private FileInputStream fileInputStream;
    public void editImages(EditInformationImageRequestEntity editInformationImageRequestEntity) throws MyException {

        System.out.println("[EditImages]: picture tag: "+editInformationImageRequestEntity.getTag());
        System.out.println("[EditImages]: Student id: "+ editInformationImageRequestEntity.getId());
        Path path= Paths.get(System.getProperty("user.dir")+"/webapp");
        System.out.println("[EditImages]: "+"saving file temporarily");
        File picture=new File(path+"rawimage");
        try {
            picture.createNewFile();
            FileOutputStream outputStream=new FileOutputStream(picture);
            outputStream.write(editInformationImageRequestEntity.getImage());
            fileInputStream=new FileInputStream(picture);

            System.out.println("[Editimages]:Editing information images-->Setting up connection");
            Connection connection= JDBCConnection.connector();
            if (connection!=null){
                if (editInformationImageRequestEntity.getTag().equals("student")){
                    String QUERY="update studentinformation set Picture=? where id=?";
                    try {
                        PreparedStatement preparedStatement=connection.prepareStatement(QUERY);
                        preparedStatement.setBinaryStream(1,fileInputStream,(int)picture.length());
                        preparedStatement.setString(2,editInformationImageRequestEntity.getId());
                        boolean result=preparedStatement.execute();
                        System.out.println("[Editimages]: Edit Information images--> Result: "+result);
                    } catch (SQLException e) {
                        e.printStackTrace();
                        try {
                            connection.close();
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                            throw new MyException("An error occur when saving image");
                        }
                        throw new MyException("An error occur when saving image");
                    }finally {
                        try {
                            connection.close();
                        } catch (SQLException e) {
                            e.printStackTrace();
                            throw new MyException("An error occur when saving image");
                        }
                    }
                }
                ////////////////////////////Chcking if tag is father/////////////////////////////
                if (editInformationImageRequestEntity.getTag().equals("father")){
                    String QUERY="update studentinformation set Fatherpicture=? where id=?";
                    try {
                        PreparedStatement preparedStatement=connection.prepareStatement(QUERY);
                        preparedStatement.setBinaryStream(1,fileInputStream,(int)picture.length());
                        preparedStatement.setString(2,editInformationImageRequestEntity.getId());
                        boolean result=preparedStatement.execute();
                        System.out.println("[Editimages]: Edit Information images--> Result: "+result);
                    } catch (SQLException e) {
                        e.printStackTrace();
                        try {
                            connection.close();
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                            throw new MyException("An error occur when saving image");
                        }
                        throw new MyException("An error occur when saving image");
                    }finally {
                        try {
                            connection.close();
                        } catch (SQLException e) {
                            e.printStackTrace();
                            throw new MyException("An error occur when saving image");
                        }
                    }
                }
                //////////////////////////////////Checking if tag is mother////////////////////////////////////////
                if (editInformationImageRequestEntity.getTag().equals("mother")){
                    String QUERY="update studentinformation set Motherpicture=? where id=?";
                    try {
                        PreparedStatement preparedStatement=connection.prepareStatement(QUERY);
                        preparedStatement.setBinaryStream(1,fileInputStream,(int)picture.length());
                        preparedStatement.setString(2,editInformationImageRequestEntity.getId());
                        boolean result=preparedStatement.execute();
                        System.out.println("[Editimages]: Edit Information images--> Result: "+result);
                    } catch (SQLException e) {
                        e.printStackTrace();
                        try {
                            connection.close();
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                            throw new MyException("An error occur when saving image");
                        }
                        throw new MyException("An error occur when saving image");
                    }finally {
                        try {
                            connection.close();
                        } catch (SQLException e) {
                            e.printStackTrace();
                            throw new MyException("An error occur when saving image");
                        }
                    }
                }
//////////////////////////////////Checking if tag is guardian////////////////////////////////////////
                if (editInformationImageRequestEntity.getTag().equals("mother")){
                    String QUERY="update studentinformation set otherpicture=? where id=?";
                    try {
                        PreparedStatement preparedStatement=connection.prepareStatement(QUERY);
                        preparedStatement.setBinaryStream(1,fileInputStream,(int)picture.length());
                        preparedStatement.setString(2,editInformationImageRequestEntity.getId());
                        boolean result=preparedStatement.execute();
                        System.out.println("[Editimages]: Edit Information images--> Result: "+result);
                    } catch (SQLException e) {
                        e.printStackTrace();
                        try {
                            connection.close();
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                            throw new MyException("An error occur when saving image");
                        }
                        throw new MyException("An error occur when saving image");
                    }
                }
            }else {
                throw new MyException("Unable to edit image, please wait while we fix this issue");
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new  MyException("Unable to edit images, invalid image");
        }
    }
}
