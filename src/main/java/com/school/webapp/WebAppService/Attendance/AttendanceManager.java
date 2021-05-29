package com.school.webapp.WebAppService.Attendance;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.school.webapp.JDBC.JDBCConnection;
import com.school.webapp.Repository.Attendance;
import com.school.webapp.Repository.AttendanceRepository;
import com.school.webapp.WebAppService.MyException;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class AttendanceManager {
    @Autowired
    private AttendanceRepository attendanceRepository;
    private Map<String, String> studentinfo = new HashMap<>();
    public boolean signAttendanceForAStudent(String studentid,String schoolid,String cardsession,String cardterm) throws MyException {
        //Card term is not used here
        try {
            //Getting term and session from the Config file
            FileInputStream fileInputStream=new FileInputStream(getConfigurationFile());
            Properties properties=new Properties();
            properties.load(fileInputStream);
            fileInputStream.close();
            String session=properties.getProperty("session");
            String term=properties.getProperty("term");
            String weeknumber=properties.getProperty("week");
            if (session.equals(cardsession)&&term.equals(cardterm)){
                System.out.printf("[AttendanceManager]: signing attendance for user [%s] \n", studentid);
                //Getting the day time and weekday
                Calendar c = Calendar.getInstance();
                String todayDate = Calendar.getInstance().getTime().toString();
                //Parsing Calendar date
                List<String> data = Arrays.asList(todayDate.split(" "));
                String weekday = data.get(0);
                String date = data.get(1) + " " + data.get(2) + " " + data.get(5);
                String time = data.get(3);
                int timeOfDay = c.get(Calendar.HOUR_OF_DAY);
                if (timeOfDay >= 0 && timeOfDay < 12) {
                    String dayTime = "Morning";
                    return sign(weekday,dayTime,time,studentid,term,date,session,schoolid,weeknumber);
                } else if (timeOfDay >= 12 && timeOfDay < 16) {
                    String dayTime = "Afternoon";
                    return sign(weekday,dayTime,time,studentid,term,date,session,schoolid,weeknumber);
                } else if (timeOfDay >= 16 && timeOfDay < 21) {
                    String dayTime = "Evening";
                    return sign(weekday,dayTime,time,studentid,term,date,session,schoolid,weeknumber);
                } else if (timeOfDay >= 21 && timeOfDay < 24) {
                    throw new MyException("Unable to sign attendance in the night");
                }else {
                    return false;
                }
            }else{
                throw new MyException("Card expired");
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new MyException("Server configuration file not found, contact the software administeration");
        } catch (IOException e) {
            e.printStackTrace();
            throw new MyException("Server configuration file not found, contact the software administeration");
        }

    }

    public Map<String, String> getStudentInformation(String studentid) throws MyException {
        //Getting student information from database
        Connection connection = JDBCConnection.connector();
        if (connection != null) {
            String QUERY = "Select studentname,Gender,studentclass,parentphonenumber from studentinformation where id =?";
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(QUERY);
                preparedStatement.setString(1,studentid);
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    studentinfo.put("studentname", resultSet.getString("studentname"));
                    studentinfo.put("class", resultSet.getString("studentclass"));
                    studentinfo.put("gender", resultSet.getString("gender"));
                    studentinfo.put("parentphonenumber",resultSet.getString("parentphonenumber"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
                throw new MyException("An error occur when signing attendance");
            } finally {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    throw new MyException("An error occur when signing attendance");
                }
            }
            return studentinfo;
        } else {
            throw new MyException("Unable to register student at the moment,please wait while we fix the issue");
        }
    }

    private boolean sign(String weekday, String daytime, String time,String studentid,String term,String date,String session,String schoolid,String weeknumber) throws MyException {
        Map<String, String> map = getStudentInformation(studentid);
        System.out.println(map.get("studentname"));
        ///Check if student has marked attendance
        boolean hasStudentbeenMarked=checkIfStudentHasMarkAttendance(studentid,session,term,map.get("class"),weeknumber);
        if (hasStudentbeenMarked){
            throw new MyException("This student has already been marked for this "+daytime);
        }else {
            int result=attendanceRepository.SaveAttendance(studentid,map.get("studentname"),session,term,map.get("class"),map.get("gender"),weekday,daytime,time,date,schoolid,weeknumber);
            sendMessageToParent(studentinfo.get("parentphonenumber"),studentinfo.get("studentname"));
            System.out.println(result);
            if (result==1){
                return true;
            }else {
                return false;
            }
        }

    }
    private static File getConfigurationFile(){
        Path path= Paths.get(System.getProperty("user.dir")+"/webapp/configfile.txt");
        File file =new File(String.valueOf(path.toAbsolutePath()));
        return file;
    }
    private boolean checkIfStudentHasMarkAttendance(String studentid,String session,String term,String clas,String weeknumber) throws MyException {
        //When the current daytime,weekday,date,term,session,class has been gotten,it check the attendance db if it matches any row
        //if it does,return true (student has marked attendanc,if false=> student has not marked depending on the daytime)
        Calendar c = Calendar.getInstance();
        int timeOfDay = c.get(Calendar.HOUR_OF_DAY);
        String todayDate = Calendar.getInstance().getTime().toString();
        //Parsing Calendar date
        List<String> data = Arrays.asList(todayDate.split(" "));
        String weekday = data.get(0);
        String date = data.get(1) + " " + data.get(2) + " " + data.get(5);
        if (timeOfDay >= 0 && timeOfDay < 12) {
            String dayTime = "Morning";
            return checkAttendance(studentid,dayTime,weekday,date,session,term,clas,weeknumber);
        } else if (timeOfDay >= 12 && timeOfDay < 16) {
            String dayTime = "Afternoon";
            return checkAttendance(studentid,dayTime,weekday,date,session,term,clas,weeknumber);
        } else if (timeOfDay >= 16 && timeOfDay < 21) {
            String dayTime = "Evening";
            return checkAttendance(studentid,dayTime,weekday,date,session,term,clas,weeknumber);
        } else if (timeOfDay >= 21 && timeOfDay < 24) {
            throw new MyException("Unable to sign attendance");
        }else {
            return false;
        }

    }
    private boolean checkAttendance(String studentid,String daytime,String weekdate,String date,String session,String term,String clas,String weeknumber) throws MyException {
        ArrayList<Attendance> attendance=attendanceRepository.checkAttendance(studentid,daytime,weekdate,date,session,term,clas,weeknumber);
        if (!attendance.isEmpty()){
            return true;
        }else {
            return false;
        }
    }


    public void sendMessageToParent(String parentPhoneNumber,String childname) throws MyException {
        OkHttpClient client=new OkHttpClient.Builder()
                .connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(1, TimeUnit.MINUTES)
                .build();
        System.out.println("[AttendanceManager]:sending message to parent--> Making Request");
        MessageModel messageModel=new MessageModel();
        messageModel.setApi_token("3YzFpdKqtTIjM6pHVOA9wv1Sf30icEXEWubwUZ7q6ITflrsB9LEmdEuvAss6");
        messageModel.setFrom("Educise");
        messageModel.setTo(parentPhoneNumber);
        messageModel.setBody("Your child "+childname+" has been marked present in school at "+new Date().toString());
        messageModel.setGateway("0");
        messageModel.setAppend_sender("0");
        GsonBuilder builder=new GsonBuilder();
        builder.setPrettyPrinting();
        builder.serializeNulls();
        Gson gson=builder.create();
        String rawjson=gson.toJson(messageModel);

        RequestBody requestBody=RequestBody.create(MediaType.parse("application/json"),rawjson);
        Request request=new Request.Builder()
                .addHeader("Content-Type","application/json")
                .addHeader("Accept","application/json")
                .post(requestBody)
                .url("https://www.bulksmsnigeria.com/api/v1/sms/create")
                .build();
        try {
            Response response=client.newCall(request).execute();
            if (response.code()==200){
                System.out.println("Message sent");
            }else{
                System.out.println("An error occur while sending message");
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new MyException("Attendance has been marked but unable to send message to parent");
        }
    }
}
