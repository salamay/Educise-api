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
    @Query(value = "insert into user (id,username,password,schoolid,role,locked_status,email) values(?1,?2,?3,?4,?5,?6,?7)",nativeQuery = true)
    int registerUser(String id, String username, String password, String schoolid, String role, int lockedstatus,String email);


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


}
