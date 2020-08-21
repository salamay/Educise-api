package com.school.webapp.Repository;

import org.springframework.data.repository.CrudRepository;


import java.util.Optional;

public interface MyRepository extends CrudRepository<User,Integer> {

    Optional<User> findByusername(String username);


}
