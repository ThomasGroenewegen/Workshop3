/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.workshop3.service;

import com.workshop3.domain.Account;
import com.workshop3.persistence.AccountFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author thoma
 */
@Stateless
@Path("account")
public class AccountFacadeREST {

    @EJB
    AccountFacade accountFacade;

    public AccountFacadeREST() {
        
    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    public void create(Account entity) {
        accountFacade.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Long id, Account entity) {
        accountFacade.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Long id) {
        accountFacade.remove(accountFacade.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Account find(@PathParam("id") Long id) {
        return accountFacade.find(id);
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public List<Account> findAll() {
        return accountFacade.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_JSON})
    public List<Account> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return accountFacade.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(accountFacade.count());
    }
    
    @GET
    @Path("my_details/{username}")
    @Produces(MediaType.APPLICATION_JSON)
    public Account findMyDetails(@PathParam("username") String username) {
        return accountFacade.findByUsername(username);
    }
    
    @PUT
    @Path("cp")
    @Consumes({MediaType.APPLICATION_JSON})
    public void changePassword(Account entity) {
        accountFacade.changePassword(entity);
    }
    
}
