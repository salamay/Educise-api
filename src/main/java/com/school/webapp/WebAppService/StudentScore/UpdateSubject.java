package com.school.webapp.WebAppService.StudentScore;

import com.school.webapp.JDBC.JDBCConnection;
import com.school.webapp.WebAppService.MyException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//this class insert subject into the subject column in the table
public class UpdateSubject {
    private boolean result;
    private  Scores scores;
    public Scores InsertSubject(UpdateSubjectRequestEntity updateSubjectRequestEntity) throws MyException {

        System.out.println("[UpdateSubject]:"+updateSubjectRequestEntity.getId());
        System.out.println("[UpdateSubject]:"+updateSubjectRequestEntity.getSubject());
        Connection connection= JDBCConnection.connector();
        if (connection!=null){
            String Query="update studentscore set Subject=? where id=?";
            String Query2="select * from studentscore where id=?";
            try {
                PreparedStatement preparedStatement=connection.prepareStatement(Query);
                preparedStatement.setString(1, updateSubjectRequestEntity.getSubject());
                preparedStatement.setString(2, updateSubjectRequestEntity.getId());
                result=preparedStatement.execute();
                System.out.println("[SaveScoreThread]: result : "+ result);
                //if everything goes well, it proceed to getinformation to update the UI
                if (!result){
                    PreparedStatement preparedStatement2=connection.prepareStatement(Query2);
                    preparedStatement2.setString(1,updateSubjectRequestEntity.getId());
                    ResultSet resultSet=preparedStatement2.executeQuery();
                    while (resultSet.next()){
                        scores=new Scores();
                        String id=resultSet.getString("id");
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
                    }
                    if (scores==null){
                        throw new MyException("An error occur");
                    }
                }
            } catch (SQLException e) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                e.printStackTrace();
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
            throw new MyException("Something went wrong when updating subject, please wait while we fix this issue");
        }
        return scores;
    }
}
