package com.school.webapp.security;

import com.school.webapp.Repository.MyRepository;
import com.school.webapp.Repository.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private MyRepository myRepository;
    //This overide method is responsible for getting users account
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //The staff id and school id is gotten from the username variable,in order to prevent
        //user from tampering with another user's data
        List<String> staffid= Arrays.asList(username.split(","));
        Optional<User> user=myRepository.findByusernameAndEmail(staffid.get(0),staffid.get(1));
        user.orElseThrow( ()->new UsernameNotFoundException("username not found "+username) );
        System.out.println("Username: "+user.get().getUsername());
        System.out.println("Role: "+user.get().getRole());
        return user.map(MyUserDetails::new).get();
    }
}
