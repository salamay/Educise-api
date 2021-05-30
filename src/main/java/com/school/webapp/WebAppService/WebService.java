
package com.school.webapp.WebAppService;

import com.school.webapp.BookStore.DeletBook.DeleteBook;
import com.school.webapp.BookStore.EditBook.EditBook;
import com.school.webapp.BookStore.EditBook.EditBookRequest;
import com.school.webapp.BookStore.getBookSoldHistory.getBookSoldHistory;
import com.school.webapp.RegisterTeacher.*;
import com.school.webapp.RequestModel.AddClassModel;
import com.school.webapp.RequestModel.AddStaffModel;
import com.school.webapp.RequestModel.ClassModel;
import com.school.webapp.RequestModel.RegisterationModel;
import com.school.webapp.WebAppService.Attendance.AttendanceManager;
import com.school.webapp.WebAppService.Information.EditInformation.DeleteStudent;
import com.school.webapp.WebAppService.Information.EditInformation.EditImage;
import com.school.webapp.WebAppService.Information.EditInformation.EditInformation;
import com.school.webapp.WebAppService.Information.EditInformation.EditInformationImageRequestEntity;
import com.school.webapp.Repository.*;
import com.school.webapp.BookStore.SaveBook.BookEntity;
import com.school.webapp.BookStore.SellBook.SellBook;
import com.school.webapp.WebAppService.DeleteSchoolFee.DeleteSchoolFee;
import com.school.webapp.WebAppService.Information.StudentandParent.RetrieveStudentInformation;
import com.school.webapp.WebAppService.Information.StudentandParent.StudentInformationResponseEntity;
import com.school.webapp.WebAppService.Registration.Register;
import com.school.webapp.WebAppService.Registration.RegistrationModel;
import com.school.webapp.WebAppService.RetrievNameFromSession.RetrieveName;
import com.school.webapp.WebAppService.RetrievNameFromSession.RetrieveNameResponse;
import com.school.webapp.RetrieveParentInformation.RetrieveParentInformation;
import com.school.webapp.RetrieveParentInformation.RetrieveParentInformationResponseEntity;
import com.school.webapp.RetrieveParentNames.RetrieveParentName;
import com.school.webapp.RetrieveParentNames.RetrieveParentNameResponse;
import com.school.webapp.WebAppService.RetrieveSession.AddClass;
import com.school.webapp.WebAppService.RetrieveSession.DeleteClass;
import com.school.webapp.WebAppService.RetrieveSession.RetrieveAcademicSession;
import com.school.webapp.WebAppService.RetrieveSession.RetrieveClass;
import com.school.webapp.WebAppService.SaveSchoolFee.GetSchoolFee.GetSchoolFee;
import com.school.webapp.WebAppService.SaveSchoolFee.GetSchoolFee.getSchoolFeeResponseEntity;
import com.school.webapp.WebAppService.SaveSchoolFee.GetSchoolFee.getSchoolfeeWithoutTerm;
import com.school.webapp.WebAppService.SaveSchoolFee.SaveDataSchoolFeeTable;
import com.school.webapp.WebAppService.SaveSchoolFee.SaveSchoolFee;
import com.school.webapp.WebAppService.SaveSchoolFee.SaveSchoolFeeRequestEntity;
import com.school.webapp.WebAppService.SaveSchoolFee.Term.SaveTerm;
import com.school.webapp.WebAppService.SaveSchoolFee.getDebtors.getDebtors;
import com.school.webapp.Session.CreateSession;
import com.school.webapp.Session.SessionRequestEntity;
import com.school.webapp.WebAppService.StudentScore.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;;
import java.util.*;
import java.util.UUID;

@Service
public class WebService {
    public int queryresponse;
    @Autowired
    private MyRepository myRepository;

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private AttendanceRepository attendanceRepository;
    @Autowired
    private AttendanceManager attendanceManager;

