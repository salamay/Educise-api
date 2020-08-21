package com.school.webapp.StudentScore;

import com.school.webapp.JDBC.JDBCConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class getStudentScore {
   private  Scores scores;
    public Scores saveScore(getStudentScoreRequestEntity getStudentScoreRequestEntity) {
        Connection connection = JDBCConnection.connector();
        if (connection != null) {
            String Query = "Select * from " + getStudentScoreRequestEntity.getTable() + " Where Studentname=?";

            PreparedStatement preparedStatement = null;
            try {
                preparedStatement = connection.prepareStatement(Query);
                preparedStatement.setString(1, getStudentScoreRequestEntity.getName());
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {

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
                    scores=new Scores(Subject, FirstCa, SecondCa, ThirdCa, FourthCa, FifthCa, SeventhCa, SixthCa, EightCa, NinthCa, Tenthca, Exam, Cumm);
                    System.out.println(scores.getSubject());
                }

            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
            return scores;
        }else{
            return null;
        }
     }

    }