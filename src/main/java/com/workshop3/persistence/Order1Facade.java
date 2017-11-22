/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.workshop3.persistence;

import com.workshop3.domain.Order1;
import com.workshop3.domain.OrderItem;
import java.util.Collection;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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
    
    public void createOrder(Order1 order) {        
        
        em.persist(order);
        for(OrderItem orderItem: order.getOrderItemCollection()) {
            orderItem.setOrder(order);
            em.persist(orderItem);
        }
    }    
}
