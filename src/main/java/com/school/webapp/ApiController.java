package com.school.webapp;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.school.webapp.Information.StudentandParent.StudentInformationRequestEntity;
import com.school.webapp.Information.StudentandParent.StudentInformationResponseEntity;
import com.school.webapp.RegisterTeacher.RegisterTeacherRequestEntity;
import com.school.webapp.RegisterTeacher.RegisterTeacherResponse;
import com.school.webapp.Registration.RegistrationModel;
import com.school.webapp.RequestModel.AuthenticateRequest;
import com.school.webapp.ResponseModel.JwtResponse;
import com.school.webapp.ResponseModel.JwtUtils;
import com.school.webapp.Session.SessionRequestEntity;
import com.school.webapp.Session.SessionResponseEntity;
import com.school.webapp.StudentScore.*;
import com.school.webapp.WebAppService.WebService;
import com.school.webapp.security.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;


@RestController
public class ApiController {
    //Controller class



    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private MyUserDetailsService myUserDetailsService;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private WebService webService;

    @RequestMapping(value = "/hello")
    public String hello(){
        return "Rejhd";
    }

    //Authenticate
    @RequestMapping(value = "/authenticate",method = RequestMethod.POST)
    public ResponseEntity<?> Authenticate(@RequestBody AuthenticateRequest authenticateRequest) throws Exception {
        System.out.println("DDDDD");
        String jwt=null;
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticateRequest.getUsername(),authenticateRequest.getPassword()));

        }catch (BadCredentialsException e){
            throw new Exception("Incorect password [bad Credentials]");
        }
        UserDetails userDetails= myUserDetailsService.loadUserByUsername(authenticateRequest.getUsername());
        if (userDetails!=null){
            jwt=jwtUtils.generateToken(userDetails);
            return ResponseEntity.ok(new JwtResponse(jwt));
        }else {
            return null;
        }

    }


    //////////////////Register Student method////////////////////////////////////////
    @RequestMapping(value = "register/{session}",method = RequestMethod.POST,consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> RegisterStudent(@PathVariable String session,
                                                  @RequestBody MultipartFile studentpicture,
                                                  @RequestBody MultipartFile fatherpicture,
                                                  @RequestBody MultipartFile motherpicture,
                                                  @RequestBody MultipartFile json
                                ) throws SQLException {

        if (json!=null&& studentpicture!=null && fatherpicture!=null && motherpicture!=null){
            RegistrationModel registrationModel=new RegistrationModel();
            System.out.println("[Registering]: "+"Converting json file to bytes");
            try {
                byte [] entity=json.getBytes();
                System.out.println("[Registering]: "+"Converting bytes to string");
                String jsonstring=new String(entity,"UTF-8");
                System.out.println("[Registering]: "+"Converting String to json");
                GsonBuilder builder=new GsonBuilder();
                builder.setPrettyPrinting();
                builder.serializeNulls();
                Gson gson=builder.create();
                System.out.println("[Registering]: "+"preparing json object file");
                registrationModel=new RegistrationModel();
                registrationModel=gson.fromJson(jsonstring,RegistrationModel.class);
                System.out.println("[Registering]: "+"Finished preparing json object");
            } catch (IOException e) {
                System.out.println("[Registering]: "+"Unable to convert to byte");
                e.printStackTrace();
            }
            String result=webService.RegisterStudent(registrationModel,session, studentpicture,fatherpicture,motherpicture);
            if (result!=null){
                System.out.println("[Registering]: "+"result is not equal to null");
                return ResponseEntity.accepted().body(result);

            }else {
                System.out.println("[Registering]: "+"result is equal to null");
                return ResponseEntity.unprocessableEntity().body("Error in saving");
            }

        }else {
            return ResponseEntity.badRequest().body("Invalid parameters");
        }

    }
    /////////////////////////Register method End //////////////////////////////////////////////////////

    @RequestMapping(value = "registerteacher",method = RequestMethod.POST)
    public ResponseEntity<RegisterTeacherResponse> registerTeacher(@RequestBody RegisterTeacherRequestEntity registerTeacherRequestEntity){
        String result=webService.registerTeacher(registerTeacherRequestEntity);
        System.out.println("controller[RegisterTeacher]: "+result);
        return ResponseEntity.accepted().body(new RegisterTeacherResponse(result));
    }

    //Create Session
    @RequestMapping(value = "createsession",method = RequestMethod.POST)
    public ResponseEntity<SessionResponseEntity> createSession(@RequestBody SessionRequestEntity sessionRequestEntity){
       String result= webService.CreateSession(sessionRequestEntity);
        System.out.println("[Controller]"+result);
        return ResponseEntity.ok(new SessionResponseEntity(result));
    }

    //get Student Scores
    @RequestMapping(value = "getstudentscores",method = RequestMethod.POST)
    public ResponseEntity<Scores> getStudentScores(@RequestBody getStudentScoreRequestEntity getStudentScoreRequestEntity){
        Scores scores=webService.getScores(getStudentScoreRequestEntity);
        return ResponseEntity.ok(scores);
    }
    //update Student Score
     @RequestMapping(value = "updatescore",method = RequestMethod.POST)
    public ResponseEntity<UpdateScoreResponseEntity> updateScore(@RequestBody UpdateScoreRequestEntity updateScoreRequestEntity){
         String result=webService.UpdateScore(updateScoreRequestEntity);
         System.out.println(result);
         return ResponseEntity.accepted().body(new UpdateScoreResponseEntity(result));
     }
    //Insert subject
    @RequestMapping(value = "insertsubject",method = RequestMethod.POST)
    public ResponseEntity<InsertSubjectResponseEntity> insertSubject(@RequestBody InsertSubjectRequestEntity insertSubjectRequestEntity){
        String result=webService.insertSubject(insertSubjectRequestEntity);
        System.out.println(result);
        return ResponseEntity.accepted().body(new InsertSubjectResponseEntity(result));
    }
    //retrieve student information
    @RequestMapping(value = "retrievestudentinformation",method = RequestMethod.POST)
    public ResponseEntity<StudentInformationResponseEntity> retrieveStudentInformation(@RequestBody StudentInformationRequestEntity studentInformationRequestEntity){
        StudentInformationResponseEntity studentInformationResponseEntity=webService.retrieveStudentInformation(studentInformationRequestEntity);
        if (studentInformationResponseEntity==null){
            return ResponseEntity.notFound().build();
        }else {
            return ResponseEntity.ok(studentInformationResponseEntity);

        }
    }

    ///////Retrieve inforation sessions////
    @RequestMapping(value = "retrieveinformationsession",method = RequestMethod.GET)
    public ResponseEntity<?> retrieve(){
        ArrayList<String> list=webService.retrieve();
        String string=list.toString();
        if (list==null){
            return ResponseEntity.notFound().build();
        }else {
            HttpHeaders headers=new HttpHeaders();
            headers.add("Content-Type","text/plain; charset=UTF-8");
            System.out.println("[Retrieving session]: response sent");
            return ResponseEntity.ok()
                    .headers(headers)
                    .contentLength(string.length())
                    .body(string);
        }
    }

}
