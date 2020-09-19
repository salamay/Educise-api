package com.school.webapp.RetrieveParentInformation;

import com.school.webapp.JDBC.JDBCConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RetrieveParentInformation {
    //This Responselist instance is the original response;
    //it contains the childnamelist and the childpictureslist;
    private ArrayList<RetrieveParentInformationResponseEntity> Responselist;
    private List<String> childnameslist;
    private List<byte[]> childpictureslist;
    private ResultSet resultSet;


    public RetrieveParentInformationResponseEntity retrieveParentInfo(String session,String parentname) {
        System.out.println("[RetrieveParentInformation]-->Retrieving parent Information-->Data provided: " + "session:" + session + ": parentname: " + parentname);
        Connection conn= JDBCConnection.connector();
        String Query = "Select Studentname, Mothername,Fathername,Fatherpicture,MotherPicture,picture from " + session + " where Fathername =? or Mothername=?";
        RetrieveParentInformationResponseEntity retrieveParentInformationResponseEntity=new RetrieveParentInformationResponseEntity();
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(Query);
            preparedStatement.setString(1, parentname);
            preparedStatement.setString(2, parentname);
            resultSet = preparedStatement.executeQuery();
            System.out.println("[RetrieveParentInformation]: Query Executed");
            childnameslist=new ArrayList<>();
            childpictureslist=new ArrayList<>();
            while (resultSet.next()) {
                retrieveParentInformationResponseEntity.setFathername(resultSet.getString("Fathername"));
                retrieveParentInformationResponseEntity.setMothername(resultSet.getString("Mothername"));
                childnameslist.add(resultSet.getString("StudentName"));
                Blob fatherpicture = resultSet.getBlob("Fatherpicture");
                Blob motherpicture = resultSet.getBlob("Motherpicture");
                Blob associateChildpictures = resultSet.getBlob("Picture");
                retrieveParentInformationResponseEntity.setFatherpicture(fatherpicture.getBytes(1,(int)fatherpicture.length()));
                retrieveParentInformationResponseEntity.setMotherpictures(motherpicture.getBytes(1,(int)motherpicture.length()));
                childpictureslist.add(associateChildpictures.getBytes(1,(int)associateChildpictures.length()));
            }
            retrieveParentInformationResponseEntity.setAssociatechildnames(childnameslist);
            retrieveParentInformationResponseEntity.setAssociatechildpictures(childpictureslist);
        }catch (SQLException e){
            try {
                conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
                return null;
            }
            e.printStackTrace();
            return null;
        }finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        }
       if (resultSet!=null){
           return retrieveParentInformationResponseEntity;
       }else {
           return null;
       }
    }
}
