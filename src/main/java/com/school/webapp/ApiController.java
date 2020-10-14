package com.school.webapp;



import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.school.webapp.BookStore.EditBook.EditBookRequest;
import com.school.webapp.BookStore.SaveBook.BookEntity;
import com.school.webapp.Information.EditInformation.EditInformationImageRequestEntity;
import com.school.webapp.Information.StudentandParent.StudentInformationResponseEntity;
import com.school.webapp.RegisterTeacher.RegisterTeacherRequestEntity;
import com.school.webapp.RegisterTeacher.RegisterTeacherResponse;
import com.school.webapp.Registration.RegistrationModel;
import com.school.webapp.Repository.BookHistory;
import com.school.webapp.RequestModel.AuthenticateRequest;
import com.school.webapp.ResponseModel.JwtResponse;
import com.school.webapp.ResponseModel.JwtUtils;
import com.school.webapp.RetrievNameFromSession.RetrieveNameResponse;
import com.school.webapp.RetrieveParentInformation.RetrieveParentInformationResponseEntity;
import com.school.webapp.RetrieveParentNames.RetrieveParentNameResponse;
import com.school.webapp.SchoolFee.SaveSchoolFee.GetSchoolFee.getSchoolFeeResponseEntity;
import com.school.webapp.SchoolFee.SaveSchoolFee.SaveSchoolFeeRequestEntity;
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

    ///////////////////////////////////////////////////Authentication Session///////////////////////////////////////////////
    //authenticate
    @RequestMapping(value = "/authenticate",method = RequestMethod.POST)
    public ResponseEntity<?> Authenticate(@RequestBody AuthenticateRequest authenticateRequest) throws Exception {
        System.out.println("user Authenticating");
        String jwt=null;
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticateRequest.getUsername(),authenticateRequest.getPassword()));

        }catch (BadCredentialsException e){
            System.out.println("Incorect password [bad Credentials]:");
            return ResponseEntity.notFound().build();
        }
        UserDetails userDetails= myUserDetailsService.loadUserByUsername(authenticateRequest.getUsername());
        if (userDetails!=null){
            jwt=jwtUtils.generateToken(userDetails);
            HttpHeaders headers=new HttpHeaders();
            headers.add("Content-Type","application/json; charset=UTF-8");
            JwtResponse jwtResponse=new JwtResponse();
            jwtResponse.setJwt(jwt);
            return ResponseEntity.ok().headers(headers).body(jwtResponse);
        }else {
            return  ResponseEntity.notFound().build();
        }

    }
    ////////////////////////////////////////////Authentication session end/////////////////////////////////////////////

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////// Student Information Session//////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //////////////////Register Student method////////////////////////////////////////
    @RequestMapping(value = "register/{session}",method = RequestMethod.POST,consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> RegisterStudent(@PathVariable String session,
                                                  @RequestBody MultipartFile studentpicture,
                                                  @RequestBody MultipartFile fatherpicture,
                                                  @RequestBody MultipartFile motherpicture,
                                                  @RequestBody MultipartFile json
                                ) throws SQLException {

        if (json!=null&& studentpicture!=null && fatherpicture!=null && motherpicture!=null&&session!=null){
            RegistrationModel registrationModel=new RegistrationModel();
            System.out.println("[Controller]: Registering student--> "+"Converting json file to bytes");
            System.out.println("[Controller]: Registering student--> "+"Session:"+session);
            try {
                byte [] entity=json.getBytes();
                System.out.println("[Controller]: Registering student--> "+"Converting bytes to string");
                String jsonstring=new String(entity,"UTF-8");
                System.out.println("[Controller]: Registering student--> "+"Converting String to json");

                GsonBuilder builder=new GsonBuilder();
                builder.setPrettyPrinting();
                builder.serializeNulls();
                Gson gson=builder.create();
                System.out.println("[Controller]: Registering student--> "+"preparing json object file");
                registrationModel=gson.fromJson(jsonstring,RegistrationModel.class);
                System.out.println("[Controller]: Registering student--> "+"Finished preparing json object");
            } catch (IOException e) {
                System.out.println("[Controller]: Registering student--> "+"Unable to convert to byte");
                e.printStackTrace();
            }
            String result=webService.RegisterStudent(registrationModel,session, studentpicture,fatherpicture,motherpicture);
            if (result!=null){
                System.out.println("[Controller]: Registering student--> "+"result is not equal to null");
                return ResponseEntity.accepted().body(result);

            }else {
                System.out.println("[Controller]: Registering student--> "+"result is equal to null");
                return ResponseEntity.unprocessableEntity().body("Error in saving");
            }

        }else {
            return ResponseEntity.badRequest().body("Invalid parameters");
        }

    }
    /////////////////////////Register method End //////////////////////////////////////////////////////

    ///Retrieving student information
    @RequestMapping(value = "retrievestudentinformation/{name}/{classselected}",method = RequestMethod.GET)
    public ResponseEntity<?> retrieveStudentInformation(@PathVariable String name,@PathVariable String classselected) {
        System.out.println(name);
        System.out.println(classselected);
        if (name!=null && classselected!=null){
            StudentInformationResponseEntity studentInformationResponseEntity = webService.retrieveStudentinfo(name, classselected);
            if (studentInformationResponseEntity == null &&name==null &&classselected==null) {
                return ResponseEntity.notFound().build();
            } else {
                System.out.println("[Controller]: Retrieving information");
                System.out.println("[Controller]: "+studentInformationResponseEntity.getStudent());
                System.out.println("[Controller]: "+studentInformationResponseEntity.getStudentname());
                System.out.println("[Controller]: Retrieving information--> Preparing response");
                GsonBuilder builder=new GsonBuilder();
                builder.setPrettyPrinting();
                builder.serializeNulls();
                Gson gson=builder.create();
                String json=gson.toJson(studentInformationResponseEntity);
                System.out.println("[Controller]: Retrieving information--> Response sent");
                HttpHeaders headers=new HttpHeaders();
                headers.add("Content-Type","application/json;charset=UTF-8");
                return ResponseEntity.ok().headers(headers).body(json);
            }
        }else{
            return ResponseEntity.badRequest().build();
        }
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////Retrieve Information END/////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////Edit student info///////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @RequestMapping(value = "editstudentinformation/{session}/{newValue}/{id}/{column}",method = RequestMethod.GET)
    public ResponseEntity<?> editStudentInformation(@PathVariable String session,
                                                    @PathVariable String newValue,@PathVariable String id,
                                                    @PathVariable String column){
        System.out.println("[Controller]: EditstudentInformation--> session: "+session);
        System.out.println("[Controller]: EditstudentInformation--> newValue: "+newValue);
        System.out.println("[Controller]: EditstudentInformation--> id: "+id);
        System.out.println("[Controller]: EditstudentInformation--> column: "+column);
        if (session!=null && newValue!=null && id!=null &&column!=null){
            boolean result=webService.editStudentInformation(newValue,id,column,session);
            if (result){
                return ResponseEntity.ok().build();
            }else {
                return ResponseEntity.unprocessableEntity().build();
            }
        }else {
            return ResponseEntity.badRequest().build();
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////Edit student info end///////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////Edit information image ///////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //This method edit both the student,father and mother image
    @RequestMapping(value = "editinformationimage",method = RequestMethod.POST)
    public ResponseEntity<?> editImage(@RequestBody EditInformationImageRequestEntity editInformationImageRequestEntity){

        if (editInformationImageRequestEntity!=null){
            boolean result= webService.EditInformationImage(editInformationImageRequestEntity);
            if (result){
                return ResponseEntity.ok().build();
            }else {
                return ResponseEntity.unprocessableEntity().build();
            }
        }else {
            return ResponseEntity.badRequest().build();
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////Edit information image ///////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //Delete Student info
    @RequestMapping(value = "deletestudent/{id}/{session}",method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteStudent(@PathVariable int id, @PathVariable String session){
        System.out.println("[Controller]: Deleting student--> id: "+id);
        System.out.println("[Controller]: Deleting student--> session: "+session);
        if (id!=0&&session!=null){
            boolean result=webService.deleteStudent(id,session);
            if (result){
                return ResponseEntity.ok().build();
            }else {
                return ResponseEntity.unprocessableEntity().build();
            }
        }else {
            return ResponseEntity.badRequest().build();
        }
    }


    ///Retrieving studentnames
    @RequestMapping(value = "retrivenames/{classselected}")
    public ResponseEntity<?> getNames(@PathVariable String classselected){
        ArrayList<RetrieveNameResponse> list=webService.RetrieveName(classselected);
        GsonBuilder builder=new GsonBuilder();
        builder.serializeNulls();
        Gson gson=builder.create();
        String string=gson.toJson(list);
        if (list==null&&list.isEmpty()){
            return ResponseEntity.notFound().build();
        }else {
            HttpHeaders httpHeaders=new HttpHeaders();
            httpHeaders.add("Content-Type","text/plain; charset=UTF-8");
            System.out.println("[Retrieving name]: response sent");
            return ResponseEntity.ok().headers(httpHeaders).contentLength(string.length()).body(string);
        }
    }
    ///////////Retrieve name End////////////////////



    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////Student information session end/////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////







    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////  teacher Information session/////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    ////////////////////////////////////Register Teacher/////////////////////////////////////////////
    @RequestMapping(value = "registerteacher",method = RequestMethod.POST)
    public ResponseEntity<RegisterTeacherResponse> registerTeacher(@RequestBody RegisterTeacherRequestEntity registerTeacherRequestEntity){
        String result=webService.registerTeacher(registerTeacherRequestEntity);
        System.out.println("controller[RegisterTeacher]: "+result);
        return ResponseEntity.accepted().body(new RegisterTeacherResponse(result));
    }
    ///////////////////////////////////Register Teacher End///////////////////////////////////////////







    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////// Creating session start//////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    ///////////////////////////////////////////Create Session////////////////////////////////////////
    @RequestMapping(value = "createsession",method = RequestMethod.POST)
    public ResponseEntity<SessionResponseEntity> createSession(@RequestBody SessionRequestEntity sessionRequestEntity){
       if (sessionRequestEntity!=null){
           String result= webService.CreateSession(sessionRequestEntity);
           if (result!=null){
               System.out.println("[Controller]"+result);
               return ResponseEntity.ok(new SessionResponseEntity(result));
           }else {
               return ResponseEntity.unprocessableEntity().build();
           }
       }else {
           return ResponseEntity.badRequest().build();
       }
    }
    //////////////////////////Create session end///////////////////////////////////////////////////


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////// Creating session end//////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////






    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////Score session begin/////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    ///////////////////////////////////////////////////////////////////////////////
    ////////////////get Student Scores////////////////////////////////////////////
    /////////////////this method get the student scores////////////////////////////
    /////The requestbody takes in name of the student and retrieve all the CA from
    ///the table which is also present in the request body////////////////////////
    /////////////////////////////////////////////////////////////////////////////
    @RequestMapping(value = "getstudentscores",method = RequestMethod.POST,consumes = MediaType.MULTIPART_FORM_DATA_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getStudentScores(@RequestBody MultipartFile jsonbody){
        if (jsonbody!=null){
            getStudentScoreRequestEntity getStudentScoreRequestEntity=new getStudentScoreRequestEntity();
            try {
                System.out.println("[Controller]-->Converting bytes to Json Object]");
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
            if (scores!=null&&!scores.isEmpty()){
                System.out.println("[Controller-->Getting Student scores]-->Response is ok");
                GsonBuilder builder=new GsonBuilder();
                builder.setPrettyPrinting();
                builder.serializeNulls();
                Gson gson=builder.create();
                String json=gson.toJson(scores);
                System.out.println(json);
                HttpHeaders headers=new HttpHeaders();
                headers.add("Content-Type","application/json; charset=UTF-8");
                System.out.println("[Controller-->Getting Student scores]--> Response sent");
                return ResponseEntity.ok().headers(headers).body(json);
            }else {
                System.out.println("[Controller-->Getting Student scores]-->score is not found");
                return ResponseEntity.notFound().build();
            }
        }else {
            return ResponseEntity.badRequest().build();
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
                 System.out.println("[Controller-->Updating score]--> "+updateScoreRequestEntity.getId());
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
                System.out.println("[Controller]:-->Converting request to bytes");
                byte[] bytes=json.getBytes();
                String raw=new String(bytes,"UTF-8");
                System.out.println("[Controller]:-->Converting bytes to json Object");
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
    @RequestMapping(value = "insertsubject/{subject}/{session}/{studentname}/{term}",method = RequestMethod.GET)
    public ResponseEntity<?> insertSubject(@PathVariable String subject,@PathVariable String session,@PathVariable  String studentname,@PathVariable String term){

        if(subject!=null&&session!=null&&studentname!=null&&term!=null){
            System.out.println("[Controller]-->Inserting subject");
            String result=webService.insertSubject(subject,session,studentname,term);
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
    @RequestMapping(value = "deletesubject/{id}/{session}")
    public ResponseEntity<?>deleteSubject(@PathVariable String id,@PathVariable String session){
        System.out.println("[Controller]-->Deleting Subject: id:"+id);
        System.out.println("[Controller]-->Deleting Subject: Session:"+session);
        if (session!=null&&id!=null){
            boolean result=webService.deleteSubject(id,session);
            if (result){
                System.out.println("[Controller]-->Deleting Subject: "+result);
                return ResponseEntity.ok().build();
            }else {
                return ResponseEntity.notFound().build();
            }
        }else {
            return ResponseEntity.badRequest().build();
        }
    }


    ////////////////////////////////////DELETE SUBJECT END////////////////////////////////////////////////////////////

    /////////////////////////////////////////retrieve student information/////////////////////////////////////////////
    //This method return Json containing Student info including bytes of images///////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////




    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////Score session end//////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////





    //////////////////////////////////////////////////////////////////////////////////
    ///////Retrieve information sessions.this method returns a string of sessions////
    //this method return string in json format////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////
    @RequestMapping(value = "retrieveinformationsession",method = RequestMethod.GET)
    public ResponseEntity<?> retrieve(){
        ArrayList<String> list=webService.retrieve();
        String string=list.toString();
        if (list.isEmpty()&&list==null){
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
        if (list==null&&list.isEmpty()){
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








    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////Parent information session start////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////This method get the names of the parent  ///////////////////
    ///////////it takes in the session and fetch the names from it from the database/////
     @RequestMapping(value = "retrieveparent/{session}",method = RequestMethod.GET)
    public ResponseEntity<?> getParentNames(@PathVariable String session){
         ArrayList<RetrieveParentNameResponse> list=webService.retrieveParentname(session);
         if (list==null&&list.isEmpty()){
             return ResponseEntity.notFound().build();
         }else {
             GsonBuilder builder=new GsonBuilder();
             builder.serializeNulls();
             Gson gson=builder.create();
             String json=gson.toJson(list);
             HttpHeaders httpHeaders=new HttpHeaders();
             httpHeaders.add("Content-Type","text/plain; charset=UTF-8");
             System.out.println("[Controller]-->Retrieving parent names:"+json);
             System.out.println("[Controller]-->Retrieving parent names: response sent");
             return ResponseEntity.ok().headers(httpHeaders).contentLength(json.length()).body(json);
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
    /////////////////////End/////////////////////////////////////




    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////Parent information session end////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////SCHOOL FEE SESSION//////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////



    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////Getting school fee/////////////////////////////////////////////////////////
    @RequestMapping(value = "getschoolfee/{clas}/{term}/{year}/{tag}",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getSchoolFee(@PathVariable String clas,@PathVariable String term,@PathVariable String year,@PathVariable String tag){
        System.out.println("[Controller]:getting School fee-->\r\n class: "+clas+"\r\n term: "+term+"\r\n year: "+year+"\r\n tag: "+tag);
        if (clas!=null&&term!=null&&year!=null&&tag!=null){
            ArrayList<getSchoolFeeResponseEntity> schoolFees=webService.getSchoolFees(clas,term,year,tag);
            System.out.println(schoolFees.toString());
            HttpHeaders headers;
            if (schoolFees!=null&&!schoolFees.isEmpty()){
                headers=new HttpHeaders();
                headers.add("Content-Type","application/json; charset=UTF-8");
                System.out.println("[Controller]:getting School fee-->Response sent");
                return ResponseEntity.ok().headers(headers).body(schoolFees);
            }else {
                System.out.println("[Controller]:getting School fee-->Could not get School fees:Result is null");
                return ResponseEntity.notFound().build();
            }
        }else {
            System.out.println("[Controller]:getting School fee-->Could not get School fees");
            return ResponseEntity.badRequest().build();
        }
    }
    ///////////////////////////////////////Getting School Fee END/////////////////////////////////////////////////////

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////Getting school fee without term/////////////////////////////////////////////////////////
    @RequestMapping(value = "getschoolfeewithoutterm/{clas}/{session}/{tag}",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getSchoolFeeWithoutTerm(@PathVariable String clas,@PathVariable String session,@PathVariable String tag){
        System.out.println("[Controller]:getting School fee without term-->\r\n class: "+clas+"\r\n session: "+session+"\r\n tag: "+tag);
        if (clas!=null&&session!=null&&tag!=null){
            ArrayList<getSchoolFeeResponseEntity> schoolFees=webService.getSchoolFeesWithoutTerm(clas,session,tag);
            System.out.println(schoolFees.toString());
            HttpHeaders headers;
            if (schoolFees!=null&&!schoolFees.isEmpty()){
                headers=new HttpHeaders();
                headers.add("Content-Type","application/json; charset=UTF-8");
                System.out.println("[Controller]:getting School fee without term -->Response sent");
                return ResponseEntity.ok().headers(headers).body(schoolFees);
            }else {
                System.out.println("[Controller]:getting School fee without term--->Could not get School fees:Result is null");
                return ResponseEntity.notFound().build();
            }
        }else {
            System.out.println("[Controller]:getting School fee without term--->Could not get School fees");
            return ResponseEntity.badRequest().build();
        }
    }
    ///////////////////////////////////////Getting School Fee without END/////////////////////////////////////////////////////


    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////saving term school fee/////////////////////////////////////////////////////////
    ///////This method add the term into the school fee table when the student paid

    @RequestMapping (value = "saveterm/{term}/{studentname}/{clas}/{session}/{tag}",method = RequestMethod.GET)
    public ResponseEntity<?> saveTerm(@PathVariable String studentname,@PathVariable String clas,
                                      @PathVariable String session,@PathVariable String tag,
                                      @PathVariable String term){
        System.out.println("[Controller]:Saving term-->\r\n class: "+clas+"\r\n session: "+session+"\r\n term: "+term+"\r\n name: "+studentname+"\r\n tag: "+tag);
        if (studentname!=null &&clas!=null &&session!=null&&tag!=null&&term!=null){
            boolean result=webService.saveterm(studentname,clas,session,tag,term);
            if (result){
                System.out.println("[Controller]: Result:"+result);
                return ResponseEntity.accepted().build();
            }else {
                return ResponseEntity.unprocessableEntity().build();
            }
        }else {
            System.out.println("[Controller]: Bad request returning response");
            return ResponseEntity.badRequest().build();
        }
    }
    ///////////////////////////////////////SAVING TERM END/////////////////////////////////////////////////////


    //////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////Save student name to school fee table/////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //This method save student name to school fee table
    @RequestMapping(value = "savestudentnametoschoolfee",method = RequestMethod.POST)
    public ResponseEntity<?> saveStudentToSchoolFee(@RequestBody SaveSchoolFeeRequestEntity saveSchoolFeeRequestEntity){
        if (saveSchoolFeeRequestEntity!=null){
            System.out.println("[Contoller]: Student name:"+saveSchoolFeeRequestEntity.getStudentname());
            System.out.println("[Contoller]: class:"+saveSchoolFeeRequestEntity.getClas());
            System.out.println("[Contoller]: session:"+saveSchoolFeeRequestEntity.getYear());
            System.out.println("[Contoller]: tag:"+saveSchoolFeeRequestEntity.getTag());
            System.out.println("[Contoller]: term:"+saveSchoolFeeRequestEntity.getTerm());
            System.out.println("[Contoller]:  Proceeding to web service");
            boolean result=webService.saveSchoolFee(saveSchoolFeeRequestEntity);
            if (result){
                return ResponseEntity.ok().build();
            }else {
                return ResponseEntity.unprocessableEntity().build();
            }
        }else {
            return ResponseEntity.badRequest().build();
        }

    }

    ////////////////////////////////////Save Student name to school fee end////////////////////////////////



    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////saving data to school fee table/////////////////////////////////////////////////////////
    ///////This method save data to schoolfee table with respect to the student name

    @RequestMapping (value = "savedatatoschoolfeetable/{studentid}/{column}/{entity}",method = RequestMethod.GET)
    public ResponseEntity<?> saveDataToSchoolFeeTable(@PathVariable String studentid,@PathVariable String column,@PathVariable String entity){
        System.out.println("[Controller]:Saving data to schoolfee table-->\r\n student id: "+studentid+"\r\n column: "+column+"\r\n entity: "+entity);
        if (studentid!=null &&column!=null&&entity!=null){
            boolean result=webService.saveDataToSchoolFeeTable(studentid,column,entity);
            if (result){
                System.out.println("[Controller]: Result:"+result);
                return ResponseEntity.accepted().build();
            }else {
                return ResponseEntity.unprocessableEntity().build();
            }
        }else {
            System.out.println("[Controller]: Bad request returning response");
            return ResponseEntity.badRequest().build();
        }
    }
///////////////////////////////////////SAVING TERM END/////////////////////////////////////////////////////


@RequestMapping(value = "deleteschoolfee/{id}",method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteSchoolFee(@PathVariable String id){
    System.out.println("[Controller]:Saving data to schoolfee table-->\r\n id: "+id);
    if (id!=null){
       boolean result= webService.deleteSchoolFee(id);
       if (result){
           System.out.println("[Controller]: Result is OK");
           return ResponseEntity.ok().build();
       }else {
           System.out.println("[Controller]: Result is not OK");
           return ResponseEntity.unprocessableEntity().build();
       }
    }else {
        return ResponseEntity.badRequest().build();
    }
}







    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////Getting debtors/////////////////////////////////////////////////////////
    @RequestMapping(value = "getdebtors/{clas}/{term}/{year}/{minimumfee}/{tag}",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getDebtors(@PathVariable String clas,@PathVariable String term,@PathVariable String year,@PathVariable int minimumfee,@PathVariable String tag){
        System.out.println("[Controller]:getting debtors-->\r\n class: "+clas+"\r\n session: "+year+"\r\n term: "+term+"\r\n minimum: "+minimumfee+"\r\n tag:"+tag);
        if (clas!=null&& term!=null &&year!=null&&minimumfee!=0&&tag!=null){
            //This also return list of getSchoolfeeResponseEntity because we are getting the same data as getting school fee
            ArrayList<getSchoolFeeResponseEntity> debtors=webService.getDebtors(clas,term,year,minimumfee,tag);
            if (debtors!=null&&!debtors.isEmpty()){
                System.out.println("[Controller]:getting Debtors-->Preparing response in json format");
                GsonBuilder builder=new GsonBuilder();
                builder.serializeNulls();
                builder.setPrettyPrinting();
                Gson gson=builder.create();
                String json=gson.toJson(debtors);
                System.out.println("[Controller]:getting Debtors --> "+json);
                HttpHeaders headers=new HttpHeaders();
                headers.add("Content-Type","application/json; charset=UTF-8");
                return ResponseEntity.ok().headers(headers).body(json);
            }else {
                return ResponseEntity.notFound().build();
            }
        }else {
            System.out.println("[Controller]:getting School fee-->Could not get debtors");
            return ResponseEntity.badRequest().build();
        }

    }

    ///////////////////////////////////////Getting debtors END/////////////////////////////////////////////////////



    ////////////////////////////////////////////////SCHOOL FEE SESSION END////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////





    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////BOOK SESSION/////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @RequestMapping(value = "savebook",method = RequestMethod.POST)
    public ResponseEntity<?> saveBooktoStore(@RequestBody BookEntity book){
        if (book!=null){
            boolean result=webService.saveBook(book);
            if (result){
                System.out.println("[Controller]:Saving book-->Saving book");
                return ResponseEntity.ok().build();
            }else {
                return ResponseEntity.notFound().build();
            }
        }else {
            System.out.println("[Controller]:Saving book-->Saving book-->Result is false");
            return ResponseEntity.badRequest().build();
        }
    }
    //Delete book
    @RequestMapping(value = "deletebook/{id}",method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteBook(@PathVariable int id){
        if (id!=0){
            boolean result=webService.deleteBook(id);
            if (result){
                return ResponseEntity.ok().build();
            }else {
                return ResponseEntity.unprocessableEntity().build();
            }
        }else {
            return ResponseEntity.badRequest().build();
        }
    }
    //Find all book
    @RequestMapping(value = "findallbook",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> findAllBook(){
        final ArrayList<BookEntity> allBooks=webService.findAllBooks();
        if (allBooks!=null&&!allBooks.isEmpty()){
            return ResponseEntity.ok().body(allBooks);
        }else {
            System.out.println("[Controller]:Getting all books-->Result is false");
            return ResponseEntity.notFound().build();
        }
    }
    //Search Book
    @RequestMapping(value = "searchbook/{bookname}/{session}/{term}",method = RequestMethod.GET)
    public ResponseEntity<?> searchBook(@PathVariable String bookname,@PathVariable String session,@PathVariable String term){
        System.out.println("[Controller]:getting debtors-->\r\n bookname: "+bookname+"\r\n session: "+session+"\r\n term: "+term);
        if (!bookname.isEmpty()&&!session.isEmpty()&&!term.isEmpty()){
          final ArrayList<BookEntity> resultbooks=webService.searchBook(bookname,session,term);
          if (resultbooks!=null&&!resultbooks.isEmpty()){
              System.out.println("[Controller]:Searching books-->result= "+resultbooks.toString());
              return ResponseEntity.ok().body(resultbooks);
          }else {
              System.out.println("[Controller]:Searching books-->Result is false");
              return ResponseEntity.notFound().build();
          }
      }else {
          return ResponseEntity.badRequest().build();
      }
    }
    //Sell book
    @RequestMapping(value = "sellbook/{bookname}/{term}/{session}/{buyer}",method = RequestMethod.POST)
    public ResponseEntity<?> sellBook(@RequestBody BookEntity bookEntity,@PathVariable String bookname, @PathVariable String term, @PathVariable String session, @PathVariable String buyer){
        System.out.println("[Controller]:selling books-->\r\n bookname: "+bookname+"\r\n session: "+session+"\r\n term: "+term+"\r\n buyer: "+buyer);
        if (bookname!=null&&term!=null&&session!=null&&buyer!=null&&bookEntity!=null){
           boolean result=new WebService().sellBook(bookname,term,session,buyer,bookEntity);
            if (result){
                System.out.println("[Controller]:Selling books-->Book Sold,returning response");
                return ResponseEntity.ok().build();
            }else {
                System.out.println("[Controller]:Selling books-->Result is false");
                return ResponseEntity.unprocessableEntity().build();
            }
        }else {
            return ResponseEntity.badRequest().build();
        }
    }
    //book history
    @RequestMapping(value = "getbookhistory/{session}/{term}/{date}",method = RequestMethod.GET)
    public ResponseEntity<?> getBookHistory(@PathVariable String session,@PathVariable int term,@PathVariable String date){
        System.out.println("[Controller]:getting book history");
       if (!session.isEmpty()&&term!=0&&!date.isEmpty()){
           ArrayList<BookHistory> histories=webService.getBookHistory(session,term,date);
           if (!histories.isEmpty()&&histories!=null){
               GsonBuilder builder=new GsonBuilder();
               builder.serializeNulls();
               builder.setPrettyPrinting();
               Gson gson=builder.create();
               String json=gson.toJson(histories);
               System.out.println("[Controller]: book history-->"+json);
               HttpHeaders headers=new HttpHeaders();
               headers.add("Content-Type","application/json; charset=UTF-8");
               return ResponseEntity.ok().headers(headers).body(json);
           }else {
               return ResponseEntity.noContent().build();
           }

       }else {
           return ResponseEntity.badRequest().build();
       }
    }
//    //Delete Book History
//    @RequestMapping(value = "deletebookHistory/{id}",method = RequestMethod.DELETE)
//    public ResponseEntity<?> deleteBookHistory(@PathVariable String id){
//        System.out.println("[Controller]:deleting book history with id="+id);
//        if (id!=null){
//            boolean result=webService.deleteBookHistory(id);
//            if (result){
//                return ResponseEntity.ok().build();
//            }else{
//                return ResponseEntity.notFound().build();
//            }
//        }else {
//            return ResponseEntity.badRequest().build();
//        }
//    }
    //Edit book
    @RequestMapping(value = "editbook",method = RequestMethod.POST)
    public ResponseEntity<?> editBook(@RequestBody EditBookRequest editBookRequest){
        if (editBookRequest!=null){
            System.out.println("[Controller]:Editing book-->proceeding to web service");
            boolean result=webService.editBook(editBookRequest);
            if (result){
                return ResponseEntity.ok().build();
            }else {
                return ResponseEntity.notFound().build();
            }
        }else {
            return ResponseEntity.badRequest().build();
        }
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////BOOK SESSION END////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////


}
