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
public class ClienteFacade extends AbstractFacade<Cliente> {
    @PersistenceContext(unitName = "sysMMXPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ClienteFacade() {
        super(Cliente.class);
    }
    
    @Override
    public List<Cliente> findAll() {
        TypedQuery<Cliente> q = null;         
             q = em.createNamedQuery("Cliente.findByActivo",Cliente.class) ;   
        return q.getResultList();
    }     
    
    public List<Cliente> findByNombreCodigo(String  nombre) {
        TypedQuery<Cliente> q = null;
     
             q = em.createNamedQuery("Cliente.findByNombreCodigo",Cliente.class)               
                .setParameter("nombre", "%"+nombre+"%");
              
        return q.getResultList();
    }     
        
    
}
