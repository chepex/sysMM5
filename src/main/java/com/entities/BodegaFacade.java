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
public class BodegaFacade extends AbstractFacade<Bodega> {
    @PersistenceContext(unitName = "sysMMXPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public BodegaFacade() {
        super(Bodega.class);
    }
    
    @Override
    public List<Bodega> findAll() {
        TypedQuery<Bodega> q = null;         
             q = em.createNamedQuery("Bodega.findByActivo",Bodega.class) ;   
        return q.getResultList();
    }       
    
}
