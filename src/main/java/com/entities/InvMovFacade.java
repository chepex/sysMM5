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
 * @author chepe
 */
@Stateless
public class InvMovFacade extends AbstractFacade<InvMov> {
    @PersistenceContext(unitName = "sysMMXPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public InvMovFacade() {
        super(InvMov.class);
    }
    
    public List<InvMov> findByDocumentoFecha(Date fi, Date ff, Documento docto) {
        TypedQuery<InvMov> q = null;
         
  
        if(null!=fi && docto!=null){
             q = em.createNamedQuery("InvMov.findByDocumentoFecha",InvMov.class)
                .setParameter("fi", fi)
                .setParameter("ff", ff)
                .setParameter("documento", docto.getIddocumento());
        }        
        if(null==fi &&  docto!=null){
             q = em.createNamedQuery("InvMov.findByDocumento",InvMov.class)               
                .setParameter("docto",   docto.getIddocumento());
        }
       
        
        return q.getResultList();
    }      
    
}
