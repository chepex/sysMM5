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
public class UsuarioFacade extends AbstractFacade<Usuario> {
    @PersistenceContext(unitName = "sysMMXPU2")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UsuarioFacade() {
        super(Usuario.class);
    }
    
    @Override
    public List<Usuario> findAll() {
        TypedQuery<Usuario> q = null;         
             q = em.createNamedQuery("Usuario.findByActivo",Usuario.class) ;   
        return q.getResultList();
    }    
    
  public List<Usuario> findByLogin2(String usuario , String pass) {
        TypedQuery<Usuario> q = null;
        q = em.createNamedQuery("Usuario.findByLogin",Usuario.class)
                .setParameter("usuario", usuario)
                .setParameter("password", pass);

       return q.getResultList();
    }     
        
    
}
