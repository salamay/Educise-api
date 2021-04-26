package com.school.webapp.WebAppService.StudentScore;

import com.school.webapp.JDBC.JDBCConnection;
import com.school.webapp.WebAppService.MyException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UpdateScore {
    private  boolean result;
    private  Scores scores;
    public Scores updateScore(UpdateScoreRequestEntity updateScoreRequestEntity) throws MyException {
        System.out.println("[UpdateScore]:"+updateScoreRequestEntity.getId());
        System.out.println("[UpdateScore]:"+updateScoreRequestEntity.getCa());
        System.out.println("[UpdateScore]:"+updateScoreRequestEntity.getScore());

        Connection connection= JDBCConnection.connector();
        String Query="update studentscore set "+updateScoreRequestEntity.getCa()+"=?"+" where id=?";
        String Query2="select * from studentscore where id=?";
        if (connection!=null){
            try {
                PreparedStatement preparedStatement=connection.prepareStatement(Query);
                preparedStatement.setString(1,updateScoreRequestEntity.getScore());
                preparedStatement.setString(2,updateScoreRequestEntity.getId());
                result=preparedStatement.execute();
                System.out.println("[UpdateScore]: Result: "+ result);
                //If everything goes well ,it fetch data to update The UI
                if (!result){
                    PreparedStatement preparedStatement2=connection.prepareStatement(Query2);
                    preparedStatement2.setString(1,updateScoreRequestEntity.getId());
                    ResultSet resultSet=preparedStatement2.executeQuery();
                    if (resultSet!=null){
                        while (resultSet.next()){
                            scores=new Scores();
                            String id=resultSet.getString("id");
                            String Subject = resultSet.getString("Subject");
                            String term=resultSet.getString("term");
                            double FirstCa = resultSet.getDouble("firstca");
                            double SecondCa = resultSet.getDouble("secondca");
                            double ThirdCa = resultSet.getDouble("thirdca");
                            double FourthCa = resultSet.getDouble("fourthCa");
                            double FifthCa = resultSet.getDouble("fifthCa");
                            double SixthCa = resultSet.getDouble("sixthca");
                            double SeventhCa = resultSet.getDouble("seventhca");
                            double EightCa = resultSet.getDouble("eightca");
                            double NinthCa = resultSet.getDouble("ninthca");
                            double Tenthca = resultSet.getDouble("tenthca");
                            double Exam = resultSet.getDouble("exam");
                            double Cumm = resultSet.getDouble("cummulative");
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
                        }
                    }else {
                        throw new MyException("Result is empty");
                    }
                }else {
                    throw new MyException("An error occur");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                try {
                    connection.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    throw new MyException("Something went wrong when updating subject");
                }
                throw new MyException("Something went wrong when updating subject");
            }finally {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    throw new MyException("Something went wrong when updating subject");
                }
            }
        }else {
            throw new MyException("Something went wrong when updating score, please wait while we fix this issue");
        }
        return scores;
    }
}
