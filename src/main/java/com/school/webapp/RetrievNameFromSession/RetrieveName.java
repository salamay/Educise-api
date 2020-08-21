package com.school.webapp.RetrievNameFromSession;

import com.school.webapp.JDBC.JDBCConnection;

import java.lang.reflect.Array;
import java.sql.Connection;
import java.util.ArrayList;

public class RetrieveName {
    public ArrayList<String> retrieveName(String classname){

        System.out.println("[RetrievingNameFromSession]: Settiong up connection");
        Connection connection= JDBCConnection.connector();
        if (connection!=null){
            System.out.println("[RetrievingNameFromSession]: Connected successfully");
            String Query = "Select StudentName from " + classname + " Where 1";

        }else {
            return null;
        }
    }
}
