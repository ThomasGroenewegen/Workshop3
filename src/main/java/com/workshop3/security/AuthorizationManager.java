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
    
    static public boolean isUserAuthorized(String accountType, String uri) {
        
        boolean authorized = false;
        // TODO verder uitwerken voor KLANT en MEDEWERKER
        if (accountType.equals("ADMIN")) authorized = true;
        if (accountType.equals("MEDEWERKER")) {
            if (uri.contains("account")) {
                authorized = false;
            } else authorized = true;
        }
        if (accountType.equals("NONE")) {
            // when not logged only allow:
            if (!uri.contains("home")) authorized = false;
        }
        System.out.println("AUTHORIZATION MANAGER: " + uri + " : " + authorized);
        return authorized;
    }
}
