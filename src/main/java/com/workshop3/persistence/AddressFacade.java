/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.workshop3.persistence;

import com.workshop3.domain.Address;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author hwkei
 */
@Stateless
public class AddressFacade extends AbstractFacade<Address> {

    @PersistenceContext(unitName = "com_Workshop3_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AddressFacade() {
        super(Address.class);
    }
    
    public List<Address> getAddressesCustomer(Long customerId) {
        List<Address> addressList = new ArrayList<>();
        for (Address address : findAll()) {
            if (address.getCustomer().getId() == customerId) {
                addressList.add(address);
            }
        }
        return addressList;
    }
    
}
