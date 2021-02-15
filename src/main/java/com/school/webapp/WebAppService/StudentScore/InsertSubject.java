package com.school.webapp.WebAppService.StudentScore;

import com.school.webapp.JDBC.JDBCConnection;
import com.school.webapp.WebAppService.MyException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class InsertSubject {
    private int i;
    private Scores scores;
    public Scores insertSubject(String subject, String session, String studentname, String term, String schoolid) throws MyException {
        Connection connection= JDBCConnection.connector();
        if (connection!=null){
            String QUERY="insert into studentscore (Subject,Studentname,term,schoolid,academicsession) values(?,?,?,?,?)";
            String QUERY2="select * from studentscore where Subject=? and Studentname=? and term=? and schoolid=? and academicsession=?";
            try {
                PreparedStatement preparedStatement=connection.prepareStatement(QUERY);
                preparedStatement.setString(1,subject);
                preparedStatement.setString(2,studentname);
                preparedStatement.setString(3,term);
                preparedStatement.setString(4,schoolid);
                preparedStatement.setString(5,session);
                i=preparedStatement.executeUpdate();
                if (i==1){
                    PreparedStatement preparedStatement2=connection.prepareStatement(QUERY2);
                    preparedStatement2.setString(1,subject);
                    preparedStatement2.setString(2,studentname);
                    preparedStatement2.setString(3,term);
                    preparedStatement2.setString(4,schoolid);
                    preparedStatement2.setString(5,session);
                    ResultSet resultSet=preparedStatement2.executeQuery();
                    if (resultSet!=null){
                       while (resultSet.next()){
                           scores=new Scores();
                           String id=String.valueOf(resultSet.getInt("id"));
                           String Subject = resultSet.getString("Subject");
                           String termm=resultSet.getString("term");
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
                           scores.setTerm(termm);
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
                        throw new MyException("An error occur");
                    }
                    return scores;
                }else {
                    throw new MyException("An error occur");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                try {
                    connection.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    throw new MyException("An error occur");
                }
                throw new MyException("An error occur");
            }finally {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    throw new MyException("An error occur");
                }
            }

        }else {
            throw new MyException("An error occur");
        }
    }
}
