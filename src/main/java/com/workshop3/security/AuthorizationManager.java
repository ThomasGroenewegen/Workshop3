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
    
    static public boolean isUserAuthorized(String jwt, String uri, boolean get, boolean validHeader) {
        
        // Bepaal de user en role die in het cookie zijn meegegeven
        String user = null;
        String role = "NONE";
        if (!jwt.equals("NONE")) {
            role = JWToken.parseRoleFromJWT(jwt);
            user = JWToken.parseUsernameFromJWT(jwt);
        }
        // TODO: deze rollen moeten nog goed uitgewerkt worden!
        // Default is not authorized, elke authorisatie wordt expliciet gegeven
        boolean authorized = false;
        switch(role) {
            case "ADMIN": {
                // Admin can do all
                if (validHeader) authorized = true;
                break;
            }
            case "MEDEWERKER": {
                // MEDEWERKER can do all except some account parts
                if (!validHeader) {
                    authorized = false;
                } else {
                    if (uri.endsWith("login")) authorized = true;
                    // Allow account only for the own page 
                    if (uri.contains("account")) {
                        if (uri.endsWith(user)) {
                            authorized = true;
                        }
                    } else {
                        authorized = true;
                    }
                    // for password change:
                    if (uri.endsWith("account/cp")) authorized = true;
                }
                break;
            }
            case "KLANT": {
                if (!validHeader) {
                    authorized = false;
                } else {
                    if (uri.endsWith("/product") && get) authorized = true;
                    if (uri.endsWith("/product/available") && get) authorized = true;
                    if (uri.endsWith("login")) authorized = true;
                    // Allow account only for the own page 
                    if (uri.contains("account")) {
                        if (uri.endsWith(user)) {
                            authorized = true;
                        } else {
                            authorized = false;
                        }
                    }
                    // for password change:
                    if (uri.endsWith("account/cp")) authorized = true;
                }
                break;
            }
            case "NONE": {
            if (uri.contains("home") || (uri.endsWith("/product") && get)
                    || (uri.endsWith("/login") && validHeader)) authorized = true;
                break;
            }
        }
        
        // Output for debugging
        System.out.println("IN AUTHORIZATION MANAGER: \n"
                + "        USER: " + user + "\n"
                + "        ROLE: " + role + "\n"
                + "        URI: " + uri + "\n" 
                + "        GET: " + get + "\n"
                + "        VALID HEADER : " + validHeader + "\n"
                + "        RESULTING AUTHORISATION : " + authorized);
        return authorized;
    }
}