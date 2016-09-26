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
public class PagoCompraFacade extends AbstractFacade<PagoCompra> {
    @PersistenceContext(unitName = "sysMMXPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PagoCompraFacade() {
        super(PagoCompra.class);
    }
    
    
    public List<PagoCompra> findByCompra(Compra c) {
       TypedQuery<PagoCompra> q = null;
       q = em.createNamedQuery("PagoCompra.findByCompra",PagoCompra.class)
            .setParameter("compra", c.getIdcompra());

       return q.getResultList();
    }    
    
}
