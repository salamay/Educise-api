package com.school.webapp.WebAppService;

import com.school.webapp.BookStore.DeletBook.DeleteBook;
import com.school.webapp.BookStore.DeleteBookHistory.DeleteBookHistory;
import com.school.webapp.BookStore.EditBook.EditBook;
import com.school.webapp.BookStore.EditBook.EditBookRequest;
import com.school.webapp.BookStore.getBookSoldHistory.getBookSoldHistory;
import com.school.webapp.Information.EditInformation.DeleteStudent;
import com.school.webapp.Information.EditInformation.EditImage;
import com.school.webapp.Information.EditInformation.EditInformation;
import com.school.webapp.Information.EditInformation.EditInformationImageRequestEntity;
import com.school.webapp.Repository.BookHistory;
import com.school.webapp.Repository.BookRepository;
import com.school.webapp.BookStore.SaveBook.BookEntity;
import com.school.webapp.BookStore.SellBook.SellBook;
import com.school.webapp.DeleteSchoolFee.DeleteSchoolFee;
import com.school.webapp.Information.StudentandParent.RetrieveStudentInformation;
import com.school.webapp.Information.StudentandParent.StudentInformationResponseEntity;
import com.school.webapp.RegisterTeacher.RegisterTeacher;
import com.school.webapp.RegisterTeacher.RegisterTeacherRequestEntity;
import com.school.webapp.Registration.Register;
import com.school.webapp.Registration.RegistrationModel;
import com.school.webapp.Repository.MyRepository;
import com.school.webapp.RetrievNameFromSession.RetrieveName;
import com.school.webapp.RetrievNameFromSession.RetrieveNameResponse;
import com.school.webapp.RetrieveParentInformation.RetrieveParentInformation;
import com.school.webapp.RetrieveParentInformation.RetrieveParentInformationResponseEntity;
import com.school.webapp.RetrieveParentNames.RetrieveParentName;
import com.school.webapp.RetrieveParentNames.RetrieveParentNameResponse;
import com.school.webapp.RetrieveSession.RetrieveScoreSession;
import com.school.webapp.RetrieveSession.RetrieveSession;
import com.school.webapp.SchoolFee.SaveSchoolFee.GetSchoolFee.GetSchoolFee;
import com.school.webapp.SchoolFee.SaveSchoolFee.GetSchoolFee.getSchoolFeeResponseEntity;
import com.school.webapp.SchoolFee.SaveSchoolFee.GetSchoolFee.getSchoolfeeWithoutTerm;
import com.school.webapp.SchoolFee.SaveSchoolFee.SaveDataSchoolFeeTable;
import com.school.webapp.SchoolFee.SaveSchoolFee.Term.SaveTerm;
import com.school.webapp.SchoolFee.SaveSchoolFee.getDebtors.getDebtors;
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

    @Autowired
    private BookRepository bookRepository;

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
        System.out.println("[WebService]-->name:"+getStudentScoreRequestEntity.getName());
        System.out.println("[WebService]--> table:"+getStudentScoreRequestEntity.getTable());
        System.out.println("[WebService]--> term:"+getStudentScoreRequestEntity.getTerm());
        return  new getStudentScore().getScore(getStudentScoreRequestEntity);
    }
    //update Subject
    public String updateSubject(UpdateSubjectRequestEntity updateSubjectRequestEntity){
        return new UpdateSubject().InsertSubject(updateSubjectRequestEntity);
    }

    ////////////////////////////////////////Insert Subject//////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////
    public String insertSubject(String subject, String session, String studentname, String term){
        System.out.println("[WebService]-->Inserting subject-->Proceeding to Database");
        return new InsertSubject().insertSubject(subject,session,studentname,term);
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
    public boolean deleteSubject(String subject, String session, String studentname, String term) {
        System.out.println("[Webservice]-->Deleting Subject-->proceeding to database");
        return new DeleteSubject().deleteSubject(subject,session,studentname,term);
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
    //this method edit Student information
    ///////////////////////////////////////////////////////////////////////////////////////////////
    public boolean editStudentInformation(String newValue, String id, String column, String session) {
        System.out.println("[Webservice]:Editing student information-->Proceeding to database");
        return new EditInformation().edit(newValue,id,column,session);
    }
    /////////////////END///////////////////////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //this method edit information images
    ///////////////////////////////////////////////////////////////////////////////////////////////
    public boolean EditInformationImage(EditInformationImageRequestEntity editInformationImageRequestEntity) {
        System.out.println("[Webservice]:Editing information images-->Proceeding to database");
        return new EditImage().editImages(editInformationImageRequestEntity);
    }
    /////////////////END///////////////////////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //this method delete student from database
    ///////////////////////////////////////////////////////////////////////////////////////////////
    public boolean deleteStudent(int id, String session) {
        System.out.println("[Webservice]:deleting student information-->Proceeding to database");
        return new DeleteStudent().delete(id,session);
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
    public ArrayList<RetrieveNameResponse> RetrieveName( String classname){
        ArrayList<RetrieveNameResponse> list=new RetrieveName().retrieveName(classname);
        if (list!=null){
            return list;
        }else {
            return null;
        }
    }
    //////////////////////////////////////END/////////////////////////////////////////////////////////////////

////////////////////////This method retrieve Parent name/////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////
    public ArrayList<RetrieveParentNameResponse> retrieveParentname( String session){
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
    public ArrayList<getSchoolFeeResponseEntity> getSchoolFees(String clas, String term, String year) {
        System.out.println("[Webservice]:getting school fee-->Proceeding to database");
        return new GetSchoolFee().getFee(clas,term,year);
    }

    public ArrayList<getSchoolFeeResponseEntity> getSchoolFeesWithoutTerm(String clas, String session,String tag) {
        System.out.println("[Webservice]:getting school fee without term-->Proceeding to database");
        return new getSchoolfeeWithoutTerm().getSchoolFeeWithoutTermFiltering(clas,session,tag);
    }

    /////////////////////////////////////////////Save term to schoolfee table////////////////////////////////////////////////////
    public boolean saveterm(String studentname, String clas, String session, String tag, String term) {
        System.out.println("[Webservice]:Saving term-->Proceeding to database");
        return new SaveTerm().Save(studentname,clas,session,tag,term);
    }

    public boolean saveDataToSchoolFeeTable(String studentname, String clas, String session, String tag, String term, String column, String entity) {
        System.out.println("[Webservice]:Saving data to schoolfee table-->Proceeding to database");
        return new SaveDataSchoolFeeTable().saveDataToTable(studentname,clas,session,tag,term,column,entity);
    }

    public boolean deleteSchoolFee(String clas, String session, String term, String studentname) {
        System.out.println("[Webservice]:Deleting schoolfee from schoolfee table-->Proceeding to database");
        return new DeleteSchoolFee().deleteSchoolFee(clas,session,term,studentname);
    }

    ///////////////////////////////////SAVE TERM TO SCHOOLFEE TABLE END///////////////////////////////////////////////////////////

   public ArrayList<getSchoolFeeResponseEntity> getDebtors(String clas, String term, String year, int minimumfee, String tag) {
        System.out.println("[Webservice]:getting debtors-->Proceeding to database");
        return new getDebtors().getDebtorsList(clas,term,year,minimumfee,tag);
    }
    //////////////////////////////////////Getting school fee end/////////////////////////////////////////////////////

    ////////////////////////////////////////////////SCHOOL FEE SESSION END////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////BOOK SESSION///////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //Save book
    public boolean saveBook(BookEntity book) {
        System.out.println("[WebService]:saving books-->Proceeding to database");
        bookRepository.save(book);
        return true;
    }
    //Delete book
    public boolean deleteBook(int id) {
        System.out.println("[WebService]:deleting book-->Proceeding to database");
        return new DeleteBook().delete(id);
    }
    //Find all books
    public ArrayList<BookEntity> findAllBooks() {
        System.out.println("[WebService]:finding books-->Proceeding to database");
        ArrayList<BookEntity> books=new ArrayList<>();
        bookRepository.findAll().forEach(books::add);
        return books;
    }
    //search book
    public ArrayList<BookEntity> searchBook(String bookname, String session, String term) {
        System.out.println("[Controller]:Searching books-->\r\n bookname: "+bookname+"\r\n session: "+session+"\r\n term: "+term);
        System.out.println("[WebService]:Searching books-->Proceeding to database");
        ArrayList<BookEntity> books=new ArrayList<>();
        bookRepository.findByNameAndSessionAndTerm(bookname,session,term).forEach(books::add);
        return books;
    }
    //sell book
    public boolean sellBook(String bookname, String term, String session, String buyer, BookEntity bookEntity) {
        System.out.println("[webService]:Selling books-->\r\n bookname: "+bookname+"\r\n session: "+session+"\r\n term: "+term+"\r\n buyer: "+buyer);
        return  new SellBook().SellBook(bookname,term,session,buyer,bookEntity);
    }
    //get booksold history
    public ArrayList<BookHistory> getBookHistory(String session, int term, String date) {
        System.out.println("[WebService]:getting book sold history -->Proceeding to database");
        return new getBookSoldHistory().getHistories(session,term,date);
    }
    //deleting booksold history
    public boolean deleteBookHistory(String id) {
        System.out.println("[WebService]:deleting book sold history -->Proceeding to database");
        return new DeleteBookHistory().deleteHistory(id);
    }

    //Edit book
    public boolean editBook(EditBookRequest editBookRequest) {
        System.out.println("[WebService]:Editing book-->Proceeding to database");
        return new EditBook().edit(editBookRequest);
    }




///////////////////////////////////////////////BOOK SESSION END//////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
