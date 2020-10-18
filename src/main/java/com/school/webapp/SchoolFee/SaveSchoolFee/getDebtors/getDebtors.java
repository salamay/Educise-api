package com.school.webapp.SchoolFee.SaveSchoolFee.getDebtors;

import com.school.webapp.BookStore.getBookSoldHistory.getBookSoldHistory;
import com.school.webapp.JDBC.JDBCConnection;
import com.school.webapp.SchoolFee.SaveSchoolFee.GetSchoolFee.getSchoolFeeResponseEntity;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class getDebtors {
    private ArrayList<getSchoolFeeResponseEntity> debtores;
    public ArrayList<getSchoolFeeResponseEntity> getDebtorsList(String clas, String term, String year, int minimumfee, String tag) {
        System.out.println("[GetDebtors]:getting debtors-->Setting up connection");
        Connection connection= JDBCConnection.connector();
        ResultSet resultSet=null;
        if (connection!=null){
            debtores=new ArrayList<>();
            String QUERY="Select * from schoolfee where amount<=? and class=? and term=? and year=? and tag=? order by Studentname";
            try {
                PreparedStatement preparedStatement=connection.prepareStatement(QUERY);
                preparedStatement.setInt(1,minimumfee);
                preparedStatement.setString(2,clas);
                preparedStatement.setString(3,term);
                preparedStatement.setString(4,year);
                preparedStatement.setString(5,tag);
                resultSet=preparedStatement.executeQuery();
                getSchoolFeeResponseEntity getSchoolFeeResponseEntity=new getSchoolFeeResponseEntity();
                while (resultSet.next()){
                    getSchoolFeeResponseEntity=new getSchoolFeeResponseEntity();
                    getSchoolFeeResponseEntity.setStudentid(resultSet.getString("id"));
                    getSchoolFeeResponseEntity.setStudentname(resultSet.getString("Studentname"));
                    getSchoolFeeResponseEntity.setModeofpayment(resultSet.getString("modeofpayment"));
                    getSchoolFeeResponseEntity.setTag(resultSet.getString("tag"));
                    getSchoolFeeResponseEntity.setId(resultSet.getString("transactionid"));
                    getSchoolFeeResponseEntity.setDepositorname(resultSet.getString("depositor"));
                    String amount=resultSet.getString("amount");
                    getSchoolFeeResponseEntity.setAmount(Integer.parseInt(amount));
                    getSchoolFeeResponseEntity.setTerm(resultSet.getString("term"));
                    getSchoolFeeResponseEntity.setYear(resultSet.getString("year"));
                    System.out.println(resultSet.getString("Studentname"));
                    getSchoolFeeResponseEntity.setClas(resultSet.getString("Class"));
                    debtores.add(getSchoolFeeResponseEntity);
                }
                try {
                    Path jasperdirictory=Paths.get(System.getProperty("user.dir")+"/jasperreport");
                    JasperDesign jd= JRXmlLoader.load(jasperdirictory+"/debtor.jrxml");
                    String query="select * from schoolfee where amount<="+minimumfee +" and class='"+clas+"'"+" and term='"+term+"'"+" and year='"+year+"'"+" and tag='"+tag+"'";
                    JRDesignQuery jrDesignQuery=new JRDesignQuery();
                    jrDesignQuery.setText(query);
                    jd.setQuery(jrDesignQuery);
                    JasperReport jasperReport= JasperCompileManager.compileReport(jd);
                    JasperPrint jp= JasperFillManager.fillReport(jasperReport,null,connection);
                    Path path= Paths.get(System.getProperty("user.dir")+"/webapp");
                    File file=new File(path+"/debtors.ser");
                    try {
                        file.createNewFile();
                        Jasperprintdoc jasperprintdoc=new Jasperprintdoc();
                        jasperprintdoc.jasperPrint=jp;
                        FileOutputStream fileOutputStream=new FileOutputStream(file);
                        ObjectOutputStream objectOutputStream=new ObjectOutputStream(fileOutputStream);
                        objectOutputStream.writeObject(jasperprintdoc);
                        objectOutputStream.close();
                        fileOutputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    try {
                        //JasperExportManager.exportReportToPdfStream(jp,new FileOutputStream(file));
                        if (!debtores.isEmpty()){
                            //the printable file is deserialize and sent as a response
                            debtores.get(debtores.size()-1).setPdf(Files.readAllBytes(file.toPath()));
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                } catch (JRException e) {
                    System.out.println("[GetDebtors]:getting debtors-->Unable to get report");
                    e.printStackTrace();
                }
            } catch (SQLException e) {
                e.printStackTrace();
                try {
                    connection.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
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
        if (resultSet!=null){

            return debtores;
        }else {
            return null;
        }
    }

}
