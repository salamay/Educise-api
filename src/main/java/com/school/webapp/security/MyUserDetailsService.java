package com.school.webapp.security;

import com.school.webapp.Repository.MyRepository;
import com.school.webapp.Repository.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private MyRepository myRepository;
    //This overide method is responsible for getting users account
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user=myRepository.findByusernameAndEmail(username);
        user.orElseThrow( ()->new UsernameNotFoundException("username not found "+username) );
        System.out.println("Username: "+user.get().getUsername());
        System.out.println("Role: "+user.get().getRole());
        return user.map(MyUserDetails::new).get();
    }
}
