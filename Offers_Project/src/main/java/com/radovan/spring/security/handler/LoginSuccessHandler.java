package com.radovan.spring.security.handler;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.radovan.spring.entity.UserEntity;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;


@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {


    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest,
                                        HttpServletResponse httpServletResponse,
                                        Authentication authentication) throws IOException, ServletException {
        Collection<? extends GrantedAuthority> roles = authentication.getAuthorities();

        UserEntity authUser = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        
        
        System.out.println("User found with username:" + authUser.getUsername());
        System.out.println("Authorities: " + roles.size());
               

        for (GrantedAuthority role : roles) {
        	
            if (role.getAuthority().equals("ADMIN")) {

                httpServletResponse.sendRedirect("/admin");
                return;
            } else {
            	httpServletResponse.sendRedirect("/");
            }
        }
        
        
        
    }
}