/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.workshop3.persistence;

import com.workshop3.domain.Account;
import com.workshop3.security.PasswordHash;
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
public class AccountFacade extends AbstractFacade<Account> {

    @PersistenceContext(unitName = "com_Workshop3_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AccountFacade() {
        super(Account.class);
    }
    
    public Account findByUsername(String userName) {
        TypedQuery<Account> query = em.createNamedQuery("Account.findByUsername", Account.class);
        List<Account> accountList = query.setParameter("username", userName).getResultList();
        if (accountList.size() == 1) {
            return accountList.get(0);
        } else return null;
    }
    
    public void changePassword(Account account) {
        // Hash the password before saving it to the database
        account.setPassword(PasswordHash.generateHash(account.getPassword()));
        edit(account);
    }
    
    @Override
    public void create(Account account) {
        // Hash the password before saving it to the database
        account.setPassword(PasswordHash.generateHash(account.getPassword()));
        getEntityManager().persist(account);
    }
    
}