    public RetrieveStudentInformation retrieveStudentInformation;
    @Autowired
    private Register register;


//Register Student
    public String RegisterStudent(RegistrationModel registrationModel, MultipartFile studentpicture, MultipartFile fatherpicture, MultipartFile motherpicture, MultipartFile otherpicture, String schoolid) throws SQLException, MyException {
        //check subscription status
        checkSubscriptionStatus(schoolid);
        //Check verification status
        checkVerificationStatus(schoolid);
        String result;
        result=register.Register(registrationModel,studentpicture,fatherpicture,motherpicture,otherpicture,schoolid);
        System.out.println("[Service]: "+result);
       return result;
    }
     ///////////////////////////Create Session/////////////////////////////////////////////
    public String CreateSession(SessionRequestEntity sessionRequestEntity){
        return new CreateSession().ExecuteQuery(sessionRequestEntity);
    }
    ///////////////////////////CREATING SESSION END/////////////////////////////////////////

    ////////////////////////////Register Teacher////////////////////////////////////////////
    public String registerTeacher(RegisterTeacherRequestEntity registerTeacherRequestEntity, String schoolid) throws MyException {
        //check subscription status
        checkSubscriptionStatus(schoolid);
        //Check verification status
        checkVerificationStatus(schoolid);
        System.out.println("[WebService]-->[Registering teacher]-->Proceeding to Database");
        return  new RegisterTeacher().Registerteacher(registerTeacherRequestEntity,schoolid);
    }

    public RegisterTeacherRequestEntity retrieveTeacherInformation( String schoolid,String teaherid) throws MyException {
        //check subscription status
        checkSubscriptionStatus(schoolid);
        //Check verification status
        checkVerificationStatus(schoolid);
        System.out.println("[WebService]-->[Retrieving teacher Information]-->Proceeding to Database");
        return new RetrieveTeacherInformation().getTeacherInformation(schoolid,teaherid);
    }

    public ArrayList<TeachernamesResponseEntity> getTeachersName(String schoolid) throws MyException {
        //check subscription status
        checkSubscriptionStatus(schoolid);
        //Check verification status
        checkVerificationStatus(schoolid);
        System.out.println("[WebService]-->[Retrieving teacher names]-->Proceeding to Database");
        return new RetrieveTeacherName().getTeachersName(schoolid);
    }


    //////////////////////////This method get the Scores////////////////////////////////////////////////////////
    public ArrayList<Scores> getScores(getStudentScoreRequestEntity getStudentScoreRequestEntity,String schoolid) throws MyException {
        //check subscription status
        checkSubscriptionStatus(schoolid);
        //Check verification status
        checkVerificationStatus(schoolid);
        System.out.println("[WebService] getting student score--> Proceeding to Database");
        System.out.println("[WebService]-->name:"+getStudentScoreRequestEntity.getName());
        System.out.println("[WebService]--> table:"+getStudentScoreRequestEntity.getTable());
        System.out.println("[WebService]--> term:"+getStudentScoreRequestEntity.getTerm());
        System.out.println("[WebService]--> school id :"+schoolid);
        return  new getStudentScore().getScore(getStudentScoreRequestEntity,schoolid);
    }
    //update Subject
    public Scores updateSubject(UpdateSubjectRequestEntity updateSubjectRequestEntity) throws MyException {

        System.out.println("[WebService] Updating subject--> Proceeding to Database");
        return new UpdateSubject().InsertSubject(updateSubjectRequestEntity);
    }

    ////////////////////////////////////////Insert Subject//////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////
    public Scores insertSubject(String subject, String session, String studentname, String term, String schoolid) throws MyException {
        //check subscription status
        checkSubscriptionStatus(schoolid);
        //Check verification status
        checkVerificationStatus(schoolid);
        System.out.println("[WebService]-->Inserting subject-->Proceeding to Database");
         return new InsertSubject().insertSubject(subject,session,studentname,term,schoolid);
    }
    //////////////////////////////////Insert Subject END///////////////////////////////////

    ////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////update student score/////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////
    public Scores UpdateScore(UpdateScoreRequestEntity updateScoreRequestEntity) throws MyException {
        System.out.println("[WebService] updating score--> proceeding to Database");
        return new UpdateScore().updateScore(updateScoreRequestEntity);
    }
    ////////////////////////////////////END//////////////////////////////////////////////////////////////////////

////////////////////////////////////////////DELETE SUBEJECT//////////////////////////////////////////////////////
    public void deleteSubject(String id) throws MyException {
        System.out.println("[Webservice]-->Deleting Subject-->proceeding to database");
        new DeleteSubject().deleteSubject(id);
    }

