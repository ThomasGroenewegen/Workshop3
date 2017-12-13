/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.workshop3.service;

import com.workshop3.domain.Order1;
import com.workshop3.domain.OrderItem;
import com.workshop3.domain.OrderStatus;
import com.workshop3.domain.Product;
import static com.workshop3.domain.Product.ProductStatus.ONBESCHIKBAAR;
import com.workshop3.persistence.CustomerFacade;
import com.workshop3.persistence.Order1Facade;
import com.workshop3.persistence.OrderItemFacade;
import com.workshop3.persistence.ProductFacade;
import java.util.Date;
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
@Path("order1")
public class Order1FacadeREST {

    @EJB
    Order1Facade order1Facade;

    @EJB
    CustomerFacade customerFacade;

    @EJB
    OrderItemFacade orderItemFacade;

    @EJB
    ProductFacade productFacade;

    public Order1FacadeREST() {

    }

    @POST
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Order1 entity) {
        entity.setOrderStatus(OrderStatus.NIEUW);
        entity.setDateTime(new Date());

        System.out.println("AANTAL PRODUCTEN: " + entity.getOrderItemCollection().size());

        // custom method to save order and orderItems together
        order1Facade.createOrder(entity, entity.getOrderItemCollection());

        for (OrderItem orderItem : entity.getOrderItemCollection()) {
            updateProductStockAfterAddingOrderItem(orderItem);
        }
    }

    @POST
    @Path("/{user}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void createByCustomer(Order1 entity) {
        create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Long id, Order1 entity) {
        Order1 order = order1Facade.find(id);
        order.setOrderStatus(entity.getOrderStatus());
        order1Facade.edit(order);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Long id) {
        Order1 order = order1Facade.find(id);
        
        order1Facade.remove(order);
        
        for (OrderItem orderItem : order.getOrderItemCollection()) {
            updateProductStockAfterDeletingOrderItem(orderItem);
        }
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Order1 find(@PathParam("id") Long id) {
        return order1Facade.find(id);
    }

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Order1> findAll() {
        return order1Facade.findAll();
    }

    @GET
    @Path("/{id}/{user}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Order1> findAllByCustomer(@PathParam("id") Long id) {
        return order1Facade.findAllByCustomer(id);
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(order1Facade.count());
    }

    protected void updateProductStockAfterAddingOrderItem(OrderItem orderItem) {

        Product product = productFacade.find(orderItem.getProduct().getId());

        int amount = orderItem.getAmount();
        int oldStock = product.getStock();
        product.setStock(oldStock - amount);

        if (product.getStock() == 0) {
            product.setProductStatus(ONBESCHIKBAAR);
        }

        productFacade.edit(product);
    }
    
    protected void updateProductStockAfterDeletingOrderItem(OrderItem orderItem) {

        Product product = productFacade.find(orderItem.getProduct().getId());

        int amount = orderItem.getAmount();
        int oldStock = product.getStock();
        product.setStock(oldStock + amount);

        productFacade.edit(product);
    }

}
