/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.workshop3.security;

/**
 *
 * @author hwkei
 */
public class AuthorizationManager {
    
    static public boolean isUserAuthorized(String accountType, String uri, boolean get, boolean validHeader) {
        
        boolean authorized = false;
        // TODO verder uitwerken voor KLANT en MEDEWERKER
        if (accountType.equals("ADMIN") && validHeader) authorized = true;
        if (accountType.equals("MEDEWERKER")) {
            if (uri.contains("account") || !validHeader) {
                authorized = false;
            } else authorized = true;
        }
        if (accountType.equals("NONE")) {
            // when not logged only allow:
            if (uri.contains("home") || (uri.endsWith("/product") && get)
                    || (uri.endsWith("/login") && validHeader)) authorized = true;
        }
        // Output for debugging
        System.out.println("IN AUTHORIZATION MANAGER: \n" 
                + "        ROLE: " + accountType + "\n"
                + "        URI: " + uri + "\n" 
                + "        GET: " + get + "\n"
                + "        VALID HEADER : " + validHeader + "\n"
                + "        RESULTING AUTHORISATION : " + authorized);
        return authorized;
    }
}