    /////////////////////////////////////DELETE SUBJECT END///////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //this method retrive Student information,this take in request entity instance and pass it to the Query class
    ///////////////////////////////////////////////////////////////////////////////////////////////
    public StudentInformationResponseEntity retrieveStudentinfo(String studentid) throws MyException {
        System.out.println("[WebService] Retrieving student information--> Proceeding to Database");
        retrieveStudentInformation=new RetrieveStudentInformation();
        StudentInformationResponseEntity studentInformationResponseEntity=retrieveStudentInformation.retrieveStudentInformation(studentid);
        return studentInformationResponseEntity;
    }
    /////////////////END///////////////////////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //this method edit Student information
    ///////////////////////////////////////////////////////////////////////////////////////////////
    public StudentInformationResponseEntity editStudentInformation(String newValue, String id, String column) throws MyException {
        System.out.println("[Webservice]:Editing student information-->Proceeding to database");
       return new EditInformation().edit(newValue,id,column);
    }
    /////////////////END///////////////////////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //this method edit information images
    ///////////////////////////////////////////////////////////////////////////////////////////////
    public void EditInformationImage(EditInformationImageRequestEntity editInformationImageRequestEntity) throws MyException {
        System.out.println("[Webservice]:Editing information images-->Proceeding to database");
        new EditImage().editImages(editInformationImageRequestEntity);
    }
    /////////////////END///////////////////////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //this method delete student from database
    ///////////////////////////////////////////////////////////////////////////////////////////////
    public void deleteStudent(String id) throws MyException {
        System.out.println("[Webservice]:deleting student information-->Proceeding to database");
        new DeleteStudent().delete(id);
    }
    /////////////////END///////////////////////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////////////////////////////////////////
    ///This method receive information sessions
    ////////////////////////////////////////////////////////////////////////////////////////////////
    public ArrayList<ClassModel> retrieveClasses(String schoolid) throws MyException {
        //check subscription status
        checkSubscriptionStatus(schoolid);
        //Check verification status
        checkVerificationStatus(schoolid);
        System.out.println("[WebService] Retrieving Classess--> Proceeding to Database");
            ArrayList<ClassModel> classList=new RetrieveClass().retrieve(schoolid);
                System.out.println("[WebService]: classes retrieved");
                System.out.println("[WebService]: "+classList);
                return classList;
    }
    ////////////////////////END/////////////////////////////////////////////////////////////////////////
    public boolean addClass(AddClassModel clas, String schoolid) throws MyException {
        //check subscription status
        checkSubscriptionStatus(schoolid);
        //Check verification status
        checkVerificationStatus(schoolid);
        return new AddClass().add(clas,schoolid);
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////This method Retrieve score session/////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public Object retrieveSession() throws MyException {
        System.out.println("[WebService] Retrieving session--> Proceeding to Database");
        ArrayList<String> sessionList=new RetrieveAcademicSession().retrieve();
                System.out.println("[Retrieving session]: session retrieved");
                System.out.println("[Retrieving session]: "+sessionList);
                return sessionList;
    }
    ////////////////////////////////////////END/////////////////////////////////////////////////////////


