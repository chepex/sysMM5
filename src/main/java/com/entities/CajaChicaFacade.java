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
public class CajaChicaFacade extends AbstractFacade<CajaChica> {
    @PersistenceContext(unitName = "sysMMXPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CajaChicaFacade() {
        super(CajaChica.class);
    }

    
    public List<CajaChica> findByFecha(Date  fecha) {
        TypedQuery<CajaChica> q = null;
     
             q = em.createNamedQuery("CajaChica.findByFecha",CajaChica.class)               
                .setParameter("fecha",fecha);
              
        return q.getResultList();
    }     
    
    public List<CajaChica> findByAbierta( ) {
        TypedQuery<CajaChica> q = null;
     
             q = em.createNamedQuery("CajaChica.findByAbierta",CajaChica.class);               
                
              
        return q.getResultList();
    }      
            
    
}
