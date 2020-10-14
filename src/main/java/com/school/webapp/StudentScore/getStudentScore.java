package com.school.webapp.StudentScore;

import com.school.webapp.JDBC.JDBCConnection;
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

public class getStudentScore {
   private  Scores scores;
   private ArrayList<Scores> scoresList;
    public ArrayList<Scores> getScore(getStudentScoreRequestEntity getStudentScoreRequestEntity) {
        Connection connection = JDBCConnection.connector();
        ResultSet resultSet;
        if (connection != null) {
            scoresList=new ArrayList<>();
            String Query = "Select * from " + getStudentScoreRequestEntity.getTable() + " Where Studentname=? and term =?";

            try {
                PreparedStatement preparedStatement = connection.prepareStatement(Query);
                preparedStatement.setString(1, getStudentScoreRequestEntity.getName());
                preparedStatement.setString(2,getStudentScoreRequestEntity.getTerm());
                resultSet = preparedStatement.executeQuery();
                if (resultSet!=null){
                    while (resultSet.next()) {
                        scores=new Scores();
                        String id=String.valueOf(resultSet.getInt("id"));
                        String Subject = resultSet.getString("Subject");
                        String term=resultSet.getString("term");
                        double FirstCa = resultSet.getDouble("Firstca");
                        double SecondCa = resultSet.getDouble("Secondca");
                        double ThirdCa = resultSet.getDouble("Thirdca");
                        double FourthCa = resultSet.getDouble("FourthCa");
                        double FifthCa = resultSet.getDouble("FifthCa");
                        double SixthCa = resultSet.getDouble("Sixthca");
                        double SeventhCa = resultSet.getDouble("Seventhca");
                        double EightCa = resultSet.getDouble("Eightca");
                        double NinthCa = resultSet.getDouble("Ninthca");
                        double Tenthca = resultSet.getDouble("Tenthca");
                        double Exam = resultSet.getDouble("Exam");
                        double Cumm = resultSet.getDouble("Cummulative");
                        scores.setId(id);
                        scores.setSubject(Subject);
                        scores.setTerm(term);
                        scores.setFirstca(FirstCa);
                        scores.setSecondca(SecondCa);
                        scores.setThirdca(ThirdCa);
                        scores.setFourthca(FourthCa);
                        scores.setFifthca(FifthCa);
                        scores.setSixthca(SixthCa);
                        scores.setSeventhca(SeventhCa);
                        scores.setEightca(EightCa);
                        scores.setNinthca(NinthCa);
                        scores.setTenthca(Tenthca);
                        scores.setExam(Exam);
                        scores.setCumulative(Cumm);
                        scoresList.add(scores);
                    }
                }
                Path path= Paths.get(System.getProperty("user.dir")+"/webapp");
                File file=new File(path+"/studentscores.pdf");
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    Path jasperdirictory=Paths.get(System.getProperty("user.dir")+"/jasperreport");
                    JasperDesign jd= JRXmlLoader.load(jasperdirictory+"/"+"studentscores.jrxml");
                    String sql="Select * from " + getStudentScoreRequestEntity.getTable() + " Where Studentname='"+ getStudentScoreRequestEntity.getName()+"'" +" and term ='"+getStudentScoreRequestEntity.getTerm()+"'";
                    JRDesignQuery jrDesignQuery=new JRDesignQuery();
                    jrDesignQuery.setText(sql);
                    jd.setQuery(jrDesignQuery);
                    JasperReport report= JasperCompileManager.compileReport(jd);
                    JasperPrint print= JasperFillManager.fillReport(report,null,connection);
                    JasperExportManager.exportReportToPdfStream(print,new FileOutputStream(file));
                    if (!scoresList.isEmpty()){
                        scoresList.get(scoresList.size()-1).setPdfdocumenbytes(Files.readAllBytes(file.toPath()));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JRException e) {
                    e.printStackTrace();
                    System.out.println("[getting student score]: unable to get report");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                try {
                    connection.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
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
        }else{
            return null;
        }
        if (resultSet!=null){
            return scoresList;
        }else {
            return null;
        }
     }

    }