    //////////////////////////////////////////////////////////////////////////////////////////////////
    ///THis method Retrieve List of names from class
    ////////////////////////////////////////////////////////////////////////////////////////////////////////
    public ArrayList<RetrieveNameResponse> RetrieveName(String classname, String sessionselected, String schoolid) throws MyException {
        //check subscription status
        checkSubscriptionStatus(schoolid);
        //Check verification status
        checkVerificationStatus(schoolid);
        System.out.println("[WebService] Retrieving student names--> Proceeding to Database");
        ArrayList<RetrieveNameResponse> list=new RetrieveName().retrieveName(classname,sessionselected,schoolid);
        return list;
    }
    //////////////////////////////////////END/////////////////////////////////////////////////////////////////

////////////////////////This method retrieve Parent name/////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////
    public ArrayList<RetrieveParentNameResponse> retrieveParentname( String session){
        System.out.println("[WebService] Retrieving parent names--> Proceeding to Database");
        System.out.println("[WebService]-->Retrieving parent names: "+session);
        ArrayList<RetrieveParentNameResponse> list=new RetrieveParentName().getParentNames(session);
        if (list!=null){
            return list;
        }else {
            return null;
        }
    }
    ///////////////////////////////////END//////////////////////////////////////////////////////////////////////
    public RetrieveParentInformationResponseEntity getParentInfo(String session,String parentname){
        System.out.println("[WebService] getting parent information--> Proceeding to Database");
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

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////SCHOOL FEE SESSION//////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////



    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////Getting school fee///////////////////////////////////////////////////////////
    public ArrayList<getSchoolFeeResponseEntity> getSchoolFees(String clas, String term, String year, String tag, String schoolid) throws MyException {
        //check subscription status
        checkSubscriptionStatus(schoolid);
        //Check verification status
        checkVerificationStatus(schoolid);
        System.out.println("[Webservice]:getting school fee-->Proceeding to database");
        return new GetSchoolFee().getFee(clas,term,year,tag,schoolid);
    }

    public ArrayList<getSchoolFeeResponseEntity> getSchoolFeesWithoutTerm(String clas, String session, String tag, String schoolid) throws MyException {
        //check subscription status
        checkSubscriptionStatus(schoolid);
        System.out.println("[Webservice]:getting school fee without term-->Proceeding to database");
        return new getSchoolfeeWithoutTerm().getSchoolFeeWithoutTermFiltering(clas,session,tag,schoolid);
    }

    /////////////////////////////////////////////Save term to schoolfee table////////////////////////////////////////////////////
    public getSchoolFeeResponseEntity saveterm(String studentid, String term, String schoolid) throws MyException {
        //check subscription status
        checkSubscriptionStatus(schoolid);
        //Check verification status
        checkVerificationStatus(schoolid);
        System.out.println("[Webservice]:Saving term-->Proceeding to database");
        return new SaveTerm().Save(studentid,term,schoolid);
    }

    public getSchoolFeeResponseEntity saveSchoolFee(SaveSchoolFeeRequestEntity saveSchoolFeeRequestEntity, String schoolid) throws MyException {
        //check subscription status
        checkSubscriptionStatus(schoolid);
        //Check verification status
        checkVerificationStatus(schoolid);
        System.out.println("[Webservice]:  Proceeding to Database");
        return new SaveSchoolFee().saveStudentnameToSchoolFee(saveSchoolFeeRequestEntity,schoolid);
    }

    public getSchoolFeeResponseEntity saveDataToSchoolFeeTable(String studentid, String column, String entity, String schoolid) throws MyException {
        //check subscription status
        checkSubscriptionStatus(schoolid);
        System.out.println("[Webservice]:Saving data to schoolfee table-->Proceeding to database");
        return new SaveDataSchoolFeeTable().saveDataToTable(studentid,column,entity,schoolid);
    }

    public boolean deleteSchoolFee(String id, String schoolid) throws MyException {
        //check subscription status
        checkSubscriptionStatus(schoolid);
        System.out.println("[Webservice]:Deleting schoolfee from schoolfee table-->Proceeding to database");
        return new DeleteSchoolFee().deleteSchoolFee(id,schoolid);
    }

    ///////////////////////////////////SAVE TERM TO SCHOOLFEE TABLE END///////////////////////////////////////////////////////////

   public ArrayList<getSchoolFeeResponseEntity> getDebtors(String clas, String term, String year, int minimumfee, String tag, String schoolid) throws MyException {
       //check subscription status
       checkSubscriptionStatus(schoolid);
       //Check verification status
       checkVerificationStatus(schoolid);
        System.out.println("[Webservice]:getting debtors-->Proceeding to database");
        return new getDebtors().getDebtorsList(clas,term,year,minimumfee,tag,schoolid);
    }
    //////////////////////////////////////Getting school fee end/////////////////////////////////////////////////////

    ////////////////////////////////////////////////SCHOOL FEE SESSION END////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////BOOK SESSION///////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //Save book
    public BookEntity saveBook(BookEntity book, String schoolid) throws MyException {
        //check subscription status
        checkSubscriptionStatus(schoolid);
        //Check verification status
        checkVerificationStatus(schoolid);
        System.out.println("[WebService]:saving books-->Proceeding to database");
        //This set the schoolid
        book.setSchoolid(schoolid);
        //This generate an id
        String id= UUID.randomUUID().toString();
        book.setId(id);
        BookEntity entity=bookRepository.save(book);
        return entity;
    }
    //Delete book
    public void deleteBook(int id) throws MyException {
        System.out.println("[WebService]:deleting book-->Proceeding to database");
        new DeleteBook().delete(id);
    }
    //Find all books
    public ArrayList<BookEntity> findAllBooks(String schoolid) throws MyException {
        //check subscription status
        checkSubscriptionStatus(schoolid);
        //Check verification status
        checkVerificationStatus(schoolid);
        System.out.println("[WebService]:finding books-->Proceeding to database");
        ArrayList<BookEntity> books=new ArrayList<>();
        bookRepository.getAllBook(schoolid).forEach(books::add);
        return books;
    }
    //search book
    public ArrayList<BookEntity> searchBook(String bookname, String session, String term,String schoolid) throws MyException {
        //check subscription status
        checkSubscriptionStatus(schoolid);
        //Check verification status
        checkVerificationStatus(schoolid);
        System.out.println("[Controller]:Searching books-->\r\n bookname: "+bookname+"\r\n session: "+session+"\r\n term: "+term);
        System.out.println("[WebService]:Searching books-->Proceeding to database");
        ArrayList<BookEntity> books=new ArrayList<>();
        bookRepository.findByNameAndSessionAndTerm(bookname,session,term,schoolid).forEach(books::add);
        return books;
    }
    //sell book
    public void sellBook(String bookid ,String schoolid, String buyer, BookEntity bookEntity,String session) throws MyException {
        //check subscription status
        checkSubscriptionStatus(schoolid);
        //Check verification status
        checkVerificationStatus(schoolid);
        new SellBook().SellBook(bookid,schoolid,buyer,bookEntity,session);
    }
    //get booksold history
    public ArrayList<BookHistory> getBookHistory(String session, int term, String date, String schoolid) throws MyException {
        System.out.println("[WebService]:getting book sold history -->Proceeding to database");
        return new getBookSoldHistory().getHistories(session,term,date,schoolid);
    }
//    //deleting booksold history
//    public boolean deleteBookHistory(String id) {
//        System.out.println("[WebService]:deleting book sold history -->Proceeding to database");
//        return new DeleteBookHistory().deleteHistory(id);
//    }

    //Edit book
    public BookEntity editBook(EditBookRequest editBookRequest, String schoolid) throws MyException {
        //check subscription status
        checkSubscriptionStatus(schoolid);
        //Check verification status
        checkVerificationStatus(schoolid);
        System.out.println("[WebService]:Editing book-->Proceeding to database");
        return new EditBook().edit(editBookRequest,schoolid);
    }


///////////////////////////////////////////////BOOK SESSION END//////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    ////////////////////////////////////////////////////////ATTENDANCE/////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public Object getAttendance(String clas, String session, String term,String daytime,String gender,String schoolid,String week) throws MyException {
        //check subscription status
        checkSubscriptionStatus(schoolid);
        //Check verification status
        checkVerificationStatus(schoolid);
        ArrayList<Attendance> attendance=attendanceRepository.findAttendance(clas,session,term,daytime,gender,schoolid,week);
        return attendance;
    }

