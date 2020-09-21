package com.school.webapp.StudentScore;

import com.school.webapp.JDBC.JDBCConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class getStudentScore {
   private  Scores scores;
   private ArrayList<Scores> scoresList;
    public ArrayList<Scores> getScore(getStudentScoreRequestEntity getStudentScoreRequestEntity) {
        Connection connection = JDBCConnection.connector();
        ResultSet resultSet;
        if (connection != null) {
            scoresList=new ArrayList<>();
            String Query = "Select * from " + getStudentScoreRequestEntity.getTable() + " Where Studentname=?";

            try {
                PreparedStatement preparedStatement = connection.prepareStatement(Query);
                preparedStatement.setString(1, getStudentScoreRequestEntity.getName());
                resultSet = preparedStatement.executeQuery();
                if (resultSet!=null){
                    while (resultSet.next()) {
                        scores=new Scores();
                        String Subject = resultSet.getString("Subject");
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
                        scores.setSubject(Subject);
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