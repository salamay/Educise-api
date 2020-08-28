package com.school.webapp.WebAppService;

import com.school.webapp.Information.StudentandParent.RetrieveStudentInformation;
import com.school.webapp.Information.StudentandParent.StudentInformationResponseEntity;
import com.school.webapp.RegisterTeacher.RegisterTeacher;
import com.school.webapp.RegisterTeacher.RegisterTeacherRequestEntity;
import com.school.webapp.Registration.Register;
import com.school.webapp.Registration.RegistrationModel;
import com.school.webapp.Repository.MyRepository;
import com.school.webapp.RetrievNameFromSession.RetrieveName;
import com.school.webapp.RetrieveParentInformation.RetrieveParentInformation;
import com.school.webapp.RetrieveParentInformation.RetrieveParentInformationResponseEntity;
import com.school.webapp.RetrieveParentNames.RetrieveParentName;
import com.school.webapp.RetrieveSession.RetrieveScoreSession;
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
    public int queryresponse;
    @Autowired
    private MyRepository myRepository;
    public RetrieveStudentInformation retrieveStudentInformation;
    @Autowired
    private Register register;
//Register Student
    public String RegisterStudent(RegistrationModel registrationModel, String session, MultipartFile studentpicture, MultipartFile fatherpicture, MultipartFile motherpicture) throws SQLException {
       String result=register.Register(registrationModel,session,studentpicture,fatherpicture,motherpicture);
       System.out.println("[Service]: "+result);
       return result;

    }
     ///////////////////////////Create Session/////////////////////////////////////////////
    public String CreateSession(SessionRequestEntity sessionRequestEntity){
        return new CreateSession().ExecuteQuery(sessionRequestEntity);
    }
    ///////////////////////////CREATING SESSION END/////////////////////////////////////////

    ////////////////////////////Register Teacher////////////////////////////////////////////
    public String registerTeacher(RegisterTeacherRequestEntity registerTeacherRequestEntity){
        return  new RegisterTeacher().Registerteacher(registerTeacherRequestEntity);
    }
    ////////////////////////////CREATING SESSION END/////////////////////////////////////////


    //////////////////////////This method get the Scores////////////////////////////////////////////////////////
    public ArrayList<Scores> getScores(getStudentScoreRequestEntity getStudentScoreRequestEntity){
        System.out.println("[WebService]-->Proceeding to Database");
        System.out.println("[WebService]-->"+getStudentScoreRequestEntity.getName());
        System.out.println("[WebService]-->"+getStudentScoreRequestEntity.getTable());
        return  new getStudentScore().getScore(getStudentScoreRequestEntity);
    }
    //update Subject
    public String updateSubject(UpdateSubjectRequestEntity updateSubjectRequestEntity){
        return new UpdateSubject().InsertSubject(updateSubjectRequestEntity);
    }

    ////////////////////////////////////////Insert Subject//////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////
    public String insertSubject(String subject,String session,String studentname){
        System.out.println("[WebService]-->Inserting subject-->Proceeding to Database");
        return new InsertSubject().insertSubject(subject,session,studentname);
    }
    //////////////////////////////////Insert Subject END///////////////////////////////////

    ////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////update student score/////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////
    public String UpdateScore(UpdateScoreRequestEntity updateScoreRequestEntity){
        if (updateScoreRequestEntity.getCa()!=null&&updateScoreRequestEntity.getTable()!=null&&updateScoreRequestEntity.getName()!=null&&updateScoreRequestEntity.getSubject()!=null&&updateScoreRequestEntity.getScore()!=null){
            return new UpdateScore().updateScore(updateScoreRequestEntity);
        }
        else {
            return null;
        }
    }
    ////////////////////////////////////END//////////////////////////////////////////////////////////////////////

////////////////////////////////////////////DELETE SUBEJECT//////////////////////////////////////////////////////
    public boolean deleteSubject(String subject, String session, String studentname) {
        System.out.println("[Webservice]-->Deleting Subject-->proceeding to database");
        return new DeleteSubject().deleteSubject(subject,session,studentname);
    }

    /////////////////////////////////////DELETE SUBJECT END///////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //this method retrive Student information,this take in request entity instance and pass it to the Query class
    ///////////////////////////////////////////////////////////////////////////////////////////////
    public StudentInformationResponseEntity retrieveStudentinfo(String name, String classselected){
        retrieveStudentInformation=new RetrieveStudentInformation();
        StudentInformationResponseEntity studentInformationResponseEntity=retrieveStudentInformation.retrieveStudentInformation(name,classselected);
        return studentInformationResponseEntity;
    }
    /////////////////END///////////////////////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////////////////////////////////////////
    ///This method receive information sessions
    ////////////////////////////////////////////////////////////////////////////////////////////////
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
    ////////////////////////END/////////////////////////////////////////////////////////////////////////

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////This method Retrieve score session/////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public ArrayList<String> retrieveScoreSession(){
        ArrayList<String> sessionList=new RetrieveScoreSession().retrieve();
        if (sessionList!=null){
            System.out.println("[RetrievingScore session]: session retrieved");
            System.out.println("[RetrievingScore session]: "+sessionList);
            return sessionList;
        }else {
            System.out.println("[RetrievingScore session]: session failed to retrieve");
            return null;
        }
    }
    ////////////////////////////////////////END/////////////////////////////////////////////////////////


    //////////////////////////////////////////////////////////////////////////////////////////////////
    ///THis method Retrieve List of names from class
    ////////////////////////////////////////////////////////////////////////////////////////////////////////
    public ArrayList<String> RetrieveName( String classname){
        ArrayList<String> list=new RetrieveName().retrieveName(classname);
        if (list!=null){
            return list;
        }else {
            return null;
        }
    }
    //////////////////////////////////////END/////////////////////////////////////////////////////////////////

////////////////////////This method retrieve Parent name/////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////
    public ArrayList<String> retrieveParentname( String session){
        System.out.println("[WebService]-->Retrieving parent names: "+session);
        ArrayList<String> list=new RetrieveParentName().getParentNames(session);
        if (list!=null){
            return list;
        }else {
            return null;
        }
    }
    ///////////////////////////////////END//////////////////////////////////////////////////////////////////////
    public RetrieveParentInformationResponseEntity getParentInfo(String session,String parentname){
        RetrieveParentInformationResponseEntity retrieveParentInformationResponseEntity=new RetrieveParentInformationResponseEntity();
        System.out.println("[WebService]-->Retrieving parent Information");
        retrieveParentInformationResponseEntity=new RetrieveParentInformation().retrieveParentInfo(session,parentname);
        if (retrieveParentInformationResponseEntity!=null){
            return retrieveParentInformationResponseEntity;
        }else {
            System.out.println("[WebService]-->Retrieving parent Information-->Response is null,unable to fetch parent information");
            return  null;
        }
    }

}