    public boolean signAttendance(String studentid,String schoolid,String session,String term) throws MyException {
        //check subscription status
        checkSubscriptionStatus(schoolid);
        //Check verification status
        checkVerificationStatus(schoolid);
        return  attendanceManager.signAttendanceForAStudent(studentid,schoolid,session,term);
    }


    ////////////////////////////////////////////////ATTENDANCE END/////////////////////////////////////////////////


///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////MOBILE ENDPOINT/////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
public int mobileRegisteration(RegisterationModel registerationModel) throws MyException {
        //check if email exist
    checkIfEmailExist(registerationModel.getEmail());
        //check if school id already exist
        checkIfSchoolidAlreadyExist(registerationModel.getSchoolid());

        //check if username already exist
        checkIfUsernameExist(registerationModel.getStaffid());
    String uuid=UUID.randomUUID().toString();
    String role="ROLE_ADMIN";
    int lockedstatus=1;
    int active=1;
    //User have to pay an amount that corresponds to the number of student
    int amounttopay=Integer.parseInt(registerationModel.getNumberofstudent())*1000;

    return myRepository.registerUser(uuid,registerationModel.getStaffid(),registerationModel.getPassword(),registerationModel.getSchoolid(),role,lockedstatus,registerationModel.getEmail(),String.valueOf(amounttopay),"0");

}

