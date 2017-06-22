/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ticket;

import com.entities.AbstractFacade;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author mmixco
 */
@Stateless
public class DeptoFacade extends AbstractFacade<Depto> {

    @PersistenceContext(unitName = "sysMMXPU2")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DeptoFacade() {
        super(Depto.class);
    }
    
}
