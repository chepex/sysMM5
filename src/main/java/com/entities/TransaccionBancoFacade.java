/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.entities;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author mmixco
 */
@Stateless
public class TransaccionBancoFacade extends AbstractFacade<TransaccionBanco> {
    @PersistenceContext(unitName = "sysMMXPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TransaccionBancoFacade() {
        super(TransaccionBanco.class);
    }
    
    
   
    public List<TransaccionBanco> findByBanco(Banco banco) {
        TypedQuery<TransaccionBanco> q = null;         
             q = em.createNamedQuery("TransaccionBanco.findByBanco",TransaccionBanco.class)  
               .setParameter("idbanco",banco.getIdbanco());
        return q.getResultList();
    }       
        
}
