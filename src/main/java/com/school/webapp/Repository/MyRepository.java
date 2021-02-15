package com.school.webapp.Repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;


import java.util.Optional;

public interface MyRepository extends CrudRepository<User,Integer> {

    @Query(value = "select * from user where username=?1",nativeQuery = true)
    Optional<User> findByusernameAndEmail(String username);


}
