package com.school.webapp;


import com.school.webapp.RegisterTeacher.TeachernamesResponseEntity;
import com.school.webapp.Repository.Contact;
import com.school.webapp.Repository.ContactRepo;
import com.school.webapp.Repository.User;
import com.school.webapp.RequestModel.*;
import com.school.webapp.WebAppService.MyException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.school.webapp.BookStore.EditBook.EditBookRequest;
import com.school.webapp.BookStore.SaveBook.BookEntity;
import com.school.webapp.WebAppService.Information.EditInformation.EditInformationImageRequestEntity;
import com.school.webapp.WebAppService.Information.StudentandParent.StudentInformationResponseEntity;
import com.school.webapp.RegisterTeacher.RegisterTeacherRequestEntity;
import com.school.webapp.RegisterTeacher.RegisterTeacherResponse;
import com.school.webapp.WebAppService.Registration.RegistrationModel;
import com.school.webapp.Repository.BookHistory;
import com.school.webapp.ResponseModel.JwtResponse;
import com.school.webapp.ResponseModel.JwtUtils;
import com.school.webapp.WebAppService.RetrievNameFromSession.RetrieveNameResponse;
import com.school.webapp.RetrieveParentInformation.RetrieveParentInformationResponseEntity;
import com.school.webapp.RetrieveParentNames.RetrieveParentNameResponse;
import com.school.webapp.WebAppService.SaveSchoolFee.GetSchoolFee.getSchoolFeeResponseEntity;
import com.school.webapp.WebAppService.SaveSchoolFee.SaveSchoolFeeRequestEntity;
import com.school.webapp.Session.SessionRequestEntity;
import com.school.webapp.Session.SessionResponseEntity;
import com.school.webapp.WebAppService.StudentScore.*;
import com.school.webapp.WebAppService.WebService;
import com.school.webapp.security.MyUserDetailsService;
import okhttp3.Response;
import org.apache.commons.lang.time.DateUtils;
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
import java.io.UnsupportedEncodingException;

import java.security.InvalidKeyException;

import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;

import javax.crypto.spec.SecretKeySpec;

import javax.xml.bind.DatatypeConverter;

import org.json.JSONException;

import org.json.JSONObject;


