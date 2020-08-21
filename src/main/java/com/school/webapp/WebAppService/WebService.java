package com.school.webapp.WebAppService;

import com.school.webapp.Information.StudentandParent.RetrieveStudentInformation;
import com.school.webapp.Information.StudentandParent.StudentInformationRequestEntity;
import com.school.webapp.Information.StudentandParent.StudentInformationResponseEntity;
import com.school.webapp.RegisterTeacher.RegisterTeacher;
import com.school.webapp.RegisterTeacher.RegisterTeacherRequestEntity;
import com.school.webapp.Registration.Register;
import com.school.webapp.Registration.RegistrationModel;
import com.school.webapp.Repository.MyRepository;
import com.school.webapp.RetrieveSession.RetrieveSession;
import com.school.webapp.Session.CreateSession;
import com.school.webapp.Session.SessionRequestEntity;
import com.school.webapp.StudentScore.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.sql.SQLException;
import java.util.ArrayList;

@Service
public class WebService {
    private int queryresponse;
    @Autowired
    private MyRepository myRepository;

    @Autowired
    private Register register;
//Register Student
    public String RegisterStudent(RegistrationModel registrationModel, String session, MultipartFile studentpicture, MultipartFile fatherpicture, MultipartFile motherpicture) throws SQLException {
       String result=register.Register(registrationModel,session,studentpicture,fatherpicture,motherpicture);
       System.out.println("[Service]: "+result);
       return result;

    }
//Create Session
    public String CreateSession(SessionRequestEntity sessionRequestEntity){
        return new CreateSession().ExecuteQuery(sessionRequestEntity);
    }
    //Register Teacher
    public String registerTeacher(RegisterTeacherRequestEntity registerTeacherRequestEntity){
        return  new RegisterTeacher().Registerteacher(registerTeacherRequestEntity);
    }
    //Get Score
    public Scores getScores(getStudentScoreRequestEntity getStudentScoreRequestEntity){
        return  new getStudentScore().saveScore(getStudentScoreRequestEntity);
    }
    //Insert Subject
    public String insertSubject(InsertSubjectRequestEntity insertSubjectRequestEntity){
        return new InsertSubject().InsertSubject(insertSubjectRequestEntity);
    }
    //update student score
    public String UpdateScore(UpdateScoreRequestEntity updateScoreRequestEntity){
        if (updateScoreRequestEntity.getCa()!=null&&updateScoreRequestEntity.getTable()!=null&&updateScoreRequestEntity.getName()!=null&&updateScoreRequestEntity.getSubject()!=null&&updateScoreRequestEntity.getScore()!=null){
            return new UpdateScore().updateScore(updateScoreRequestEntity);
        }
        else {
            return "Failed";
        }
    }
    //retrive Student information,this take in request entity instance and pass it to the Query class
    public StudentInformationResponseEntity retrieveStudentInformation(StudentInformationRequestEntity studentInformationRequestEntity){
        return new RetrieveStudentInformation().retrieveStudentInformation(studentInformationRequestEntity);
    }

///This method receive sessions
    public ArrayList<String> retrieve(){

        ArrayList<String> sessionList=new RetrieveSession().retrieve();
        if (sessionList!=null){
            System.out.println("[Retrieving session]: session retrieved");
            System.out.println("[Retrieving session]: "+sessionList);
            return sessionList;
        }else {
            System.out.println("[Retrieving session]: session failed to retrieve");
            return null;
        }
    }
}
