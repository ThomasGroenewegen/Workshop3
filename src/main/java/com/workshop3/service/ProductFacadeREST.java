/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.workshop3.service;

import com.workshop3.domain.Product;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ejb.EJB;
import com.workshop3.persistence.*;
import java.util.HashSet;
import java.util.Set;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Ahmed-Al-Alaaq(Egelantier)
 */
@Stateless

@Path("product")
public class ProductFacadeREST {

    @EJB
    ProductFacade productFacade;

    /*@PersistenceContext(unitName = "com_Workshop3_war_1.0-SNAPSHOTPU")
    private EntityManager em;*/
    public ProductFacadeREST() {

    }

    @POST
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Product entity) {
        productFacade.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Long id, Product entity) {
        productFacade.edit(entity);
    }

    @DELETE
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void remove(@PathParam("id") Long id) {
        productFacade.remove(productFacade.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Product find(@PathParam("id") Long id) {
        return productFacade.find(id);
    }
    
    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Product> findAll() {
        return productFacade.findAll();
    }
    
    @GET
    @Path("/available")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Product> findAllAvailable() {
        System.out.println("METHOD FIND ALL AVAILABLE WAS CALLED");
        return productFacade.findAllAvailable();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Product> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return productFacade.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(productFacade.count());
    }

 

}
  /*@GET
    @Path("/get")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Set<Product> findStatus() {
       Set<Product> allProducts = new HashSet<Product>(productFacade.findAll());
       Set<Product> beschikken = null;
       for (Product prod : allProducts){
           if (prod.getProductStatus().equals("BESCHIKBAAR"))
               beschikken.add(prod);
       }
        return beschikken;
    }*/