import java.io.*;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.*;


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
    @Autowired
    private ContactRepo contactRepo;

    @RequestMapping(value = "/hello")
    public String hello() {
        return "Rejhd";
    }
    ///////////////////////////////////////////////////Authentication Session///////////////////////////////////////////////
    //authenticate
    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> Authenticate(@RequestBody AuthenticateRequest authenticateRequest) throws Exception {
        System.out.println("user Authenticating");
        String jwt = null;
        if (authenticateRequest.getStaffid() != null && authenticateRequest.getPassword() != null) {
            try {
                //The staffid in authenticateRequest is in this format username,schoolid,it is then split to get the
                // username and schoolid, the schoolid is used as part of token payload for subsequent request
                List<String> userdata = Arrays.asList(authenticateRequest.getStaffid().split(","));
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticateRequest.getStaffid(), authenticateRequest.getPassword()));
                ////Return back a token as a response,
                ///This serve as an auhourization for the user
                UserDetails userDetails = myUserDetailsService.loadUserByUsername(authenticateRequest.getStaffid());
                if (userDetails != null) {
                    jwt = jwtUtils.generateToken(userDetails, userdata.get(1));
                    HttpHeaders headers = new HttpHeaders();
                    headers.add("Content-Type", "application/json; charset=UTF-8");
                    JwtResponse jwtResponse = new JwtResponse();
                    jwtResponse.setJwt(jwt);
                    return ResponseEntity.ok().headers(headers).body(jwtResponse);
                } else {
                    return ResponseEntity.notFound().build();
                }
            } catch (BadCredentialsException e) {
                System.out.println("Incorect password [bad Credentials]:");
                return ResponseEntity.unprocessableEntity().body("Incorect password [bad Credentials]");
            }

        } else {
            return ResponseEntity.badRequest().build();
        }

    }
    ////////////////////////////////////////////Authentication session end/////////////////////////////////////////////


    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////// Student Information Session//////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //////////////////Register Student method////////////////////////////////////////
    @RequestMapping(value = "register", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> RegisterStudent(@RequestBody MultipartFile studentpicture,
                                                  @RequestBody MultipartFile fatherpicture,
                                                  @RequestBody MultipartFile motherpicture,
                                                  @RequestBody MultipartFile otherpicture,
                                                  @RequestBody MultipartFile json,
                                                  @RequestAttribute String schoolid

    ) throws SQLException, MyException {

        if (json != null && studentpicture != null && fatherpicture != null && motherpicture != null && schoolid != null) {
            RegistrationModel registrationModel = new RegistrationModel();
            System.out.println("[Controller]: Registering student--> " + "Converting json file to bytes");
            try {
                byte[] entity = json.getBytes();
                System.out.println("[Controller]: Registering student--> " + "Converting bytes to string");
                String jsonstring = new String(entity, "UTF-8");
                System.out.println("[Controller]: Registering student--> " + "Converting String to json");

                GsonBuilder builder = new GsonBuilder();
                builder.setPrettyPrinting();
                builder.serializeNulls();
                Gson gson = builder.create();
                System.out.println("[Controller]: Registering student--> " + "preparing json object file");
                registrationModel = gson.fromJson(jsonstring, RegistrationModel.class);
                System.out.println("[Controller]: Registering student--> " + "Finished preparing json object");
            } catch (IOException e) {
                System.out.println("[Controller]: Registering student--> " + "Unable to convert to byte");
                e.printStackTrace();
            }
            String result = webService.RegisterStudent(registrationModel, studentpicture, fatherpicture, motherpicture, otherpicture, schoolid);
            System.out.println("[Controller]: Registering student--> " + "result is not equal to null");
            return ResponseEntity.ok().body(result);
        } else {
            return ResponseEntity.badRequest().body("Invalid parameters");
        }

    }
    /////////////////////////Register method End //////////////////////////////////////////////////////

    ///Retrieving student information
    @RequestMapping(value = "retrievestudentinformation/{studentid}", method = RequestMethod.GET)
    public ResponseEntity<?> retrieveStudentInformation(@PathVariable String studentid) throws MyException {
        if (studentid != null) {
            System.out.println("[Controller]: Retrieving information for student id" + studentid);
            StudentInformationResponseEntity studentInformationResponseEntity = webService.retrieveStudentinfo(studentid);
            System.out.println("[Controller]: Student name:" + studentInformationResponseEntity.getStudentname());
            System.out.println("[Controller]: Retrieving information--> Preparing response");
            //Converting retrieve data to Json format
            GsonBuilder builder = new GsonBuilder();
            builder.setPrettyPrinting();
            builder.serializeNulls();
            Gson gson = builder.create();
            String json = gson.toJson(studentInformationResponseEntity);
            System.out.println("[Controller]: Retrieving information--> Response sent");
            System.out.println(json);
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Type", "application/json;charset=UTF-8");
            return ResponseEntity.ok().headers(headers).contentLength(json.length()).body(json);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////Retrieve Information END/////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////Edit student info///////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @RequestMapping(value = "editstudentinformation/{newValue}/{id}/{column}", method = RequestMethod.GET)
    public ResponseEntity<?> editStudentInformation(@PathVariable String newValue, @PathVariable String id,
                                                    @PathVariable String column) throws MyException {
        System.out.println("[Controller]: EditstudentInformation--> newValue: " + newValue);
        System.out.println("[Controller]: EditstudentInformation--> student id: " + id);
        System.out.println("[Controller]: EditstudentInformation--> column: " + column);
        if (newValue != null && id != null && column != null) {
            StudentInformationResponseEntity studentInformationResponseEntity = webService.editStudentInformation(newValue, id, column);
            return ResponseEntity.ok().body(studentInformationResponseEntity);
        } else {
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
    @RequestMapping(value = "editinformationimage", method = RequestMethod.POST)
    public ResponseEntity<?> editImage(@RequestBody EditInformationImageRequestEntity editInformationImageRequestEntity) throws MyException {
        if (editInformationImageRequestEntity != null) {
            webService.EditInformationImage(editInformationImageRequestEntity);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////Edit information image ///////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //Delete Student info
    @RequestMapping(value = "deletestudent/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteStudent(@PathVariable String id) throws MyException {
        System.out.println("[Controller]: Deleting student--> Student id: " + id);
        if (id != null) {
            webService.deleteStudent(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }


    ///Retrieving studentnames
    @RequestMapping(value = "retrivenames/{classselected}/{sessionselected}")
    public ResponseEntity<?> getNames(@PathVariable String classselected, @PathVariable String sessionselected, @RequestAttribute String schoolid) throws MyException {
        if (classselected != null && sessionselected != null && schoolid != null) {
            System.out.println("[Controller]: Retrieving names--> Class: " + classselected);
            System.out.println("[Controller]: Retrieving names--> Session: " + sessionselected);
            System.out.println("[Controller]: Retrieving names--> school id: " + schoolid);
            ArrayList<RetrieveNameResponse> list = webService.RetrieveName(classselected, sessionselected, schoolid);
            GsonBuilder builder = new GsonBuilder();
            builder.serializeNulls();
            Gson gson = builder.create();
            String string = gson.toJson(list);
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add("Content-Type", "text/plain; charset=UTF-8");
            System.out.println("[Retrieving name]: response sent");
            return ResponseEntity.ok().headers(httpHeaders).contentLength(string.length()).body(string);
        } else {
            return ResponseEntity.badRequest().body("Bad request");
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
    @RequestMapping(value = "registerteacher", method = RequestMethod.POST)
    public ResponseEntity<?> registerTeacher(@RequestBody RegisterTeacherRequestEntity registerTeacherRequestEntity, @RequestAttribute String schoolid) throws MyException {
        if (registerTeacherRequestEntity != null && schoolid != null) {
            String result = webService.registerTeacher(registerTeacherRequestEntity, schoolid);
            System.out.println("controller[RegisterTeacher]: " + result);
            return ResponseEntity.ok().body(new RegisterTeacherResponse(result));
        } else {
            return ResponseEntity.badRequest().body("Bad request");
        }
    }
    ///////////////////////////////////Register Teacher End///////////////////////////////////////////


    ////Retrieve teacher information method
    @RequestMapping(value = "retrieveteacherinformation/{teacherid}", method = RequestMethod.GET)
    public ResponseEntity<?> retrieveTeacherInformation(@RequestAttribute String schoolid, @PathVariable String teacherid) throws MyException {
        //RegisterTeacherRequestEntityClass is used to fetch teacher information from database because it corresponds to the database structure
        if (schoolid != null && teacherid != null) {
            RegisterTeacherRequestEntity retrievedTeacherInformation = webService.retrieveTeacherInformation(schoolid, teacherid);
            if (retrievedTeacherInformation != null) {
                System.out.println("[Controller]-->[Retrieving teacher Information] Returning response");
                HttpHeaders headers = new HttpHeaders();
                headers.add("Content-Type", "application/json; charset=UTF-8");
                GsonBuilder builder = new GsonBuilder();
                builder.serializeNulls();
                builder.setPrettyPrinting();
                Gson gson = builder.create();
                String responseData = gson.toJson(retrievedTeacherInformation);
                System.out.println("[Controller]-->[Retrieving teacher Information] --> Teacher id: " + teacherid);
                return ResponseEntity.ok().headers(headers).body(responseData);
            } else {
                return ResponseEntity.unprocessableEntity().body("No information");
            }
        } else {
            return ResponseEntity.badRequest().body("Bad request");
        }
    }
    ////Retrieve teacher information method end


    @RequestMapping(value = "retrieveTeacherNames", method = RequestMethod.GET)
    public ResponseEntity<?> getTeachersName(@RequestAttribute String schoolid) throws MyException {
        if (schoolid != null) {
            ArrayList<TeachernamesResponseEntity> teachernamesResponseEntities = webService.getTeachersName(schoolid);
            if (!teachernamesResponseEntities.isEmpty()) {
                return ResponseEntity.ok(teachernamesResponseEntities);
            } else {
                return ResponseEntity.unprocessableEntity().body("Teacher name is empty");
            }
        } else {
            return ResponseEntity.badRequest().body("Bad request");
        }
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////// Creating session start//////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    ///////////////////////////////////////////Create Session////////////////////////////////////////
    @RequestMapping(value = "createsession", method = RequestMethod.POST)
    public ResponseEntity<SessionResponseEntity> createSession(@RequestBody SessionRequestEntity sessionRequestEntity) {
        if (sessionRequestEntity != null) {
            String result = webService.CreateSession(sessionRequestEntity);
            if (result != null) {
                System.out.println("[Controller]" + result);
                return ResponseEntity.ok(new SessionResponseEntity(result));
            } else {
                return ResponseEntity.unprocessableEntity().build();
            }
        } else {
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
    @RequestMapping(value = "getstudentscores", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getStudentScores(@RequestBody MultipartFile jsonbody, @RequestAttribute String schoolid) throws MyException {
        if (jsonbody != null && schoolid != null) {
            getStudentScoreRequestEntity getStudentScoreRequestEntity = new getStudentScoreRequestEntity();
            try {
                System.out.println("[Controller]-->Converting bytes to Json Object]");
                byte[] bytes = jsonbody.getBytes();
                String raw = new String(bytes, "UTF-8");
                GsonBuilder convert = new GsonBuilder();
                convert.setPrettyPrinting();
                convert.serializeNulls();
                Gson gson = convert.create();
                getStudentScoreRequestEntity = gson.fromJson(raw, getStudentScoreRequestEntity.class);
            } catch (IOException e) {
                e.printStackTrace();
                return ResponseEntity.unprocessableEntity().body("Unprocessable Entity");
            }
            System.out.println("[Controller-->Getting Student scores]");
            ArrayList<Scores> scores = webService.getScores(getStudentScoreRequestEntity, schoolid);
            System.out.println("[Controller-->Getting Student scores]-->Response is ok");
            GsonBuilder builder = new GsonBuilder();
            builder.setPrettyPrinting();
            builder.serializeNulls();
            Gson gson = builder.create();
            String json = gson.toJson(scores);
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Type", "application/json; charset=UTF-8");
            System.out.println("[Controller-->Getting Student scores]--> Response sent");
            return ResponseEntity.ok().headers(headers).body(json);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
    //////////////////////////END//////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////


    ////////////////////////////update Student Score/////////////////////////
    //////////////////////This method update student score///////////////////
    /////////////////////////////////////////////////////////////////////////
    @RequestMapping(value = "updatescore", method = RequestMethod.POST)
    public ResponseEntity<?> updateScore(@RequestBody MultipartFile file) throws MyException {
        if (file != null) {
            UpdateScoreRequestEntity updateScoreRequestEntity = new UpdateScoreRequestEntity();
            System.out.println("[Controller-->Updating score]--> Coverting json request");
            try {
                byte[] bytes = file.getBytes();
                String raw = new String(bytes, "UTF-8");
                GsonBuilder builder = new GsonBuilder();
                builder.setPrettyPrinting();
                builder.serializeNulls();
                Gson gson = builder.create();
                updateScoreRequestEntity = gson.fromJson(raw, UpdateScoreRequestEntity.class);
                System.out.println("[Controller-->Updating score]--> " + updateScoreRequestEntity.getId());
                Scores scores = webService.UpdateScore(updateScoreRequestEntity);
                HttpHeaders headers = new HttpHeaders();
                headers.add("Content-Type", "application/json; charset=UTF-8");
                return ResponseEntity.ok().headers(headers).body(scores);
            } catch (IOException e) {
                e.printStackTrace();
                return ResponseEntity.badRequest().build();
            }
        } else {
            return ResponseEntity.badRequest().build();
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
    @RequestMapping(value = "updatesubject", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> insertSubject(@RequestBody MultipartFile json) throws MyException {
        if (json != null) {
            UpdateSubjectRequestEntity updateSubjectRequestEntity = new UpdateSubjectRequestEntity();
            try {
                System.out.println("[Controller]:-->Converting request to bytes");
                byte[] bytes = json.getBytes();
                String raw = new String(bytes, "UTF-8");
                System.out.println("[Controller]:-->Converting bytes to json Object");
                GsonBuilder builder = new GsonBuilder();
                builder.serializeNulls();
                builder.setPrettyPrinting();
                Gson gson = builder.create();
                updateSubjectRequestEntity = gson.fromJson(raw, UpdateSubjectRequestEntity.class);
                Scores scores = webService.updateSubject(updateSubjectRequestEntity);
                return ResponseEntity.ok().body(scores);
            } catch (IOException e) {
                e.printStackTrace();
                throw new MyException("An error occur");
            }
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
    /////////////////////////////Update SUBJECT END/////////////////////////////////////////////////

    /////////////////////////////////////Insert Subject////////////////////////////////////////////
    @RequestMapping(value = "insertsubject/{subject}/{session}/{studentname}/{term}", method = RequestMethod.GET)
    public ResponseEntity<?> insertSubject(@PathVariable String subject, @PathVariable String session, @PathVariable String studentname, @PathVariable String term, @RequestAttribute String schoolid) throws MyException {
        if (subject != null && session != null && studentname != null && term != null && schoolid != null) {
            System.out.println("[Controller]-->Inserting subject");
            Scores scores = webService.insertSubject(subject, session, studentname, term, schoolid);
            return ResponseEntity.ok().body(scores);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////Insert subject END//////////////////////////////////////////////

    /////////////////////////////////////Delete SUbject//////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////
    @RequestMapping(value = "deletesubject/{id}")
    public ResponseEntity<?> deleteSubject(@PathVariable String id) throws MyException {
        System.out.println("[Controller]-->Deleting Subject: id:" + id);
        if (id != null) {
            webService.deleteSubject(id);
            return ResponseEntity.ok().build();
        } else {
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
    ///////Retrieve classes.this method returns a string of classess////
    //this method return string in json format////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////
    @RequestMapping(value = "retrieveclasses", method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> retrieveClassess(@RequestAttribute String schoolid) throws MyException {
        if (schoolid!=null){
            ArrayList<ClassModel> list = webService.retrieveClasses(schoolid);
            if (!list.isEmpty()){
                HttpHeaders headers = new HttpHeaders();
                headers.add("Content-Type", "application/json; charset=UTF-8");
                System.out.println("[Controller]: class sent");
                GsonBuilder builder = new GsonBuilder();
                builder.serializeNulls();
                builder.setPrettyPrinting();
                Gson gson = builder.create();
                String listdata = gson.toJson(list);
                System.out.println(listdata);
                return ResponseEntity.ok()
                        .headers(headers)
                        .body(listdata);
            }else{
                return ResponseEntity.unprocessableEntity().body("Failed to process data");
            }

        }else{
            return ResponseEntity.badRequest().body("Bad request");
        }

    }
    ///////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////
    /////retrieve classess end /////////////////
    ///////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////

    /////////////////////////////////////////////////////////////////////////
    //////////////////This Method retrieve Session /////////////////////
    //////////////////////////////////////////////////////////////////////////
    @RequestMapping(value = "retrievesession", method = RequestMethod.GET)
    public ResponseEntity<?> retrieveSession() throws MyException {
        Object list = webService.retrieveSession();
        String string = list.toString();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "text/plain; charset=UTF-8");
        System.out.println("[Controller]: Session sent");
        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(string.length())
                .body(string);
    }
    ///////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////
    /////retrieve  session Session end ///////////////
    ///////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////


    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////Parent information session start////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////This method get the names of the parent  ///////////////////
    ///////////it takes in the session and fetch the names from it from the database/////
    @RequestMapping(value = "retrieveparent/{session}", method = RequestMethod.GET)
    public ResponseEntity<?> getParentNames(@PathVariable String session) {
        ArrayList<RetrieveParentNameResponse> list = webService.retrieveParentname(session);
        if (list == null && list.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            GsonBuilder builder = new GsonBuilder();
            builder.serializeNulls();
            Gson gson = builder.create();
            String json = gson.toJson(list);
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add("Content-Type", "text/plain; charset=UTF-8");
            System.out.println("[Controller]-->Retrieving parent names:" + json);
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
    @RequestMapping(value = "getparentinformation/{session}/{parentname}", method = RequestMethod.GET)
    public ResponseEntity<?> getParentInformation(@PathVariable String session, @PathVariable String parentname) {
        if (session != null && parentname != null) {
            RetrieveParentInformationResponseEntity retrieveParentInformationResponseEntity = webService.getParentInfo(session, parentname);
            HttpHeaders headers = new HttpHeaders();
            if (retrieveParentInformationResponseEntity != null) {
                System.out.println("[Controller]-->Sending Response");
                headers.add("Content-Type", "application/json; charset=UTF-8");
                GsonBuilder builder = new GsonBuilder();
                builder.setPrettyPrinting();
                builder.serializeNulls();
                Gson gson = builder.create();
                String response = gson.toJson(retrieveParentInformationResponseEntity);
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.notFound().headers(headers).build();
            }
        } else {
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
    @RequestMapping(value = "getschoolfee/{clas}/{term}/{year}/{tag}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getSchoolFee(@RequestAttribute String schoolid, @PathVariable String clas, @PathVariable String term, @PathVariable String year, @PathVariable String tag) throws MyException {
        System.out.println("[Controller]:getting School fee-->\r\n class: " + clas + "\r\n term: " + term + "\r\n year: " + year + "\r\n tag: " + tag);
        if (clas != null && term != null && year != null && tag != null && schoolid != null) {
            ArrayList<getSchoolFeeResponseEntity> schoolFees = webService.getSchoolFees(clas, term, year, tag, schoolid);
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Type", "application/json; charset=UTF-8");
            System.out.println("[Controller]:getting School fee-->Response sent");
            return ResponseEntity.ok().headers(headers).body(schoolFees);
        } else {
            System.out.println("[Controller]:getting School fee-->Could not get School fees");
            return ResponseEntity.badRequest().build();
        }
    }
    ///////////////////////////////////////Getting School Fee END/////////////////////////////////////////////////////

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////Getting school fee without term/////////////////////////////////////////////////////////
    @RequestMapping(value = "getschoolfeewithoutterm/{clas}/{session}/{tag}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getSchoolFeeWithoutTerm(@PathVariable String clas, @PathVariable String session, @PathVariable String tag, @RequestAttribute String schoolid) throws MyException {
        System.out.println("[Controller]:getting School fee without term-->\r\n class: " + clas + "\r\n session: " + session + "\r\n tag: " + tag);
        if (clas != null && session != null && tag != null && schoolid != null) {
            ArrayList<getSchoolFeeResponseEntity> schoolFees = webService.getSchoolFeesWithoutTerm(clas, session, tag, schoolid);
            System.out.println(schoolFees.toString());
            if (schoolFees != null && !schoolFees.isEmpty()) {
                HttpHeaders headers = new HttpHeaders();
                headers.add("Content-Type", "application/json; charset=UTF-8");
                System.out.println("[Controller]:getting School fee without term -->Response sent");
                return ResponseEntity.ok().headers(headers).body(schoolFees);
            } else {
                System.out.println("[Controller]:getting School fee without term--->Could not get School fees:Result is null");
                return ResponseEntity.notFound().build();
            }
        } else {
            System.out.println("[Controller]:getting School fee without term--->Could not get School fees");
            return ResponseEntity.badRequest().build();
        }
    }
    ///////////////////////////////////////Getting School Fee without END/////////////////////////////////////////////////////


    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////saving term school fee/////////////////////////////////////////////////////////
    ///////This method add the term into the school fee table when the student paid

    @RequestMapping(value = "saveterm/{term}/{studentid}", method = RequestMethod.GET)
    public ResponseEntity<?> saveTerm(@PathVariable String studentid, @PathVariable String term, @RequestAttribute String schoolid) throws MyException {
        System.out.println("[Controller]:Saving term-->\r\n term: " + term + "\r\n studentid: " + studentid);
        if (studentid != null && schoolid != null && term != null) {
            getSchoolFeeResponseEntity result = webService.saveterm(studentid, term, schoolid);
            System.out.println("[Controller]: Result:" + result);
            return ResponseEntity.ok().body(result);
        } else {
            System.out.println("[Controller]: Bad request returning response");
            return ResponseEntity.badRequest().build();
        }
    }
    ///////////////////////////////////////SAVING TERM END/////////////////////////////////////////////////////


    //////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////Save student name to school fee table/////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //This method save student name to school fee table
    @RequestMapping(value = "savestudentnametoschoolfee", method = RequestMethod.POST)
    public ResponseEntity<?> saveStudentToSchoolFee(@RequestBody SaveSchoolFeeRequestEntity saveSchoolFeeRequestEntity, @RequestAttribute String schoolid) throws MyException {
        if (saveSchoolFeeRequestEntity != null && schoolid != null) {
            System.out.println("[Contoller]: Student name:" + saveSchoolFeeRequestEntity.getStudentname());
            System.out.println("[Contoller]: class:" + saveSchoolFeeRequestEntity.getClas());
            System.out.println("[Contoller]: session:" + saveSchoolFeeRequestEntity.getYear());
            System.out.println("[Contoller]: tag:" + saveSchoolFeeRequestEntity.getTag());
            System.out.println("[Contoller]: term:" + saveSchoolFeeRequestEntity.getTerm());
            System.out.println("[Contoller]:  Proceeding to web service");
            getSchoolFeeResponseEntity result = webService.saveSchoolFee(saveSchoolFeeRequestEntity, schoolid);
            return ResponseEntity.ok().body(result);
        } else {
            return ResponseEntity.badRequest().build();
        }

    }

    ////////////////////////////////////Save Student name to school fee end////////////////////////////////


    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////saving data to school fee table/////////////////////////////////////////////////////////
    ///////This method save data to schoolfee table with respect to the student name

    @RequestMapping(value = "savedatatoschoolfeetable/{studentid}/{column}/{entity}", method = RequestMethod.GET)
    public ResponseEntity<?> saveDataToSchoolFeeTable(@PathVariable String studentid, @PathVariable String column, @PathVariable String entity, @RequestAttribute String schoolid) throws MyException {
        System.out.println("[Controller]:Saving data to schoolfee table-->\r\n student id: " + studentid + "\r\n column: " + column + "\r\n entity: " + entity);
        if (studentid != null && column != null && entity != null && schoolid != null) {
            getSchoolFeeResponseEntity result = webService.saveDataToSchoolFeeTable(studentid, column, entity, schoolid);
            System.out.println("[Controller]: Result:" + result);
            return ResponseEntity.ok(result);
        } else {
            System.out.println("[Controller]: Bad request returning response");
            return ResponseEntity.badRequest().build();
        }
    }
///////////////////////////////////////SAVING TERM END/////////////////////////////////////////////////////


    @RequestMapping(value = "deleteschoolfee/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteSchoolFee(@PathVariable String id, @RequestAttribute String schoolid) throws MyException {
        System.out.println("[Controller]:Saving data to schoolfee table-->\r\n id: " + id + "\r\n schoolid:" + schoolid);
        if (id != null && schoolid != null) {
            webService.deleteSchoolFee(id, schoolid);
            System.out.println("[Controller]: Result is OK");
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().body(" Bad request");
        }
    }


    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////Getting debtors/////////////////////////////////////////////////////////
    @RequestMapping(value = "getdebtors/{clas}/{term}/{year}/{minimumfee}/{tag}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getDebtors(@PathVariable String clas, @PathVariable String term, @PathVariable String year, @PathVariable int minimumfee, @PathVariable String tag, @RequestAttribute String schoolid) throws MyException {
        System.out.println("[Controller]:getting debtors-->\r\n class: " + clas + "\r\n session: " + year + "\r\n term: " + term + "\r\n minimum: " + minimumfee + "\r\n tag:" + tag);
        if (clas != null && term != null && year != null && minimumfee != 0 && tag != null && schoolid != null) {
            //This also return list of getSchoolfeeResponseEntity because we are getting the same data as getting school fee
            ArrayList<getSchoolFeeResponseEntity> debtors = webService.getDebtors(clas, term, year, minimumfee, tag, schoolid);
            System.out.println("[Controller]:getting Debtors-->Preparing response in json format");
            GsonBuilder builder = new GsonBuilder();
            builder.serializeNulls();
            builder.setPrettyPrinting();
            Gson gson = builder.create();
            String json = gson.toJson(debtors);
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Type", "application/json; charset=UTF-8");
            return ResponseEntity.ok().headers(headers).body(json);
        } else {
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

    @RequestMapping(value = "savebook", method = RequestMethod.POST)
    public ResponseEntity<?> saveBooktoStore(@RequestBody BookEntity book, @RequestAttribute String schoolid) {
        if (book != null) {
            BookEntity result = webService.saveBook(book, schoolid);
            if (result != null) {
                System.out.println("[Controller]:Saving book-->Saving book");
                return ResponseEntity.ok().body(result);
            } else {
                return ResponseEntity.notFound().build();
            }
        } else {
            System.out.println("[Controller]:Saving book-->Saving book-->Result is false");
            return ResponseEntity.badRequest().build();
        }
    }

    //Delete book
    @RequestMapping(value = "deletebook/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteBook(@PathVariable int id) throws MyException {
        if (id != 0) {
            webService.deleteBook(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    //Find all book
    @RequestMapping(value = "findallbook", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> findAllBook(@RequestAttribute String schoolid) {
        final ArrayList<BookEntity> allBooks = webService.findAllBooks(schoolid);
        if (allBooks != null && !allBooks.isEmpty()) {
            return ResponseEntity.ok().body(allBooks);
        } else {
            System.out.println("[Controller]:Getting all books-->Result is false");
            return ResponseEntity.unprocessableEntity().body("Books are empty");
        }
    }

    //Search Book
    @RequestMapping(value = "searchbook/{bookname}/{session}/{term}", method = RequestMethod.GET)
    public ResponseEntity<?> searchBook(@PathVariable String bookname, @PathVariable String session, @PathVariable String term, @RequestAttribute String schoolid) {
        System.out.println("[Controller]:getting debtors-->\r\n bookname: " + bookname + "\r\n session: " + session + "\r\n term: " + term);
        if (!bookname.isEmpty() && !session.isEmpty() && !term.isEmpty()) {
            ArrayList<BookEntity> resultbooks = webService.searchBook(bookname, session, term, schoolid);
            if (resultbooks != null && !resultbooks.isEmpty()) {
                System.out.println("[Controller]:Searching books-->result= " + resultbooks.toString());
                return ResponseEntity.ok().body(resultbooks);
            } else {
                System.out.println("[Controller]:Searching books-->Result is false");
                return ResponseEntity.notFound().build();
            }
        } else {
            return ResponseEntity.badRequest().body("Bad request");
        }
    }

    //Sell book
    @RequestMapping(value = "sellbook/{bookid}/{buyer}/{session}", method = RequestMethod.POST)
    public ResponseEntity<?> sellBook(@RequestBody BookEntity bookEntity, @PathVariable String bookid, @PathVariable String buyer, @PathVariable String session, @RequestAttribute String schoolid) throws MyException {
        System.out.println("[Controller]:selling books-->\r\n bookid: " + bookid + "\r\n buyer: " + buyer + "\r\n session: " + session);
        if (schoolid != null && buyer != null && bookEntity != null) {
            webService.sellBook(bookid, schoolid, buyer, bookEntity, session);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    //book history
    @RequestMapping(value = "getbookhistory/{session}/{term}/{date}", method = RequestMethod.GET)
    public ResponseEntity<?> getBookHistory(@PathVariable String session, @PathVariable int term, @PathVariable String date, @RequestAttribute String schoolid) throws MyException {
        System.out.println("[Controller]:getting book history");
        if (!session.isEmpty() && term != 0 && !date.isEmpty() && schoolid != null) {
            ArrayList<BookHistory> histories = webService.getBookHistory(session, term, date, schoolid);
            GsonBuilder builder = new GsonBuilder();
            builder.serializeNulls();
            builder.setPrettyPrinting();
            Gson gson = builder.create();
            String json = gson.toJson(histories);
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Type", "application/json; charset=UTF-8");
            return ResponseEntity.ok().headers(headers).body(json);
        } else {
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
    @RequestMapping(value = "editbook", method = RequestMethod.POST)
    public ResponseEntity<?> editBook(@RequestBody EditBookRequest editBookRequest, @RequestAttribute String schoolid) throws MyException {
        if (editBookRequest != null && schoolid != null) {
            System.out.println("[Controller]:Editing book-->proceeding to web service");
            BookEntity result = webService.editBook(editBookRequest, schoolid);
            return ResponseEntity.ok().body(result);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////BOOK SESSION END////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    ////////////////////////////////////////ATTENDANCE///////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @RequestMapping(value = "getattendance/{clas}/{session}/{term}/{daytime}/{gender}/{week}")
    public ResponseEntity<?> getAttendance(@PathVariable String clas, @PathVariable String session,@PathVariable String term,@PathVariable String daytime,@PathVariable String gender,@RequestAttribute String schoolid,@PathVariable String week) {
        if (clas != null && session != null&& term!=null&&daytime!=null&&gender!=null&&schoolid!=null&&week!=null) {
            Object attendance = webService.getAttendance(clas, session,term,daytime,gender,schoolid,week);
            GsonBuilder builder = new GsonBuilder();
            builder.serializeNulls();
            builder.setPrettyPrinting();
            Gson gson = builder.create();
            String json = gson.toJson(attendance);
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Type", "application/json; charset=UTF-8");
            return ResponseEntity.ok().headers(headers).body(json);
        } else {
            return ResponseEntity.badRequest().body("Invalid request");
        }
    }
    @

//////////////////////////////////////////ATTENDANCE END////////////////////////////////////////////////////////




    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////MOBILE ENDPOINT/////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
 RequestMapping(value = "signattendance/{qrdata}")
    public ResponseEntity<?> signAttendance(@PathVariable String qrdata) throws MyException {
        //From the qrdata ,information is in this format studentid,schoolid,session,term
        if (qrdata!=null){
            //Parse the qrcode
            List<String> data= Arrays.asList(qrdata.split(","));
            if (data.size()==4){
                boolean result=webService.signAttendance(data.get(0),data.get(1),data.get(2),data.get(3));
                if (result){
                    return ResponseEntity.ok().body("Successful");
                }else{
                    return ResponseEntity.unprocessableEntity().body("Unable to sign attendance");
                }
            }else {
                return ResponseEntity.unprocessableEntity().body("Invalid Qr code");
            }

        }else {
            return  ResponseEntity.badRequest().body("Invalid request");
        }
    }
    @RequestMapping(value = "addstaff",method = RequestMethod.POST)
    public ResponseEntity<?> addStaff(@RequestBody AddStaffModel addStaffModel, @RequestAttribute String schoolid) throws MyException {
        System.out.println("Unprocessable");
        if (addStaffModel!=null&&schoolid!=null){
            int result=webService.addStaff(addStaffModel,schoolid);
            if (result==1){
                System.out.println(result);
                return ResponseEntity.ok().build();
            } else{
                return ResponseEntity.unprocessableEntity().body("Registeration failed");
            }
        }else {
            System.out.println("Unprocessable");
            return ResponseEntity.badRequest().body("Bad request");
        }

    }
    @RequestMapping(value = "retrievestaff",method = RequestMethod.GET)
    public ResponseEntity<?> getStaff(@RequestAttribute String schoolid){
        if (schoolid!=null){
            ArrayList<User> result=webService.getStaffs(schoolid);
            if (result!=null){
                System.out.println(result);
                return ResponseEntity.ok().body(result);
            } else{
                return ResponseEntity.unprocessableEntity().body("Registeration failed");
            }
        }else {
            System.out.println("Unprocessable");
            return ResponseEntity.badRequest().body("Bad request");
        }
    }
    @RequestMapping(value = "deleteStaff/{staffid}",method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteStaff(@RequestAttribute String schoolid,@PathVariable String staffid){
        System.out.println("Unprocessable");
        if (schoolid!=null&&staffid!=null){
            int result=webService.deleteStaffs(schoolid,staffid);
            if (result==1){
                System.out.println(result);
                return ResponseEntity.ok().body(result);
            } else{
                return ResponseEntity.unprocessableEntity().body("Registeration failed");
            }
        }else {
            System.out.println("Unprocessable");
            return ResponseEntity.badRequest().body("Bad request");
        }

    }

    @RequestMapping(value = "registeration",method = RequestMethod.POST)
    public ResponseEntity<?> mobileRegisteration(@RequestBody RegisterationModel registerationModel) throws MyException {
        //registerationModel here is for mobile registeration
        if (registerationModel!=null){
            int result=webService.mobileRegisteration(registerationModel);
            if (result==1){
                System.out.println(result);
                return ResponseEntity.ok().build();
            }else{
                return ResponseEntity.unprocessableEntity().body("Registeration failed");
            }
        }else {
            System.out.println("Unprocessable");
            return ResponseEntity.badRequest().body("Bad request");
        }

    }

    @RequestMapping(value = "addclass", method = RequestMethod.POST)
    public ResponseEntity<?> addClasss(@RequestBody AddClassModel clas, @RequestAttribute String schoolid) throws MyException {
        if (clas!=null&&schoolid!=null){
            boolean result=webService.addClass(clas,schoolid);
            if (result){
                return ResponseEntity.ok("Success");
            }else{
                return ResponseEntity.unprocessableEntity().body("unable to process request");
            }
        }else{
            return ResponseEntity.badRequest().body("Bad request");
        }
    }
    @RequestMapping(value = "deleteclass/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteClasss(@PathVariable String id, @RequestAttribute String schoolid) throws MyException {
        System.out.println(id);
        System.out.println(schoolid);
        if (id!=null&&schoolid!=null){
            boolean result=webService.deleteClass(id,schoolid);
            if (result){
                return ResponseEntity.ok("Success");
            }else{
                return ResponseEntity.unprocessableEntity().body("unable to process request");
            }
        }else{
            return ResponseEntity.badRequest().body("Bad request");
        }
    }

    @RequestMapping(value = "retrievesubcriptioninfo",method = RequestMethod.GET)
    public ResponseEntity<?> retrieveSubscriptionInfo(@RequestAttribute String schoolid) throws MyException, ParseException {
        System.out.println(schoolid);
        if (schoolid!=null){
            User usersubscription=webService.retrieveSubscriptionInfo(schoolid);
            if (usersubscription!=null){
                GsonBuilder builder=new GsonBuilder();
                builder.setPrettyPrinting();
                builder.serializeNulls();
                Gson gson=builder.create();
                String json=gson.toJson(usersubscription);
                return ResponseEntity.ok(json);
            } else{
                return ResponseEntity.unprocessableEntity().body("Registeration failed");
            }
        }else {
            System.out.println("Unprocessable");
            return ResponseEntity.badRequest().body("Bad request");
        }

    }
    @RequestMapping(value = "contactus",method = RequestMethod.GET)
    public ResponseEntity<?> ContactUs() throws MyException {
        Optional<Contact> contact=contactRepo.findById("1");
        contact.orElseThrow(()->new MyException("An error occured"));
        return ResponseEntity.ok(contact);
    }
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////MOBILE ENDPOINT END/////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////



    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////PayTack webhook/////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @RequestMapping( value ="subscriptioninfo", method = RequestMethod.POST)
    public ResponseEntity<?> SubcriptionInformation(@RequestHeader("X-Paystack-Signature") String token,@RequestBody String requestBody) throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException, JSONException {
        System.out.println(token);
        System.out.println(requestBody);
        if (token!=null&&requestBody!=null){

            //first you verify that this request came from paystack



            String key = "sk_test_b07ae8c4b15a9438d3c748a7386d4f0107036357"; //replace with your paystack secret_key



            String rawJson =requestBody;

            JSONObject body = new JSONObject(rawJson);

            String result = "";

            String HMAC_SHA512 = "HmacSHA512";

            String xpaystackSignature = token; //put in the request's header value for x-paystack-signature



            byte [] byteKey = key.getBytes("UTF-8");

            SecretKeySpec keySpec = new SecretKeySpec(byteKey, HMAC_SHA512);

            Mac sha512_HMAC = Mac.getInstance(HMAC_SHA512);

            sha512_HMAC.init(keySpec);

            byte [] mac_data = sha512_HMAC.

                    doFinal(body.toString().getBytes("UTF-8"));

            result = DatatypeConverter.printHexBinary(mac_data);

            if(result.toLowerCase().equals(xpaystackSignature)) {

                // you can trust the event, it came from paystack

                // respond with the http 200 response immediately before attempting to process the response

                //retrieve the request body, and deliver value to the customer

                String event=body.getString("event");
                System.out.println(event);
                if (event.equals("charge.success")) {
                    System.out.println(body.get("event"));
                    System.out.println(body.getJSONObject("data").getString("amount"));
                    System.out.println(body.getJSONObject("data").getString("created_at"));
                    System.out.println(body.getJSONObject("data").optJSONObject("authorization").getString("last4"));
                    System.out.println(body.getJSONObject("data").optJSONObject("authorization").getString("card_type"));
                    System.out.println(body.getJSONObject("data").optJSONObject("customer").getString("email"));
                    System.out.println(body.getJSONObject("data").optJSONObject("customer").getString("customer_code"));
                    System.out.println(body.getJSONObject("data").getString("reference"));

                    String amountpaid = body.getJSONObject("data").getString("amount");
                    String created_at = body.getJSONObject("data").getString("created_at");
                    String last4 = body.getJSONObject("data").optJSONObject("authorization").getString("last4");
                    String card_type = body.getJSONObject("data").optJSONObject("authorization").getString("card_type");
                    String customer_email = body.getJSONObject("data").optJSONObject("customer").getString("email");
                    String customer_code = body.getJSONObject("data").optJSONObject("customer").getString("customer_code");
                    String reference=body.getJSONObject("data").getString("reference");
                    Date paid_at=new Date(System.currentTimeMillis());
                    String next_payment_date= DateUtils.addDays(paid_at,30).toString();
                    System.out.println(next_payment_date);
                    int response = webService.updateSubscription(amountpaid, paid_at.toString(), created_at, last4, card_type, customer_code, reference, next_payment_date, customer_email);
                    System.out.println(response);
                    if (response == 1) {
                        return ResponseEntity.ok().build();
                    } else {
                        return ResponseEntity.ok().build();
                    }

                }else{
                    return ResponseEntity.ok().build();
                }
            }else{
                System.out.println("ignore");
                // this isn't from Paystack, ignore it
                return ResponseEntity.ok().build();
            }

        } else{
            return ResponseEntity.badRequest().build();
        }

    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////PayTack webhook END/////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
