/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.workshop3.persistence;

import com.workshop3.domain.Customer;
import com.workshop3.security.PasswordHash;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author hwkei
 */
@Stateless
public class CustomerFacade extends AbstractFacade<Customer> {

    @PersistenceContext(unitName = "com_Workshop3_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CustomerFacade() {
        super(Customer.class);
    }
    
    @Override
    public void create(Customer customer) {
        // Hash the password of the linked account before saving it to the database
        customer.getAccount().setPassword(PasswordHash.generateHash(customer.getAccount().getPassword()));
        getEntityManager().persist(customer);
    }

    public Customer findByAccountId(Long id) {
        Query query = em.createNamedQuery("Customer.findByAccountId", Customer.class);
        query.setParameter("id", id);
        Customer customer = (Customer)query.getSingleResult();
        System.out.println("IN FindByAccountId, customer is: " + customer.toString());
        return customer;
    }    
}
