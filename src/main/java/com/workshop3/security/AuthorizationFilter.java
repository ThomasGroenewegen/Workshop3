/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.workshop3.security;

import com.workshop3.domain.AccountType;
import java.util.Enumeration;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author hwkei
 */
public class AuthorizationFilter implements Filter {
    
    String errorPage = "/login_not_autorized.html";
    
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        
        if (filterConfig != null) { 
//           errorPage = FilterConfig.getInitParameter("error_page");
        }
    }
    
    boolean validHeader(HttpServletRequest req) {
       String requestLocation = req.getHeader("x-requested-with");
       return requestLocation != null && requestLocation.equals("XMLHttpRequest");
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {        
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        
        String role = getUserRole(request);
        
        System.out.println("IN FILTER with ROLE: " + role + " : " + request.getRequestURL());
        
        if (AuthorizationManager.isUserAuthorized(role, request.getRequestURL().toString(), 
                request.getMethod().equals("GET"), validHeader(request))) {
            chain.doFilter(request, response);
        } else {
            response.setHeader("REQUIRES_AUTH", "1");
            response.sendRedirect("http://localhost:8080/login.html");
        }
    }
    
    private String getUserRole(HttpServletRequest request) {
        
        Cookie[] cookies = request.getCookies();
        
        if (cookies == null) return "NONE"; // Not logged in
        // TODO: Aanwezigheid van meer cookies moet nog opgelost worden
        
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("jwt")) {
                return JWToken.parseRoleFromJWT(cookie.getValue());
            }
        }
        return "NONE"; // No jwt found so not logged in
    }

    @Override
    public void destroy() {
        
    }
    
}
