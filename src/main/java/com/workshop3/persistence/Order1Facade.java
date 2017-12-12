/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.workshop3.persistence;

import com.workshop3.domain.Order1;
import com.workshop3.domain.OrderItem;
import java.util.Collection;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author hwkei
 */
@Stateless
public class Order1Facade extends AbstractFacade<Order1> {

    @PersistenceContext(unitName = "com_Workshop3_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public Order1Facade() {
        super(Order1.class);
    }
    
    public void createOrder(Order1 order, Collection<OrderItem> orderItemCollection) {        
        
        em.persist(order);
        for(OrderItem orderItem: orderItemCollection) {
            em.persist(orderItem);
        }
    }    

    public List<Order1> findAllByCustomer(Long id) {
        TypedQuery query = em.createNamedQuery("Order1.findAllByCustomerId", Order1.class);
        query.setParameter("id", id);
        List<Order1> orderList = query.getResultList();
        System.out.println("IN findAllByCustomer, orderList length is: " + orderList.size());
        return orderList;
    }
}
