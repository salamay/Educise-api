package com.school.webapp.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
public class ApiSecurity extends WebSecurityConfigurerAdapter {

    @Autowired
    private MyUserDetailsService myUserDetailsService;

    @Autowired
    private SecurityFilter SecurityFilter;


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(myUserDetailsService);

    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests()
                .antMatchers("/authenticate").permitAll()
                .antMatchers("/hello").permitAll().
                antMatchers("/registeration").permitAll()
                .antMatchers("/retrievestaff").hasAnyRole("TEACHER","BURSARY","ADMIN")
                .antMatchers("/addstaff").hasAnyRole("ADMIN")
                .antMatchers("/addclass").hasAnyRole("ADMIN")
                .antMatchers("/register").hasAnyRole("TEACHER","ADMIN")
                .antMatchers("/retrievestudentinformation/**").hasAnyRole("BURSARY","ADMIN","TEACHER")
                .antMatchers("/signattendance/**").hasAnyRole("BURSARY","ADMIN","TEACHER")
                .antMatchers("/deleteStaff/**").hasAnyRole("ADMIN")
                .antMatchers("/deleteclass/**").hasAnyRole("ADMIN")
                .antMatchers("/editstudentinformation/**").hasRole("TEACHER")
                .antMatchers("/editinformationimage/**").hasRole("TEACHER")
                .antMatchers("/deletestudent/**").hasRole("TEACHER")
                .antMatchers("/retrivenames/**").hasAnyRole("BURSARY","ADMIN","TEACHER")
                .antMatchers("/registerteacher/**").hasAnyRole("BURSARY","ADMIN","TEACHER")
                .antMatchers("/createsession/").hasRole("ADMIN")
                .antMatchers("/getstudentscores/**").hasAnyRole("TEACHER","ADMIN")
                .antMatchers("/updatescore/**").hasRole("TEACHER")
                .antMatchers("/updatesubject/**").hasRole("TEACHER")
                .antMatchers("/insertsubject/**").hasRole("TEACHER")
                .antMatchers("/deletesubject/**").hasRole("TEACHER")
                .antMatchers("/retrieveclasses/**").hasAnyRole("BURSARY","ADMIN","TEACHER")
                .antMatchers("/retrievesession/**").hasAnyRole("BURSARY","ADMIN","TEACHER")
                .antMatchers("/retrieveparent/**").hasAnyRole("BURSARY","ADMIN","TEACHER")
                .antMatchers("/getparentinformation/**").hasAnyRole("BURSARY","ADMIN","TEACHER")
                .antMatchers("/getschoolfee/**").hasAnyRole("BURSARY","ADMIN")
                .antMatchers("/getschoolfeewithoutterm/**").hasAnyRole("BURSARY","ADMIN","TEACHER")
                .antMatchers("/saveterm/**").hasRole("BURSARY")
                .antMatchers("/savedatatoschoolfeetable/**").hasRole("BURSARY")
                .antMatchers("/savestudentnametoschoolfee/**").hasRole("BURSARY")
                .antMatchers("/deleteschoolfee/**").hasRole("BURSARY")
                .antMatchers("/getdebtors/**").hasAnyRole("BURSARY","ADMIN")
                .antMatchers("/savebook/**").hasRole("BURSARY")
                .antMatchers("/deletebook/**").hasRole("BURSARY")
                .antMatchers("/findallbook/**").hasAnyRole("BURSARY","ADMIN")
                .antMatchers("/searchbook/**").hasAnyRole("BURSARY","ADMIN")
                .antMatchers("/sellbook/**").hasRole("BURSARY")
                .antMatchers("/subscriptioninfo").hasIpAddress("52.31.139.75")
                .antMatchers("/subscriptioninfo").hasIpAddress("52.49.173.169")
                .antMatchers("/subscriptioninfo").hasIpAddress("52.214.14.220")
                .antMatchers("/getbookhistory/**").hasAnyRole("BURSARY","ADMIN")
                .antMatchers("/editbook/**").hasRole("BURSARY")
                .anyRequest().authenticated().and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(SecurityFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    public PasswordEncoder getPwdEncoder(){
        return NoOpPasswordEncoder.getInstance();
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
