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
public class BancoFacade extends AbstractFacade<Banco> {
    @PersistenceContext(unitName = "sysMMXPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public BancoFacade() {
        super(Banco.class);
    }
    
        public List<Banco> findByNombreCodigo(String  nombre) {
        TypedQuery<Banco> q = null;
         
             q = em.createNamedQuery("Banco.findByNombreCodigo",Banco.class)               
                .setParameter("nombre", "%"+nombre+"%");
              
        return q.getResultList();
    }     
        
    @Override
    public List<Banco> findAll() {
        TypedQuery<Banco> q = null;         
             q = em.createNamedQuery("Banco.findByActivo",Banco.class) ;   
        return q.getResultList();
    }           
        
    
}
