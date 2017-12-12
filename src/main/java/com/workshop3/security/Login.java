/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.workshop3.security;

import com.workshop3.domain.Account;
import static com.workshop3.domain.AccountType.KLANT;
import com.workshop3.domain.Customer;
import com.workshop3.persistence.AccountFacade;
import com.workshop3.persistence.CustomerFacade;
import java.io.IOException;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject;

/**
 *
 * @author hwkei
 */
@WebServlet({"/login", "/logout"})
public class Login extends HttpServlet {
    
    @EJB
    AccountFacade accountFacade;
    
    @EJB
    CustomerFacade customerFacade;
    
	@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException { 
        response.setContentType("text/html;charset=UTF-8");
        if (request.getRequestURI().contains("logout")) {
            Cookie cookie = new Cookie("jwt", "");
            cookie.setMaxAge(0);
            cookie.setPath("/");
            cookie.setHttpOnly(true);
            response.addCookie(cookie);
            System.out.println("Should delete cookie");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        JSONObject jObj = new JSONObject(request.getReader().readLine());
        
        Account account = accountFacade.findByUsername(jObj.getString("username"));
        if (account != null && PasswordHash.validatePassword(jObj.getString("password"), account.getPassword())) {
            System.out.println("User " + jObj.getString("username") + " succesvol gevalideerd met wachtwoord " + jObj.getString("password") );
            String jwtToken = JWToken.createJWT(
                jObj.getString("username"), 
                "ApplikaasieClient", 
                account.getAccountType().toString(), 
                60*60*1000);
            System.out.println("TOKEN: " + jwtToken);
            
            // If cookie already exist delete it
            Cookie cookie = getCookie(request, "jwt");
            if (getCookie(request, "jwt") != null) {
                cookie.setMaxAge(0);
                cookie.setValue(null);
                System.out.println("Cookie replaced");
            } 
            
            cookie = new Cookie("jwt", jwtToken);
            cookie.setMaxAge(60*60);
            cookie.setPath("/");
            cookie.setHttpOnly(true);
            response.addCookie(cookie);
			
	    response.setHeader("ROLE", JWToken.parseRoleFromJWT(jwtToken));
            
            // Get the customer id, needed to save orders
            if(account.getAccountType().equals(KLANT)) {
                Customer customer = customerFacade.findByAccountId(account.getId());
                response.setHeader("CUSTOMER_ID", customer.getId().toString());
            }
                    
        } else {
            response.setHeader("AUTH_FAILED", "1");
            System.out.println("AUTHENTICATION FAILED");
        }     
    }
    
    public static Cookie getCookie(HttpServletRequest request, String name) {
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if (cookie.getName().equals(name)) {
                    return cookie;
                }
            }
        }
        return null;
    }
}
