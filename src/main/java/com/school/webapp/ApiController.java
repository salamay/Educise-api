package com.school.webapp;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.school.webapp.Information.StudentandParent.StudentInformationResponseEntity;
import com.school.webapp.RegisterTeacher.RegisterTeacherRequestEntity;
import com.school.webapp.RegisterTeacher.RegisterTeacherResponse;
import com.school.webapp.Registration.RegistrationModel;
import com.school.webapp.RequestModel.AuthenticateRequest;
import com.school.webapp.ResponseModel.JwtResponse;
import com.school.webapp.ResponseModel.JwtUtils;
import com.school.webapp.RetrieveParentInformation.RetrieveParentInformationResponseEntity;
import com.school.webapp.Session.SessionRequestEntity;
import com.school.webapp.Session.SessionResponseEntity;
import com.school.webapp.StudentScore.*;
import com.school.webapp.WebAppService.WebService;
import com.school.webapp.security.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.*;
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
    private  WebService webService;

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
       if (result!=null){
           System.out.println("[Controller]"+result);
           return ResponseEntity.ok(new SessionResponseEntity(result));
       }else {
           return ResponseEntity.badRequest().build();
       }

    }

    ///////////////////////////////////////////////////////////////////////////////
    ////////////////get Student Scores////////////////////////////////////////////
    /////////////////this method get the student scores////////////////////////////
    /////The requestbody takes in name of the student and retrieve all the CA from
    ///the table which is also present in the request body////////////////////////
    /////////////////////////////////////////////////////////////////////////////
    @RequestMapping(value = "getstudentscores",method = RequestMethod.POST,consumes = MediaType.MULTIPART_FORM_DATA_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getStudentScores(@RequestBody MultipartFile jsonbody){
        System.out.println(jsonbody);
        if (jsonbody!=null){
            getStudentScoreRequestEntity getStudentScoreRequestEntity=new getStudentScoreRequestEntity();
            try {
                System.out.println("[Controller-->Converting bytes to Json Object]");
                byte [] bytes = jsonbody.getBytes();
                String raw=new String(bytes,"UTF-8");
                GsonBuilder convert=new GsonBuilder();
                convert.setPrettyPrinting();
                convert.serializeNulls();
                Gson gson=convert.create();
                getStudentScoreRequestEntity=gson.fromJson(raw,getStudentScoreRequestEntity.class);
            } catch (IOException e) {

                e.printStackTrace();
                return ResponseEntity.unprocessableEntity().body("Unprocessable Entity");
            }
            System.out.println("[Controller-->Getting Student scores]");
            ArrayList<Scores> scores=webService.getScores(getStudentScoreRequestEntity);
            System.out.println("[Controller-->Getting Student scores]-->Response is not equal to null");
            if (scores!=null){
                System.out.println("[Controller-->Getting Student scores]--> Building json response body");
                System.out.println("[Controller-->Getting Student scores]--> Response sent");
                return ResponseEntity.ok().body(scores);
            }else {
                return ResponseEntity.notFound().build();
            }
        }else {
            return ResponseEntity.notFound().build();
        }

    }
    //////////////////////////END//////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////


    ////////////////////////////update Student Score/////////////////////////
    //////////////////////This method update student score///////////////////
    /////////////////////////////////////////////////////////////////////////
     @RequestMapping(value = "updatescore",method = RequestMethod.POST)
    public ResponseEntity<?> updateScore(@RequestBody MultipartFile file){
         if (file!=null){
             UpdateScoreRequestEntity updateScoreRequestEntity=new UpdateScoreRequestEntity();
             System.out.println("[Controller-->Updating score]--> Coverting json request");
             try {
                 byte[] bytes=file.getBytes();
                 String raw=new String(bytes,"UTF-8");
                 GsonBuilder builder=new GsonBuilder();
                 builder.setPrettyPrinting();
                 builder.serializeNulls();
                 Gson gson=builder.create();
                 updateScoreRequestEntity=gson.fromJson(raw,UpdateScoreRequestEntity.class);
                 System.out.println("[Controller-->Updating score]--> "+updateScoreRequestEntity.getSubject());
             } catch (IOException e) {
                 e.printStackTrace();
                 return  ResponseEntity.badRequest().build();
             }
             String result=webService.UpdateScore(updateScoreRequestEntity);
             if (result!=null){
                 System.out.println(result);
                 return ResponseEntity.accepted().body(new UpdateScoreResponseEntity(result));
             }else {
                 return ResponseEntity.unprocessableEntity().build();
             }

         }else{
             return  ResponseEntity.badRequest().build();
         }

     }
     /////////////////////////////END///////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////

     //////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////update subject/////////////////////////////////////
    //this consumes a Json which contain the name of the student map a subject t,
    /// table (a table in a Database) and the subject///////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////
    @RequestMapping(value = "updatesubject",method = RequestMethod.POST,consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<UpdateSubjectResponseEntity> insertSubject(@RequestBody MultipartFile json){

        if (json!=null){
            UpdateSubjectRequestEntity updateSubjectRequestEntity =new UpdateSubjectRequestEntity();
            try {
                System.out.println("[InsertSubject]:-->Converting request to bytes");
                byte[] bytes=json.getBytes();
                String raw=new String(bytes,"UTF-8");
                System.out.println("[InsertSubject]:-->Converting bytes to json Object");
                GsonBuilder builder=new GsonBuilder();
                builder.serializeNulls();
                builder.setPrettyPrinting();
                Gson gson=builder.create();
                updateSubjectRequestEntity =gson.fromJson(raw, UpdateSubjectRequestEntity.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
            String result=webService.updateSubject(updateSubjectRequestEntity);
            System.out.println(result);
            if (result!=null){
                return ResponseEntity.accepted().body(new UpdateSubjectResponseEntity(result));
            }else {
                return  ResponseEntity.unprocessableEntity().build();
            }

        }else {
            return  ResponseEntity.badRequest().build();
        }
    }
    /////////////////////////////Update SUBJECT END/////////////////////////////////////////////////

    /////////////////////////////////////Insert Subject////////////////////////////////////////////
    @RequestMapping(value = "insertsubject/{subject}/{session}/{studentname}",method = RequestMethod.GET)
    public ResponseEntity<?> insertSubject(@PathVariable String subject,@PathVariable String session,@PathVariable  String studentname){

        if(subject!=null&&session!=null&&studentname!=null){
            System.out.println("[Controller]-->Inserting subject");
            String result=webService.insertSubject(subject,session,studentname);
            if (result!=null){
                return ResponseEntity.accepted().build();
            }else {
                return ResponseEntity.unprocessableEntity().build();
            }
        }else {
            return ResponseEntity.badRequest().build();
        }
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////Insert subject END//////////////////////////////////////////////

    /////////////////////////////////////Delete SUbject//////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////
    @RequestMapping(value = "deletesubject/{subject}/{session}/{studentname}")
    public ResponseEntity<?>deleteSubject(@PathVariable String subject,@PathVariable String session,@PathVariable String studentname){
        if (subject!=null&&session!=null&&studentname!=null){
            boolean result=webService.deleteSubject(subject,session,studentname);
            if (result){
                System.out.println("[DeletSubject]-->Deleting Subject: "+result);
                return ResponseEntity.ok().build();
            }else {
                return ResponseEntity.notFound().build();
            }
        }else {
            return ResponseEntity.badRequest().build();
        }
    }


    ////////////////////////////////////DELETE SUBJECT END///////////////////////////////////////////////

    /////////////////////////////////////////retrieve student information///////////////////////////////////////////
    //This method return Json containing Student info including bytes of images///////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @RequestMapping(value = "retrievestudentinformation/{name}/{classselected}",method = RequestMethod.GET)
    public ResponseEntity<?> retrieveStudentInformation(@PathVariable String name,@PathVariable String classselected) {
        System.out.println(name);
        System.out.println(classselected);
        StudentInformationResponseEntity studentInformationResponseEntity = webService.retrieveStudentinfo(name, classselected);
        if (studentInformationResponseEntity == null) {
            return ResponseEntity.notFound().build();
        } else {
            String json;
            HttpHeaders httpHeaders;
            System.out.println("[Controller]: Retrieving information");
            System.out.println("[Controller]: Preparing json response");
            GsonBuilder builder = new GsonBuilder();
            builder.setPrettyPrinting();
            builder.serializeNulls();
            Gson gson = builder.create();
            //this convert the response instance to json
            json = gson.toJson(studentInformationResponseEntity);
            if (json != null) System.out.println("[Controller]: json body processed succesfully");else System.out.println("[Controller]: Gson body processed failed");
            System.out.println("[Controller]: Preparing images");
            httpHeaders = new HttpHeaders();
            httpHeaders.add("Content-Type", "application/json; charset=UTF-8");
            System.out.println("[Controller]: Response sent");
            System.out.println("[Controller]: Preparing Successful");
            return ResponseEntity.ok().headers(httpHeaders).body(json);

        }
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////Retrieve Information END/////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //////////////////////////////////////////////////////////////////////////////////
    ///////Retrieve information sessions.this method returns a string of sessions////
    //this method return string in json format////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////
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
    ///////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////
    /////retrieve information Session end /////////////////
    ///////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////

    /////////////////////////////////////////////////////////////////////////
    //////////////////This Method retrieve Score Session /////////////////////
    //////////////////////////////////////////////////////////////////////////
    @RequestMapping(value = "retrievescoresession",method = RequestMethod.GET)
    public ResponseEntity<?> retrieveScoreSession(){
        ArrayList<String> list=webService.retrieveScoreSession();
        String string=list.toString();
        if (list==null){
            return ResponseEntity.notFound().build();
        }else {
            HttpHeaders headers=new HttpHeaders();
            headers.add("Content-Type","text/plain; charset=UTF-8");
            System.out.println("[Retrieving Score session]: response sent");
            return ResponseEntity.ok()
                    .headers(headers)
                    .contentLength(string.length())
                    .body(string);
        }
    }
    ///////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////
    /////retrieve score session Session end ///////////////
    ///////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////
    @RequestMapping(value = "retrivenames/{classselected}")
        public ResponseEntity<?> getNames(@PathVariable String classselected){
        ArrayList<String> list=webService.RetrieveName(classselected);
        String string=list.toString();
        if (list==null){
            return ResponseEntity.notFound().build();
        }else {
            HttpHeaders httpHeaders=new HttpHeaders();
            httpHeaders.add("Content-Type","text/plain; charset=UTF-8");
            System.out.println("[Retrieving name]: response sent");
            return ResponseEntity.ok().headers(httpHeaders).contentLength(string.length()).body(string);
        }
        }
        ///////////Retrieve name End////////////////////
        ////////////////////////////////////////////////////////
        /////////////////////////////////////////////////
        //////////////////////////////////

    /////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////This method get the names of the parent  ///////////////////
    ///////////it takes in the session and fetch the names from it from the database/////
     @RequestMapping(value = "retrieveparent/{session}",method = RequestMethod.GET)
    public ResponseEntity<?> getParentNames(@PathVariable String session){
         ArrayList<String> list=webService.retrieveParentname(session);
         String string=list.toString();
         if (list==null){
             return ResponseEntity.notFound().build();
         }else {
             HttpHeaders httpHeaders=new HttpHeaders();
             httpHeaders.add("Content-Type","text/plain; charset=UTF-8");
             System.out.println("[Controller]-->Retrieving parent names:"+list);
             System.out.println("[Controller]-->Retrieving parent names: response sent");
             return ResponseEntity.ok().headers(httpHeaders).contentLength(string.length()).body(string);
         }

     }
     /////////////////////////////////END/////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////


    ////////////////////////////////parent Information//////////////////////////////////
    ////////This method retrieve the Parent Information////////////////////////////////
    //this take in the session and the parent name,it fetch the information from session where ParentName=parentname
    ////////////////////////////////////////////////////////////////////////////////////////////////////
    @RequestMapping(value = "getparentinformation/{session}/{parentname}",method = RequestMethod.GET)
    public ResponseEntity<?> getParentInformation(@PathVariable String session,@PathVariable String parentname){
        if (session!=null&&parentname!=null){
            RetrieveParentInformationResponseEntity retrieveParentInformationResponseEntity=webService.getParentInfo(session,parentname);
            HttpHeaders headers=new HttpHeaders();
            if (retrieveParentInformationResponseEntity!=null){
                System.out.println("[Controller]-->Sending Response");
                headers.add("Content-Type","application/json; charset=UTF-8");
                GsonBuilder builder=new GsonBuilder();
                builder.setPrettyPrinting();
                builder.serializeNulls();
                Gson gson=builder.create();
                String response=gson.toJson(retrieveParentInformationResponseEntity);
                return ResponseEntity.ok(response);
            }else {
                return ResponseEntity.notFound().headers(headers).build();
            }
        }else {
            return ResponseEntity.badRequest().build();
        }
    }
    /////////////////////End////////////////////////////////////////////

}
