package com.school.webapp.Session;


import com.school.webapp.JDBC.JDBCConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CreateSession {

    public String ExecuteQuery(SessionRequestEntity sessionRequestEntity) {

        System.out.println(sessionRequestEntity.getJss1());
        System.out.println(sessionRequestEntity.getJss2());
        System.out.println(sessionRequestEntity.getJss3());
        System.out.println(sessionRequestEntity.getSs1());
        System.out.println(sessionRequestEntity.getSs2());
        System.out.println(sessionRequestEntity.getSs3());

        String Query = "Create table if not exists " + sessionRequestEntity.getJss1() + "(" +
                "id " + "int auto_increment," +
                "StudentName " + "TEXT," +
                "Studentclass " + "TEXT," +
                "PhoneNo " + "TEXT," +
                "Nickname " + "TEXT," +
                "Hobbies " + "TEXT," +
                "TurnOn " + "TEXT," +
                "TurnOff " + "TEXT," +
                "Gender " + "TEXT," +
                "Club " + "TEXT," +
                "RoleModel " + "TEXT," +
                "FutureAmbition " + "TEXT," +
                "AcademicPerformance " + "TEXT," +
                "Age " + "int(20)," +
                "Fathername " + "TEXT," +
                "Mothername " + "TEXT," +
                "Nextofkin " + "TEXT," +
                "Address " + "TEXT," +
                "Tag " + "TEXT," +
                "Picture " + "MEDIUMBLOB," +
                "Fatherpicture " + "MEDIUMBLOB," +
                "Motherpicture " + "MEDIUMBLOB," +
                "primary key(id)" +
                ")";


        //jss2Query
        String jss2Query = "Create table if not exists " + sessionRequestEntity.getJss2() + "(" +
                "id " + "int auto_increment," +
                "StudentName " + "TEXT," +
                "Studentclass " + "TEXT," +
                "PhoneNo " + "TEXT," +
                "Nickname " + "TEXT," +
                "Hobbies " + "TEXT," +
                "TurnOn " + "TEXT," +
                "TurnOff " + "TEXT," +
                "Gender " + "TEXT," +
                "Club " + "TEXT," +
                "RoleModel " + "TEXT," +
                "FutureAmbition " + "TEXT," +
                "AcademicPerformance " + "TEXT," +
                "Age " + "int(20)," +
                "Fathername " + "TEXT," +
                "Mothername " + "TEXT," +
                "Nextofkin " + "TEXT," +
                "Address " + "TEXT," +
                "Tag " + "TEXT," +
                "Picture " + "MEDIUMBLOB," +
                "Fatherpicture " + "MEDIUMBLOB," +
                "Motherpicture " + "MEDIUMBLOB," +
                "primary key(id)" +
                ")";


        //jss3Query
        String jss3Query = "Create table if not exists " + sessionRequestEntity.getJss3() + "(" +
                "id " + "int auto_increment," +
                "StudentName " + "TEXT," +
                "Studentclass " + "TEXT," +
                "PhoneNo " + "TEXT," +
                "Nickname " + "TEXT," +
                "Hobbies " + "TEXT," +
                "TurnOn " + "TEXT," +
                "TurnOff " + "TEXT," +
                "Gender " + "TEXT," +
                "Club " + "TEXT," +
                "RoleModel " + "TEXT," +
                "FutureAmbition " + "TEXT," +
                "AcademicPerformance " + "TEXT," +
                "Age " + "int(20)," +
                "Fathername " + "TEXT," +
                "Mothername " + "TEXT," +
                "Nextofkin " + "TEXT," +
                "Address " + "TEXT," +
                "Tag " + "TEXT," +
                "Picture " + "MEDIUMBLOB," +
                "Fatherpicture " + "MEDIUMBLOB," +
                "Motherpicture " + "MEDIUMBLOB," +
                "primary key(id)" +
                ")";


        String ss1Query = "Create table if not exists " + sessionRequestEntity.getSs1()+ "(" +
                "id " + "int auto_increment," +
                "StudentName " + "TEXT," +
                "Studentclass " + "TEXT," +
                "PhoneNo " + "TEXT," +
                "Nickname " + "TEXT," +
                "Hobbies " + "TEXT," +
                "TurnOn " + "TEXT," +
                "TurnOff " + "TEXT," +
                "Gender " + "TEXT," +
                "Club " + "TEXT," +
                "RoleModel " + "TEXT," +
                "FutureAmbition " + "TEXT," +
                "AcademicPerformance " + "TEXT," +
                "Age " + "int(20)," +
                "Fathername " + "TEXT," +
                "Mothername " + "TEXT," +
                "Nextofkin " + "TEXT," +
                "Address " + "TEXT," +
                "Tag " + "TEXT," +
                "Picture " + "MEDIUMBLOB," +
                "Fatherpicture " + "MEDIUMBLOB," +
                "Motherpicture " + "MEDIUMBLOB," +
                "primary key(id)" +
                ")";


        //ss2Query
        String ss2Query = "Create table if not exists " + sessionRequestEntity.getSs2() + "(" +
                "id " + "int auto_increment," +
                "StudentName " + "TEXT," +
                "Studentclass " + "TEXT," +
                "PhoneNo " + "TEXT," +
                "Nickname " + "TEXT," +
                "Hobbies " + "TEXT," +
                "TurnOn " + "TEXT," +
                "TurnOff " + "TEXT," +
                "Gender " + "TEXT," +
                "Club " + "TEXT," +
                "RoleModel " + "TEXT," +
                "FutureAmbition " + "TEXT," +
                "AcademicPerformance " + "TEXT," +
                "Age " + "int(20)," +
                "Fathername " + "TEXT," +
                "Mothername " + "TEXT," +
                "Nextofkin " + "TEXT," +
                "Address " + "TEXT," +
                "Tag " + "TEXT," +
                "Picture " + "MEDIUMBLOB," +
                "Fatherpicture " + "MEDIUMBLOB," +
                "Motherpicture " + "MEDIUMBLOB," +
                "primary key(id)" +
                ")";


        //ss3Query
        String ss3Query = "Create table if not exists " + sessionRequestEntity.getSs3() + "(" +
                "id " + "int auto_increment," +
                "StudentName " + "TEXT," +
                "Studentclass " + "TEXT," +
                "PhoneNo " + "TEXT," +
                "Nickname " + "TEXT," +
                "Hobbies " + "TEXT," +
                "TurnOn " + "TEXT," +
                "TurnOff " + "TEXT," +
                "Gender " + "TEXT," +
                "Club " + "TEXT," +
                "RoleModel " + "TEXT," +
                "FutureAmbition " + "TEXT," +
                "AcademicPerformance " + "TEXT," +
                "Age " + "int(20)," +
                "Fathername " + "TEXT," +
                "Mothername " + "TEXT," +
                "Nextofkin " + "TEXT," +
                "Address " + "TEXT," +
                "Tag " + "TEXT," +
                "Picture " + "MEDIUMBLOB," +
                "Fatherpicture " + "MEDIUMBLOB," +
                "Motherpicture " + "MEDIUMBLOB," +
                "primary key(id)" +
                ")";

        ////////////////////////Score session
        String Score1Query = "Create table if not exists " + sessionRequestEntity.getJss1() + "Score" + "(" +
                "id " + "int auto_increment," +
                "StudentName " + "TEXT," +
                "Subject " + "TEXT," +
                "firstca " + "Double," +
                "secondca " + "Double," +
                "thirdca " + "Double," +
                "fourthca " + "Double," +
                "fifthca " + "Double," +
                "sixthca " + "Double," +
                "seventhca " + "Double," +
                "Eightca " + "Double," +
                "Ninthca " + "Double," +
                "Tenthca " + "Double," +
                "Exam " + "Double," +
                "Cummulative " + "Double," +
                "primary key(id)" +
                ")";

        String Score2Query = "Create table if not exists " + sessionRequestEntity.getJss2() + "Score" + "(" +
                "id " + "int auto_increment," +
                "StudentName " + "TEXT," +
                "Subject " + "TEXT," +
                "firstca " + "Double," +
                "secondca " + "Double," +
                "thirdca " + "Double," +
                "fourthca " + "Double," +
                "fifthca " + "Double," +
                "sixthca " + "Double," +
                "seventhca " + "Double," +
                "Eightca " + "Double," +
                "Ninthca " + "Double," +
                "Tenthca " + "Double," +
                "Exam " + "Double," +
                "Cummulative " + "Double," +
                "primary key(id)" +
                ")";

        String Score3Query = "Create table if not exists " + sessionRequestEntity.getJss3() + "Score" + "(" +
                "id " + "int auto_increment," +
                "StudentName " + "TEXT," +
                "Subject " + "TEXT," +
                "firstca " + "Double," +
                "secondca " + "Double," +
                "thirdca " + "Double," +
                "fourthca " + "Double," +
                "fifthca " + "Double," +
                "sixthca " + "Double," +
                "seventhca " + "Double," +
                "Eightca " + "Double," +
                "Ninthca " + "Double," +
                "Tenthca " + "Double," +
                "Exam " + "Double," +
                "Cummulative " + "Double," +
                "primary key(id)" +
                ")";

        String Score4Query = "Create table if not exists " + sessionRequestEntity.getSs1() + "Score" + "(" +
                "id " + "int auto_increment," +
                "StudentName " + "TEXT," +
                "Subject " + "TEXT," +
                "firstca " + "Double," +
                "secondca " + "Double," +
                "thirdca " + "Double," +
                "fourthca " + "Double," +
                "fifthca " + "Double," +
                "sixthca " + "Double," +
                "seventhca " + "Double," +
                "Eightca " + "Double," +
                "Ninthca " + "Double," +
                "Tenthca " + "Double," +
                "Exam " + "Double," +
                "Cummulative " + "Double," +
                "primary key(id)" +
                ")";

        String Score5Query = "Create table if not exists " + sessionRequestEntity.getSs2() + "Score" + "(" +
                "id " + "int auto_increment," +
                "StudentName " + "TEXT," +
                "Subject " + "TEXT," +
                "firstca " + "Double," +
                "secondca " + "Double," +
                "thirdca " + "Double," +
                "fourthca " + "Double," +
                "fifthca " + "Double," +
                "sixthca " + "Double," +
                "seventhca " + "Double," +
                "Eightca " + "Double," +
                "Ninthca " + "Double," +
                "Tenthca " + "Double," +
                "Exam " + "Double," +
                "Cummulative " + "Double," +
                "primary key(id)" +
                ")";

        String Score6Query = "Create table if not exists " + sessionRequestEntity.getSs3() + "Score" + "(" +
                "id " + "int auto_increment," +
                "StudentName " + "TEXT," +
                "Subject " + "TEXT," +
                "firstca " + "Double," +
                "secondca " + "Double," +
                "thirdca " + "Double," +
                "fourthca " + "Double," +
                "fifthca " + "Double," +
                "sixthca " + "Double," +
                "seventhca " + "Double," +
                "Eightca " + "Double," +
                "Ninthca " + "Double," +
                "Tenthca " + "Double," +
                "Exam " + "Double," +
                "Cummulative " + "Double," +
                "primary key(id)" +
                ")";


        System.out.println("[CreatingSessionThread]:" + sessionRequestEntity.getJss1() + "information");
        Connection connection = JDBCConnection.connector();
        if (connection != null) {
            try {

                //jss1 Query
                PreparedStatement preparedStatement = connection.prepareStatement(Query);

                int i = preparedStatement.executeUpdate();
                System.out.println("[CreatingSessionThread]: Query 1 executed");
                System.out.println("[CreatingSessionThread]: " + i);


                //Jss2 query
                PreparedStatement preparedStatement1 = connection.prepareStatement(jss2Query);
                int j = preparedStatement1.executeUpdate();
                System.out.println("[CreatingSessionThread]: Query 2 executed");
                System.out.println("[CreatingSessionThread]: " + j);

                //Jss3 query
                PreparedStatement preparedStatement2 = connection.prepareStatement(jss3Query);
                int k = preparedStatement2.executeUpdate();
                System.out.println("[CreatingSessionThread]: Query 3 executed");
                System.out.println("[CreatingSessionThread]: " + k);

                //ss1 query
                PreparedStatement preparedStatement3 = connection.prepareStatement(ss1Query);
                int l = preparedStatement3.executeUpdate();
                System.out.println("[CreatingSessionThread]: Query 4 executed");
                System.out.println("[CreatingSessionThread]: " + l);

                //ss2query
                PreparedStatement preparedStatement4 = connection.prepareStatement(ss2Query);
                int m = preparedStatement4.executeUpdate();
                System.out.println("[CreatingSessionThread]: Query 5 executed");
                System.out.println("[CreatingSessionThread]: " + m);

                //ss3 query
                PreparedStatement preparedStatement5 = connection.prepareStatement(ss3Query);
                int n = preparedStatement5.executeUpdate();

                System.out.println("[CreatingSessionThread]: Query 6 executed");
                System.out.println("[CreatingSessionThread]: " + n);


                ///////////////////////////////////////////////////////////
                //Adding session name to database
                String SaveSessionQuery = "INSERT INTO SessionTable (sessionname)  VALUES(?)";

                //session query1
                String SaveSessionQuery1 = "INSERT INTO SessionTable (sessionname)  VALUES(?)";
                //Session query 2
                String SaveSessionQuery2 = "INSERT INTO SessionTable (sessionname)  VALUES(?)";
                //session query 3
                String SaveSessionQuery3 = "INSERT INTO SessionTable (sessionname)  VALUES(?)";
                // session query 4
                String SaveSessionQuery4 = "INSERT INTO SessionTable (sessionname)  VALUES(?)";
                // session query 5
                String SaveSessionQuery5 = "INSERT INTO SessionTable (sessionname)  VALUES(?)";


                ///////////////////////////////////////////////////////////
                //Adding sessionScore name to database
                String SaveSessionScoreQuery = "INSERT INTO SessionScore (sessionscore)  VALUES(?)";

                //session query1
                String SaveSessionScoreQuery1 = "INSERT INTO SessionScore (sessionscore)  VALUES(?)";
                //Session query 2
                String SaveSessionScoreQuery2 = "INSERT INTO SessionScore (sessionscore)  VALUES(?)";
                //session query 3
                String SaveSessionScoreQuery3 = "INSERT INTO SessionScore (sessionscore)  VALUES(?)";
                // session query 4
                String SaveSessionScoreQuery4 = "INSERT INTO SessionScore (sessionscore)  VALUES(?)";
                // session query 5
                String SaveSessionScoreQuery5 = "INSERT INTO SessionScore (sessionscore)  VALUES(?)";


                ///////////////////Saving to database
                PreparedStatement preparedStatement6 = connection.prepareStatement(SaveSessionQuery);
                preparedStatement6.setString(1, sessionRequestEntity.getJss1());
                int o = preparedStatement6.executeUpdate();
                System.out.println("[CreatingSessionThread]: Query 7 executed");
                System.out.println("[CreatingSessionThread]: " + o);


                PreparedStatement preparedStatement7 = connection.prepareStatement(SaveSessionQuery1);
                preparedStatement7.setString(1, sessionRequestEntity.getJss2());
                int p = preparedStatement7.executeUpdate();
                System.out.println("[CreatingSessionThread]: Query 8 executed");
                System.out.println("[CreatingSessionThread]: " + p);

                PreparedStatement preparedStatement8 = connection.prepareStatement(SaveSessionQuery2);
                preparedStatement8.setString(1, sessionRequestEntity.getJss3());
                int q = preparedStatement8.executeUpdate();
                System.out.println("[CreatingSessionThread]: Query 9 executed");
                System.out.println("[CreatingSessionThread]: " + q);

                PreparedStatement preparedStatement9 = connection.prepareStatement(SaveSessionQuery3);
                preparedStatement9.setString(1, sessionRequestEntity.getSs1());
                int r = preparedStatement9.executeUpdate();
                System.out.println("[CreatingSessionThread]: Query 10 executed");
                System.out.println("[CreatingSessionThread]: " + r);

                PreparedStatement preparedStatement10 = connection.prepareStatement(SaveSessionQuery4);
                preparedStatement10.setString(1, sessionRequestEntity.getSs3());
                int s = preparedStatement10.executeUpdate();
                System.out.println("[CreatingSessionThread]: Query 11 executed");
                System.out.println("[CreatingSessionThread]: " + s);

                PreparedStatement preparedStatement11 = connection.prepareStatement(SaveSessionQuery5);
                preparedStatement11.setString(1, sessionRequestEntity.getSs3());
                int t = preparedStatement11.executeUpdate();
                System.out.println("[CreatingSessionThread]: Query 12 executed");
                System.out.println("[CreatingSessionThread]: " + t);

                PreparedStatement preparedStatement12 = connection.prepareStatement(Score1Query);
                int u = preparedStatement12.executeUpdate();
                System.out.println("[CreatingSessionThread]: Query Score1 executed");
                System.out.println("[CreatingSessionThread]: " + u);

                PreparedStatement preparedStatement13 = connection.prepareStatement(Score2Query);
                int v = preparedStatement13.executeUpdate();
                System.out.println("[CreatingSessionThread]: Query Score2 executed");
                System.out.println("[CreatingSessionThread]: " + v);

                PreparedStatement preparedStatement14 = connection.prepareStatement(Score3Query);
                int w = preparedStatement14.executeUpdate();
                System.out.println("[CreatingSessionThread]: Query Score3 executed");
                System.out.println("[CreatingSessionThread]: " + w);

                PreparedStatement preparedStatement15 = connection.prepareStatement(Score4Query);
                int x = preparedStatement15.executeUpdate();
                System.out.println("[CreatingSessionThread]: Query Score4 executed");
                System.out.println("[CreatingSessionThread]: " + x);

                PreparedStatement preparedStatement16 = connection.prepareStatement(Score5Query);
                int y = preparedStatement16.executeUpdate();
                System.out.println("[CreatingSessionThread]: Query Score5 executed");
                System.out.println("[CreatingSessionThread]: " + y);

                PreparedStatement preparedStatement17 = connection.prepareStatement(Score6Query);
                int z = preparedStatement17.executeUpdate();
                System.out.println("[CreatingSessionThread]: Query Score6 executed");
                System.out.println("[CreatingSessionThread]: " + z);

                //////////Saving Session Score To database
                System.out.println("Saving Session Score To Database");

                PreparedStatement preparedStatement18 = connection.prepareStatement(SaveSessionScoreQuery);
                preparedStatement18.setString(1, sessionRequestEntity.getJss1() + "Score");
                int a = preparedStatement18.executeUpdate();
                System.out.println("[CreatingSessionThread]: SessionScoreQuery 1 executed");
                System.out.println("[CreatingSessionThread]: " + a);

                PreparedStatement preparedStatement19 = connection.prepareStatement(SaveSessionScoreQuery1);
                preparedStatement19.setString(1, sessionRequestEntity.getJss2() + "Score");
                int b = preparedStatement19.executeUpdate();
                System.out.println("[CreatingSessionThread]: SessionScoreQuery 2 executed");
                System.out.println("[CreatingSessionThread]: " + b);


                PreparedStatement preparedStatement20 = connection.prepareStatement(SaveSessionScoreQuery2);
                preparedStatement20.setString(1, sessionRequestEntity.getJss3() + "Score");
                int c = preparedStatement20.executeUpdate();
                System.out.println("[CreatingSessionThread]: SessionScoreQuery 3 executed");
                System.out.println("[CreatingSessionThread]: " + c);

                PreparedStatement preparedStatement21 = connection.prepareStatement(SaveSessionScoreQuery3);
                preparedStatement21.setString(1, sessionRequestEntity.getSs1() + "Score");
                int d = preparedStatement21.executeUpdate();
                System.out.println("[CreatingSessionThread]: SessionScoreQuery 4 executed");
                System.out.println("[CreatingSessionThread]: " + d);

                PreparedStatement preparedStatement22 = connection.prepareStatement(SaveSessionScoreQuery4);
                preparedStatement22.setString(1, sessionRequestEntity.getSs2() + "Score");
                int e = preparedStatement22.executeUpdate();
                System.out.println("[CreatingSessionThread]: SessionScoreQuery 5 executed");
                System.out.println("[CreatingSessionThread]: " + e);

                PreparedStatement preparedStatement23 = connection.prepareStatement(SaveSessionScoreQuery5);
                preparedStatement23.setString(1, sessionRequestEntity.getSs3() + "Score");
                int f = preparedStatement23.executeUpdate();
                System.out.println("[CreatingSessionThread]: SessionScoreQuery 6 executed");
                System.out.println("[CreatingSessionThread]: " + f);
                if (i==0 && j==0 && k==0 && l==0 && m==0 && n==0 && o==1 && p==1 && q==1 && r==1 && s==1 && t==1 && u==0 && v==0 && w==0 && x==0 && y==0 && z==0 && a==1 && b==1 && c==1 && d==1 && e==1 && f==1){
                    return "SUCCESS";
                }else {
                    return null;
                }

            } catch (SQLException e) {
                e.printStackTrace();
                try {
                    connection.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    try {
                        connection.close();
                    } catch (SQLException exc) {
                        exc.printStackTrace();
                        return null;
                    }
                }
                return null;
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
    }
}