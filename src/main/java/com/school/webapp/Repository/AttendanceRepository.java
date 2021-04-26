package com.school.webapp.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.ArrayList;


public interface AttendanceRepository extends JpaRepository<Attendance,String> {

    @Query(value = "Select * from attendance where clas=?1 and session=?2 and term=?3 and daytime=?4 and gender=?5 and schoolid=?6 and weeknumber=?7 order by studentname and id",nativeQuery = true)
    ArrayList<Attendance> findAttendance(String clas, String session, String term, String daytime, String gender, String schoolid, String week);
    @Modifying
    @Transactional
    @Query(value = "insert into attendance(studentid,studentname,session,term,clas,gender,weekday,daytime,time,date,schoolid,weeknumber) values(?1,?2,?3,?4,?5,?6,?7,?8,?9,?10,?11,?12)",nativeQuery = true)
    int SaveAttendance(String studentid, String studentname, String session, String term, String clas, String gender, String weekday, String daytime, String time, String date,String schoolid,String weeknumber);
    @Query(value = "select * from attendance where studentid=?1 and daytime=?2 and weekday=?3 and date=?4 and session=?5 and term=?6 and clas=?7 and weeknumber=?8",nativeQuery = true)
    ArrayList<Attendance> checkAttendance(String studentid,String daytime,String weekday,String date,String session,String term,String clas,String weeknumber);
}