    public boolean deleteClass(String id, String schoolid) throws MyException {
        //check subscription status
        checkSubscriptionStatus(schoolid);
        return new DeleteClass().deleteClass(id,schoolid);
    }

    public ArrayList<User> getStaffs(String schoolid) throws MyException {
        //check subscription status
        checkSubscriptionStatus(schoolid);
        return myRepository.getStaffs(schoolid);
    }

    public int deleteStaffs(String schoolid, String staffid) throws MyException {
        //check subscription status
        checkSubscriptionStatus(schoolid);
        //Check verification status
        checkVerificationStatus(schoolid);
        return myRepository.deleteStaffs(staffid,schoolid);
    }

    public int addStaff(AddStaffModel addStaffModel, String schoolid) throws MyException {
        //check subscription status
        checkSubscriptionStatus(schoolid);
        //Check verification status
        checkVerificationStatus(schoolid);
        //check if username already exist
        checkIfUsernameExist(addStaffModel.getUsername());
        return myRepository.addUser(UUID.randomUUID().toString(),addStaffModel.getUsername(),addStaffModel.getPassword(),schoolid,addStaffModel.getRole(),1);
    }

    public void checkIfSchoolidAlreadyExist(String schoolid) throws MyException {
        ArrayList<User> result=myRepository.checkForSchoolId(schoolid);
        if (result.isEmpty()){

        }else {
            throw new MyException("School id is taken, choose another school id");
        }
    }
    public void checkIfUsernameExist(String username) throws MyException {
        ArrayList<User> result=myRepository.checkForUsername(username);
        if (result.isEmpty()){


        }else {
            throw new MyException("Staff id is taken, choose another username");
        }
    }
    public void checkIfEmailExist(String email) throws MyException {
        ArrayList<User> result=myRepository.checkForEmail(email);
        if (result.isEmpty()){

        }else {
            throw new MyException("Email is taken, choose another email");
        }
    }


///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////MOBILE ENDPOINT END/////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////



    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////Webhook start/////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public int updateSubscription(String amountpaid, String paid_at, String created_at, String last4, String card_type, String customer_code, String reference, String next_payment_date,String email) {
        return myRepository.chargeSuccess(amountpaid,paid_at,created_at,last4,card_type,customer_code,reference,next_payment_date,email);
    }

    public User retrieveSubscriptionInfo(String schoolid) throws MyException, ParseException {
        User user=myRepository.getCustomerSubcriptionInfo(schoolid,"ROLE_ADMIN");

        if (user != null) {
            if(user.getNext_payment_date()!=null||user.getPaid_at()!=null){
                System.out.println(user.getNext_payment_date());
                System.out.println(user.getPaid_at());
                String next_payment_date=user.getNext_payment_date();
                String paid_at=user.getPaid_at();

                DateFormat formatter = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
                Date next = formatter.parse(next_payment_date);
                Date paid=formatter.parse(paid_at);

                if (next.after(paid)){
                    user.setSubscription_status("active");
                }else{
                    user.setSubscription_status("not active");
                }
            }else{

            }
            return user;
        }else{
            throw new MyException(null);
        }

    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////Webhook end/////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    //Check for subscription status

    public void checkSubscriptionStatus(String schoolid) throws MyException {
        User user=myRepository.checkSubscription(schoolid,"ROLE_ADMIN");
        if (user.getSubscription_status()!=null){
            if(user.getSubscription_status().equals("active")){

            }else{
                throw new MyException("Your subscription has expired");
            }
        }else{
            throw new MyException("Your subscription has expired");
        }

    }
    public void checkVerificationStatus(String schoolid) throws MyException {
        User user=myRepository.checkVerification(schoolid,"ROLE_ADMIN");
        System.out.println(user.getSchoolid());
        if (user.getVerification()!=null){
            if(user.getVerification().equals("1")){

            }else{
                throw new MyException("Verify your account");
            }
        }else{
            throw new MyException("Verify your account");
        }

    }


}


