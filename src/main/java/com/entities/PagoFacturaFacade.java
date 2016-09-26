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
public class PagoFacturaFacade extends AbstractFacade<PagoFactura> {
    @PersistenceContext(unitName = "sysMMXPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PagoFacturaFacade() {
        super(PagoFactura.class);
    }
    
    public List<PagoFactura> findByCompra(Factura f) {
       TypedQuery<PagoFactura> q = null;
       q = em.createNamedQuery("PagoFactura.findByIdfactura",PagoFactura.class)
            .setParameter("factura", f.getIdfactura());

       return q.getResultList();
    }      
    
   
    
    
    
    
}
