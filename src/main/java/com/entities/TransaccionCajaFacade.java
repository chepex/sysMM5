/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.entities;

import java.util.Date;
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
public class TransaccionCajaFacade extends AbstractFacade<TransaccionCaja> {
    @PersistenceContext(unitName = "sysMMXPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TransaccionCajaFacade() {
        super(TransaccionCaja.class);
    }
    
    
    public List<TransaccionCaja> findByFecha(){
       
        TypedQuery<TransaccionCaja> q = null;                 
        Date fi  = new Date();
        q = em.createNamedQuery("TransaccionCaja.findByFecha",TransaccionCaja.class)
        .setParameter("fecha", fi);
        
       return q.getResultList();     
    }     
    
}
