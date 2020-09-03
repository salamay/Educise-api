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
        System.out.println(sessionRequestEntity.getPr1());
        System.out.println(sessionRequestEntity.getPr2());
        System.out.println(sessionRequestEntity.getPr3());
        System.out.println(sessionRequestEntity.getPr4());
        System.out.println(sessionRequestEntity.getPr5());
        System.out.println(sessionRequestEntity.getNur1());
        System.out.println(sessionRequestEntity.getNur2());

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

        //Nursery one
        String Nursery1Query = "Create table if not exists " + sessionRequestEntity.getNur1() + "(" +
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

        //Nursery two
        String Nursery2Query = "Create table if not exists " + sessionRequestEntity.getNur2() + "(" +
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
        //Primary 1
        String pr1Query = "Create table if not exists " + sessionRequestEntity.getPr1() + "(" +
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
        //Primary 2
        String pr2Query = "Create table if not exists " + sessionRequestEntity.getPr2() + "(" +
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
        //Primary 3
        String pr3Query = "Create table if not exists " + sessionRequestEntity.getPr3() + "(" +
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
        //Primary 4
        String pr4Query = "Create table if not exists " + sessionRequestEntity.getPr4() + "(" +
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
        //Primary 5
        String pr5Query = "Create table if not exists " + sessionRequestEntity.getPr5() + "(" +
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
                "term " + "TEXT," +
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
                "term " + "TEXT," +
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
                "term " + "TEXT," +
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
                "term " + "TEXT," +
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
                "term " + "TEXT," +
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
                "term " + "TEXT," +
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

        String pry1ScoreQuery = "Create table if not exists " + sessionRequestEntity.getPr1() + "Score" + "(" +
                "id " + "int auto_increment," +
                "StudentName " + "TEXT," +
                "term " + "TEXT," +
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

        String pry2ScoreQuery = "Create table if not exists " + sessionRequestEntity.getPr2() + "Score" + "(" +
                "id " + "int auto_increment," +
                "StudentName " + "TEXT," +
                "term " + "TEXT," +
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

        String pry3ScoreQuery = "Create table if not exists " + sessionRequestEntity.getPr3() + "Score" + "(" +
                "id " + "int auto_increment," +
                "StudentName " + "TEXT," +
                "term " + "TEXT," +
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

        String pry4ScoreQuery = "Create table if not exists " + sessionRequestEntity.getPr4() + "Score" + "(" +
                "id " + "int auto_increment," +
                "StudentName " + "TEXT," +
                "term " + "TEXT," +
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

        String pry5ScoreQuery = "Create table if not exists " + sessionRequestEntity.getPr5() + "Score" + "(" +
                "id " + "int auto_increment," +
                "StudentName " + "TEXT," +
                "term " + "TEXT," +
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

        String nur1ScoreQuery = "Create table if not exists " + sessionRequestEntity.getNur1() + "Score" + "(" +
                "id " + "int auto_increment," +
                "StudentName " + "TEXT," +
                "term " + "TEXT," +
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

        String nur2ScoreQuery = "Create table if not exists " + sessionRequestEntity.getNur2() + "Score" + "(" +
                "id " + "int auto_increment," +
                "StudentName " + "TEXT," +
                "term " + "TEXT," +
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
        Connection connection = JDBCConnection.connector();
        if (connection != null) {
            try {

                //jss1 Query
                PreparedStatement preparedStatement = connection.prepareStatement(Query);

                int i = preparedStatement.executeUpdate();
                System.out.println("[CreatingSessionThread]: Jss1 information table query executed");
                System.out.println("[CreatingSessionThread]: " + i);


                //Jss2 query
                PreparedStatement preparedStatement1 = connection.prepareStatement(jss2Query);
                int j = preparedStatement1.executeUpdate();
                System.out.println("[CreatingSessionThread]: Jss2 information table query executed");
                System.out.println("[CreatingSessionThread]: " + j);

                //Jss3 query
                PreparedStatement preparedStatement2 = connection.prepareStatement(jss3Query);
                int k = preparedStatement2.executeUpdate();
                System.out.println("[CreatingSessionThread]: Jss3 information table query executed");
                System.out.println("[CreatingSessionThread]: " + k);

                //ss1 query
                PreparedStatement preparedStatement3 = connection.prepareStatement(ss1Query);
                int l = preparedStatement3.executeUpdate();
                System.out.println("[CreatingSessionThread]: ss1 information table query executed");
                System.out.println("[CreatingSessionThread]: " + l);

                //ss2query
                PreparedStatement preparedStatement4 = connection.prepareStatement(ss2Query);
                int m = preparedStatement4.executeUpdate();
                System.out.println("[CreatingSessionThread]: ss2 information table query executed");
                System.out.println("[CreatingSessionThread]: " + m);

                //ss3 query
                PreparedStatement preparedStatement5 = connection.prepareStatement(ss3Query);
                int n = preparedStatement5.executeUpdate();

                System.out.println("[CreatingSessionThread]: ss3 information table query executed");
                System.out.println("[CreatingSessionThread]: " + n);

                //pr1 query
                PreparedStatement primary1Preparedstatement = connection.prepareStatement(pr1Query);
                int pr1 = primary1Preparedstatement.executeUpdate();

                System.out.println("[CreatingSessionThread]: Primary one table Query executed");
                System.out.println("[CreatingSessionThread]: " + pr1);

                //pr2 query
                PreparedStatement primary2Preparedstatement = connection.prepareStatement(pr2Query);
                int pr2 = primary2Preparedstatement.executeUpdate();

                System.out.println("[CreatingSessionThread]: Primary two table Query executed");
                System.out.println("[CreatingSessionThread]: " + pr2);

                //pr3 query
                PreparedStatement primary3Preparedstatement = connection.prepareStatement(pr3Query);
                int pr3 = primary3Preparedstatement.executeUpdate();

                System.out.println("[CreatingSessionThread]: Primary three table Query executed");
                System.out.println("[CreatingSessionThread]: " + pr3);

                //pr4 query
                PreparedStatement primary4Preparedstatement = connection.prepareStatement(pr4Query);
                int pr4 = primary4Preparedstatement.executeUpdate();

                System.out.println("[CreatingSessionThread]: Primary four table Query executed");
                System.out.println("[CreatingSessionThread]: " + pr4);

                //pr5 query
                PreparedStatement primary5Preparedstatement = connection.prepareStatement(pr5Query);
                int pr5 = primary5Preparedstatement.executeUpdate();

                System.out.println("[CreatingSessionThread]: Primary five table Query executed");
                System.out.println("[CreatingSessionThread]: " + pr5);

                //nur1 query
                PreparedStatement nur1Preparedstatement = connection.prepareStatement(Nursery1Query);
                int nur1 = nur1Preparedstatement.executeUpdate();

                System.out.println("[CreatingSessionThread]: nursery one table Query executed");
                System.out.println("[CreatingSessionThread]: " + nur1);

                //pr5 query
                PreparedStatement nur2Preparedstatement = connection.prepareStatement(Nursery2Query);
                int nur2 = nur2Preparedstatement.executeUpdate();

                System.out.println("[CreatingSessionThread]: Nursery two table Query executed");
                System.out.println("[CreatingSessionThread]: " + nur2);
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
                String SaveSessionQuery6 = "INSERT INTO SessionTable (sessionname)  VALUES(?)";
                String SaveSessionQuery7 = "INSERT INTO SessionTable (sessionname)  VALUES(?)";
                String SaveSessionQuery8 = "INSERT INTO SessionTable (sessionname)  VALUES(?)";
                String SaveSessionQuery9 = "INSERT INTO SessionTable (sessionname)  VALUES(?)";
                String SaveSessionQuery10 = "INSERT INTO SessionTable (sessionname)  VALUES(?)";
                String SaveSessionQuery11 = "INSERT INTO SessionTable (sessionname)  VALUES(?)";
                String SaveSessionQuery12 = "INSERT INTO SessionTable (sessionname)  VALUES(?)";
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
                // session query 6
                String SaveSessionScoreQuery6 = "INSERT INTO SessionScore (sessionscore)  VALUES(?)";
                // session query 7
                String SaveSessionScoreQuery7 = "INSERT INTO SessionScore (sessionscore)  VALUES(?)";
                // session query 8
                String SaveSessionScoreQuery8 = "INSERT INTO SessionScore (sessionscore)  VALUES(?)";
                // session query 9
                String SaveSessionScoreQuery9 = "INSERT INTO SessionScore (sessionscore)  VALUES(?)";
                // session query 10
                String SaveSessionScoreQuery10 = "INSERT INTO SessionScore (sessionscore)  VALUES(?)";
                // session query 11
                String SaveSessionScoreQuery11 = "INSERT INTO SessionScore (sessionscore)  VALUES(?)";
                // session query 12
                String SaveSessionScoreQuery12 = "INSERT INTO SessionScore (sessionscore)  VALUES(?)";

                ///////////////////Saving to database
                PreparedStatement preparedStatement6 = connection.prepareStatement(SaveSessionQuery);
                preparedStatement6.setString(1, sessionRequestEntity.getJss1());
                int o = preparedStatement6.executeUpdate();
                System.out.println("[CreatingSessionThread]: Jss1 information score query executed");
                System.out.println("[CreatingSessionThread]: " + o);


                PreparedStatement preparedStatement7 = connection.prepareStatement(SaveSessionQuery1);
                preparedStatement7.setString(1, sessionRequestEntity.getJss2());
                int p = preparedStatement7.executeUpdate();
                System.out.println("[CreatingSessionThread]: Jss2 information score query executed");
                System.out.println("[CreatingSessionThread]: " + p);

                PreparedStatement preparedStatement8 = connection.prepareStatement(SaveSessionQuery2);
                preparedStatement8.setString(1, sessionRequestEntity.getJss3());
                int q = preparedStatement8.executeUpdate();
                System.out.println("[CreatingSessionThread]: Jss3 information score query executed");
                System.out.println("[CreatingSessionThread]: " + q);

                PreparedStatement preparedStatement9 = connection.prepareStatement(SaveSessionQuery3);
                preparedStatement9.setString(1, sessionRequestEntity.getSs1());
                int r = preparedStatement9.executeUpdate();
                System.out.println("[CreatingSessionThread]: ss1 information score query executed");
                System.out.println("[CreatingSessionThread]: " + r);

                PreparedStatement preparedStatement10 = connection.prepareStatement(SaveSessionQuery4);
                preparedStatement10.setString(1, sessionRequestEntity.getSs2());
                int s = preparedStatement10.executeUpdate();
                System.out.println("[CreatingSessionThread]: ss2 information score query executed");
                System.out.println("[CreatingSessionThread]: " + s);

                PreparedStatement preparedStatement11 = connection.prepareStatement(SaveSessionQuery5);
                preparedStatement11.setString(1, sessionRequestEntity.getSs3());
                int t = preparedStatement11.executeUpdate();
                System.out.println("[CreatingSessionThread]: ss3 information score query executed");
                System.out.println("[CreatingSessionThread]: " + t);

                PreparedStatement pry1SaveSessionQuery = connection.prepareStatement(SaveSessionQuery6);
                pry1SaveSessionQuery.setString(1, sessionRequestEntity.getPr1());
                int pr11 = pry1SaveSessionQuery.executeUpdate();
                System.out.println("[CreatingSessionThread]: Save primary one table Query executed");
                System.out.println("[CreatingSessionThread]: " + pr11);

                PreparedStatement pry2SaveSessionQuery = connection.prepareStatement(SaveSessionQuery7);
                pry2SaveSessionQuery.setString(1, sessionRequestEntity.getPr2());
                int pr22 = pry2SaveSessionQuery.executeUpdate();
                System.out.println("[CreatingSessionThread]: Save primary two table Query executed");
                System.out.println("[CreatingSessionThread]: " + pr22);

                PreparedStatement pry3SaveSessionQuery = connection.prepareStatement(SaveSessionQuery8);
                pry3SaveSessionQuery.setString(1, sessionRequestEntity.getPr3());
                int pr33 = pry3SaveSessionQuery.executeUpdate();
                System.out.println("[CreatingSessionThread]: Save primary three table Query executed");
                System.out.println("[CreatingSessionThread]: " + pr33);

                PreparedStatement pry4SaveSessionQuery = connection.prepareStatement(SaveSessionQuery9);
                pry4SaveSessionQuery.setString(1, sessionRequestEntity.getPr4());
                int pr44 = pry4SaveSessionQuery.executeUpdate();
                System.out.println("[CreatingSessionThread]: Save primary four table Query executed");
                System.out.println("[CreatingSessionThread]: " + pr44);

                PreparedStatement pry5SaveSessionQuery = connection.prepareStatement(SaveSessionQuery10);
                pry5SaveSessionQuery.setString(1, sessionRequestEntity.getPr5());
                int pr55 = pry5SaveSessionQuery.executeUpdate();
                System.out.println("[CreatingSessionThread]: Save primary five table Query executed");
                System.out.println("[CreatingSessionThread]: " + pr55);

                PreparedStatement nur1SaveSessionQuery = connection.prepareStatement(SaveSessionQuery11);
                nur1SaveSessionQuery.setString(1, sessionRequestEntity.getNur1());
                int nur11 = nur1SaveSessionQuery.executeUpdate();
                System.out.println("[CreatingSessionThread]: Save nursery one table Query executed");
                System.out.println("[CreatingSessionThread]: " + nur11);

                PreparedStatement nur2SaveSessionQuery = connection.prepareStatement(SaveSessionQuery12);
                nur2SaveSessionQuery.setString(1, sessionRequestEntity.getNur2());
                int nur22 = nur2SaveSessionQuery.executeUpdate();
                System.out.println("[CreatingSessionThread]: Save nursery two table Query executed");
                System.out.println("[CreatingSessionThread]: " + nur22);

                PreparedStatement preparedStatement12 = connection.prepareStatement(Score1Query);
                int u = preparedStatement12.executeUpdate();
                System.out.println("[CreatingSessionThread]: Jss1 Score Query executed");
                System.out.println("[CreatingSessionThread]: " + u);

                PreparedStatement preparedStatement13 = connection.prepareStatement(Score2Query);
                int v = preparedStatement13.executeUpdate();
                System.out.println("[CreatingSessionThread]:  Jss2 Score Query executed");
                System.out.println("[CreatingSessionThread]: " + v);

                PreparedStatement preparedStatement14 = connection.prepareStatement(Score3Query);
                int w = preparedStatement14.executeUpdate();
                System.out.println("[CreatingSessionThread]:  Jss3 Score Query executed");
                System.out.println("[CreatingSessionThread]: " + w);

                PreparedStatement preparedStatement15 = connection.prepareStatement(Score4Query);
                int x = preparedStatement15.executeUpdate();
                System.out.println("[CreatingSessionThread]:  ss1 Score Query executed");
                System.out.println("[CreatingSessionThread]: " + x);

                PreparedStatement preparedStatement16 = connection.prepareStatement(Score5Query);
                int y = preparedStatement16.executeUpdate();
                System.out.println("[CreatingSessionThread]:  ss2 Score Query executed");
                System.out.println("[CreatingSessionThread]: " + y);

                PreparedStatement preparedStatement17 = connection.prepareStatement(Score6Query);
                int z = preparedStatement17.executeUpdate();
                System.out.println("[CreatingSessionThread]:  ss3 Score Query executed");
                System.out.println("[CreatingSessionThread]: " + z);

                PreparedStatement pry1ScoreQueryPreparedStatemnt = connection.prepareStatement(pry1ScoreQuery);
                int pr1score = pry1ScoreQueryPreparedStatemnt.executeUpdate();
                System.out.println("[CreatingSessionThread]:  primary one Score Query executed");
                System.out.println("[CreatingSessionThread]: " + pr1score);

                PreparedStatement pry2ScoreQueryPreparedStatemnt = connection.prepareStatement(pry2ScoreQuery);
                int pr2score = pry2ScoreQueryPreparedStatemnt.executeUpdate();
                System.out.println("[CreatingSessionThread]:  primary two Score Query executed");
                System.out.println("[CreatingSessionThread]: " + pr2score);

                PreparedStatement pry3ScoreQueryPreparedStatemnt = connection.prepareStatement(pry3ScoreQuery);
                int pr3score = pry3ScoreQueryPreparedStatemnt.executeUpdate();
                System.out.println("[CreatingSessionThread]:  primary three Score Query executed");
                System.out.println("[CreatingSessionThread]: " + pr3score);

                PreparedStatement pry4ScoreQueryPreparedStatemnt = connection.prepareStatement(pry4ScoreQuery);
                int pr4score = pry4ScoreQueryPreparedStatemnt.executeUpdate();
                System.out.println("[CreatingSessionThread]:  primary four Score Query executed");
                System.out.println("[CreatingSessionThread]: " + pr4score);

                PreparedStatement pry5ScoreQueryPreparedStatemnt = connection.prepareStatement(pry5ScoreQuery);
                int pr5score = pry5ScoreQueryPreparedStatemnt.executeUpdate();
                System.out.println("[CreatingSessionThread]:  primary five Score Query executed");
                System.out.println("[CreatingSessionThread]: " + pr5score);

                PreparedStatement nur1ScoreQueryPreparedStatemnt = connection.prepareStatement(nur1ScoreQuery);
                int nur1score = nur1ScoreQueryPreparedStatemnt.executeUpdate();
                System.out.println("[CreatingSessionThread]:  nursery one Score Query executed");
                System.out.println("[CreatingSessionThread]: " + nur1score);

                PreparedStatement nur2ScoreQueryPreparedStatemnt = connection.prepareStatement(nur2ScoreQuery);
                int nur2score = nur2ScoreQueryPreparedStatemnt.executeUpdate();
                System.out.println("[CreatingSessionThread]:  nursery two Score Query executed");
                System.out.println("[CreatingSessionThread]: " + nur2score);

                //////////Saving Session Score To database
                System.out.println("Saving Session Score To Database");

                PreparedStatement preparedStatement18 = connection.prepareStatement(SaveSessionScoreQuery);
                preparedStatement18.setString(1, sessionRequestEntity.getJss1() + "Score");
                int a = preparedStatement18.executeUpdate();
                System.out.println("[CreatingSessionThread]: jss1 score session query executed");
                System.out.println("[CreatingSessionThread]: " + a);

                PreparedStatement preparedStatement19 = connection.prepareStatement(SaveSessionScoreQuery1);
                preparedStatement19.setString(1, sessionRequestEntity.getJss2() + "Score");
                int b = preparedStatement19.executeUpdate();
                System.out.println("[CreatingSessionThread]: jss2 score session query executed");
                System.out.println("[CreatingSessionThread]: " + b);


                PreparedStatement preparedStatement20 = connection.prepareStatement(SaveSessionScoreQuery2);
                preparedStatement20.setString(1, sessionRequestEntity.getJss3() + "Score");
                int c = preparedStatement20.executeUpdate();
                System.out.println("[CreatingSessionThread]: jss3 score session query executed");
                System.out.println("[CreatingSessionThread]: " + c);

                PreparedStatement preparedStatement21 = connection.prepareStatement(SaveSessionScoreQuery3);
                preparedStatement21.setString(1, sessionRequestEntity.getSs1() + "Score");
                int d = preparedStatement21.executeUpdate();
                System.out.println("[CreatingSessionThread]: ss1 score session query executed");
                System.out.println("[CreatingSessionThread]: " + d);

                PreparedStatement preparedStatement22 = connection.prepareStatement(SaveSessionScoreQuery4);
                preparedStatement22.setString(1, sessionRequestEntity.getSs2() + "Score");
                int e = preparedStatement22.executeUpdate();
                System.out.println("[CreatingSessionThread]: ss2 score session query executed");
                System.out.println("[CreatingSessionThread]: " + e);

                PreparedStatement preparedStatement23 = connection.prepareStatement(SaveSessionScoreQuery5);
                preparedStatement23.setString(1, sessionRequestEntity.getSs3() + "Score");
                int f = preparedStatement23.executeUpdate();
                System.out.println("[CreatingSessionThread]: ss3 score session query executed");
                System.out.println("[CreatingSessionThread]: " + f);

                PreparedStatement pr1SaveScoreSession = connection.prepareStatement(SaveSessionScoreQuery6);
                pr1SaveScoreSession.setString(1, sessionRequestEntity.getPr1() + "Score");
                int pr1savescore = pr1SaveScoreSession.executeUpdate();
                System.out.println("[CreatingSessionThread]: primary one save score session query executed");
                System.out.println("[CreatingSessionThread]: " + pr1savescore);

                PreparedStatement pr2SaveScoreSession = connection.prepareStatement(SaveSessionScoreQuery7);
                pr2SaveScoreSession.setString(1, sessionRequestEntity.getPr2() + "Score");
                int pr2savescore = pr2SaveScoreSession.executeUpdate();
                System.out.println("[CreatingSessionThread]: primary two save score session query executed");
                System.out.println("[CreatingSessionThread]: " + pr2savescore);

                PreparedStatement pr3SaveScoreSession = connection.prepareStatement(SaveSessionScoreQuery8);
                pr3SaveScoreSession.setString(1, sessionRequestEntity.getPr3() + "Score");
                int pr3savescore = pr3SaveScoreSession.executeUpdate();
                System.out.println("[CreatingSessionThread]: primary three save score session query executed");
                System.out.println("[CreatingSessionThread]: " + pr3savescore);

                PreparedStatement pr4SaveScoreSession = connection.prepareStatement(SaveSessionScoreQuery9);
                pr4SaveScoreSession.setString(1, sessionRequestEntity.getPr4() + "Score");
                int pr4savescore = pr4SaveScoreSession.executeUpdate();
                System.out.println("[CreatingSessionThread]: primary four save score session query executed");
                System.out.println("[CreatingSessionThread]: " + pr4savescore);

                PreparedStatement pr5SaveScoreSession = connection.prepareStatement(SaveSessionScoreQuery10);
                pr5SaveScoreSession.setString(1, sessionRequestEntity.getPr5() + "Score");
                int pr5savescore = pr5SaveScoreSession.executeUpdate();
                System.out.println("[CreatingSessionThread]: primary five save score session query executed");
                System.out.println("[CreatingSessionThread]: " + pr5savescore);

                PreparedStatement nur1SaveScoreSession = connection.prepareStatement(SaveSessionScoreQuery11);
                nur1SaveScoreSession.setString(1, sessionRequestEntity.getNur1() + "Score");
                int nur1savescore = nur1SaveScoreSession.executeUpdate();
                System.out.println("[CreatingSessionThread]: nursery one save score session query executed");
                System.out.println("[CreatingSessionThread]: " + nur1savescore);

                PreparedStatement nur2SaveScoreSession = connection.prepareStatement(SaveSessionScoreQuery12);
                nur2SaveScoreSession.setString(1, sessionRequestEntity.getNur2() + "Score");
                int nur2savescore = nur2SaveScoreSession.executeUpdate();
                System.out.println("[CreatingSessionThread]: nursery two save score session query executed");
                System.out.println("[CreatingSessionThread]: " + nur2savescore);
                if (i==0 && j==0 && k==0 && l==0 && m==0 && n==0 && pr1==0 && pr2==0 && pr3==0 &&pr4==0 && pr5==0 &&nur1==0 &&nur2==0 && o==1 && p==1 && q==1 && r==1 && s==1 && t==1 && pr11==1 && pr22 ==1 && pr33==1 && pr44==1 && pr55 ==1 && nur11==1 &&nur22==1 && u==0 && v==0 && w==0 && x==0 && y==0 && z==0 &&pr1score==0 && pr2score==0 && pr3score==0 &&pr4score==0 &&pr5score==0 &&nur1score==0 &&nur2score==0 && a==1 && b==1 && c==1 && d==1 && e==1 && f==1&&pr1savescore==1 &&pr2savescore==1 &&pr3savescore==1 &&pr4savescore==1 &&pr5savescore==1 &&nur1savescore==1 &&nur2savescore==1){
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