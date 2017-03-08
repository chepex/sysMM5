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
public class CuentaBancoFacade extends AbstractFacade<CuentaBanco> {
    @PersistenceContext(unitName = "sysMMXPU2")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CuentaBancoFacade() {
        super(CuentaBanco.class);
    }
    
    public List<CuentaBanco> findByIdbanco(Banco b) {
       TypedQuery<CuentaBanco> q = null;
       q = em.createNamedQuery("CuentaBanco.findByIdbanco",CuentaBanco.class)
            .setParameter("banco", b.getIdbanco());

       return q.getResultList();
    }     
}
