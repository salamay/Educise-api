package com.school.webapp.Repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;


import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Optional;

public interface MyRepository extends CrudRepository<User,Integer> {

    @Query(value = "select * from user where username=?1 and schoolid=?2",nativeQuery = true)
    Optional<User>findByusernameAndEmail(String username,String schoolid);

    @Transactional
    @Modifying
    @Query(value = "insert into user (id,username,password,schoolid,role,locked_status,email,amount,verification) values(?1,?2,?3,?4,?5,?6,?7,?8,?9)",nativeQuery = true)
    int registerUser(String id, String username, String password, String schoolid, String role, int lockedstatus,String email,String amount,String verification);


    @Query(value = "select * from user where schoolid=?1",nativeQuery = true)
    ArrayList<User> checkForSchoolId(String schoolid);

    @Query(value = "select * from user where username=?1",nativeQuery = true)
    ArrayList<User> checkForUsername(String username);

    @Query(value = "select * from user where email=?1",nativeQuery = true)
    ArrayList<User> checkForEmail(String username);

    @Transactional
    @Modifying
    @Query(value = "insert into user(id,username,password,schoolid,role,locked_status) values(?1,?2,?3,?4,?5,?6)",nativeQuery = true)
    int addUser(String id, String username, String password, String schoolid, String role, int lockedstatus);

    @Transactional
    @Modifying
    @Query(value = "select * from user where schoolid=?1 order by role",nativeQuery = true)
    ArrayList<User> getStaffs(String schoolid);

    @Transactional
    @Modifying
    @Query(value = "delete from user where id=?1 and schoolid=?2",nativeQuery = true)
    int deleteStaffs(String id,String schoolid);

    @Modifying
    @Transactional
    @Query(value = "update user set amount=?1,paid_at=?2,created_at=?3,last4=?4,card_type=?5,customer_code=?6,reference=?7,next_payment_date=?8 where email=?9",nativeQuery = true)
    int chargeSuccess(String amountpaid, String paid_at, String created_at, String last4, String card_type, String customer_code, String reference,String next_payment_date,String email);

    //get subcription info only for admin
    @Query(value = "select * from user where schoolid=?1 and role=?2",nativeQuery = true)
    User getCustomerSubcriptionInfo(String schoolid,String role);

    @Modifying
    @Transactional
    @Query(value = "update user set verification=1 where schoolid=?1",nativeQuery = true)
    int verifyAccount(String schoolid);


    @Query(value = "select * from user where schoolid=?1 and role=?2",nativeQuery = true)
    User checkSubscription(String schoolid,String role);

    @Query(value = "select * from user where schoolid=?1 and role=?2",nativeQuery = true)
    User checkVerification(String schoolid,String role);
}
