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
 * @author chepe
 */
@Stateless
public class InvDetmFacade extends AbstractFacade<InvDetm> {
    @PersistenceContext(unitName = "sysMMXPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public InvDetmFacade() {
        super(InvDetm.class);
    }
    
    public List<InvDetm> findByNombre(Producto producto) {
        TypedQuery<InvDetm> q = null;
       
                 q = em.createNamedQuery("InvDetm.findByProducto",InvDetm.class)
                .setParameter("producto", producto);
                 
         
        
        return q.getResultList();
    }      
    
}
