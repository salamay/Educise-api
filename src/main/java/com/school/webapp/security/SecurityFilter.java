package com.school.webapp.security;

import com.school.webapp.ResponseModel.JwtUtils;
import com.school.webapp.WebAppService.MyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    public static String username;
    public static String jwt;
    public static String schoolid;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private MyUserDetailsService myUserDetailsService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //The staffid in authenticateRequest is in this format username,schoolid,it is then split to get the
        // username and schoolid, the schoolid is used as part of token payload for subsequent request
        System.out.println(request.getRemoteAddr()+" Making request");
        final String authorizationHeader=request.getHeader("Authorization");
        username=null;
         jwt=null;
        if (authorizationHeader!=null && authorizationHeader.startsWith("Bearer ")){
            jwt=authorizationHeader.substring(7);
            username=jwtUtils.extractUsername(jwt);
            schoolid=jwtUtils.extractSchoolEmail(jwt);

        }

        if (username!=null && SecurityContextHolder.getContext().getAuthentication()==null){
            //Username is appended with thw comma and school id to make thing go as expected in the userdetails service
            UserDetails userDetails= myUserDetailsService.loadUserByUsername(username+","+schoolid);
            if (userDetails!=null){
                if (jwtUtils.validateToken(jwt,userDetails)){
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken=new UsernamePasswordAuthenticationToken(
                            userDetails,null,userDetails.getAuthorities()
                    );
                    usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                }
                request.setAttribute("schoolid",schoolid);
            }
        }
        filterChain.doFilter(request,response);
    }
}
