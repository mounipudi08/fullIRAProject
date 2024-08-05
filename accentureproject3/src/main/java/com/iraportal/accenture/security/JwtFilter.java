package com.iraportal.accenture.security;
//
//import java.io.IOException;
//
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import com.iraportal.accenture.security.jwt.JwtTokenProvider;
//import com.iraportal.accenture.service.MyUserDetailsService;
//import org.springframework.context.ApplicationContext;
//
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//@Component
//public class JwtFilter extends OncePerRequestFilter{
//	
//	@Autowired
//	JwtTokenProvider jwtService;
//	
//	
//	@Autowired
//	private ApplicationContext context;
//	
//	@Override
//	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
//			throws ServletException, IOException {
//		// TODO Auto-generated method stub
//		String authHeader = request.getHeader("Authorization");
//		String token = null;
//		String userName = null;
//		
//		if(authHeader != null && authHeader.startsWith("Bearer"))
//		{
//			token = authHeader.substring(7);
//			userName = jwtService.getUsernameFromToken(token);
//			System.out.println(userName);
//			
//		}
//		
//		if(userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//			UserDetails userDetails = context.getBean(MyUserDetailsService.class).loadUserByUsername(userName);
//			if(jwtService.validateToken(token,userDetails)) {
//				UsernamePasswordAuthenticationToken authToken = 
//				 new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
//			 authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//			 SecurityContextHolder.getContext().setAuthentication(authToken);
//			}
//		}
//		filterChain.doFilter(request, response);
//	}
//	
//	
//
//}


import com.iraportal.accenture.exceptions.UserNotActiveException;
import com.iraportal.accenture.exceptions.UserNotApprovedException;
import com.iraportal.accenture.model.User;
import com.iraportal.accenture.model.userRole;
import com.iraportal.accenture.repository.UserRepository;
import com.iraportal.accenture.security.jwt.JwtTokenProvider;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    

    
    private Collection<? extends GrantedAuthority> getAuthorities(userRole role) {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }
    
    

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        String token = null;
        String userName = null;

        if (authHeader != null && authHeader.startsWith("Bearer")) {
            token = authHeader.substring(7);
            System.out.println("Token"+ token);
            userName = jwtTokenProvider.getUsernameFromToken(token);
        }
       
        if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            try {
                User user = userRepository.findByEmail(userName);
                if (user == null)
                    throw new BadCredentialsException("Invalid email");
                System.out.println("Login attempt for user: " + user.getEmail());
                System.out.println("Retrieved hashed password from database: " + user.getPassword());
              
                if (!user.isActive()) {
                    throw new UserNotActiveException("User account is not active");
                }

                if (!user.isApproved()) {
                    throw new UserNotApprovedException("User account is not approved");
                }
                userRole role = user.getRole();

               
                UserDetails userDetails = org.springframework.security.core.userdetails.User.builder()
                        .username(user.getEmail())
                        .password(user.getPassword())
                        .authorities(getAuthorities(role))
                        .build();
                if (jwtTokenProvider.validateToken(token, userDetails)) {
                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(user.getEmail(), null, userDetails.getAuthorities());
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            } catch (UsernameNotFoundException e) {
                logger.error("User not found exception", e);
            }
        }
        filterChain.doFilter(request, response);
    }
}
