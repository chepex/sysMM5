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
public class CorrelativoFacade extends AbstractFacade<Correlativo> {
    @PersistenceContext(unitName = "sysMMXPU2")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CorrelativoFacade() {
        super(Correlativo.class);
    }
    
    public List<Correlativo> findByNombre(String nombre) {
        TypedQuery<Correlativo> q = null;
       
                 q = em.createNamedQuery("Correlativo.findByNombre",Correlativo.class)
                .setParameter("nombre", nombre);
                 
         
        
        return q.getResultList();
    }        
    
}
