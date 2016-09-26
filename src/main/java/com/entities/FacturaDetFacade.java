/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.entities;

import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * @author chepe
 */
@Stateless
public class FacturaDetFacade extends AbstractFacade<FacturaDet> {
    @PersistenceContext(unitName = "sysMMXPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public FacturaDetFacade() {
        super(FacturaDet.class);
    }
    
  public BigDecimal finById(){
        BigDecimal val=new BigDecimal("0");
        Query q =  em.createNativeQuery("SELECT max(idfactura_det) FROM  factura_det" );  
        
        
        try{           
            
            val = (BigDecimal)q.getSingleResult();
            
        }catch(Exception ex){
            
            val = new BigDecimal("0");
            
        }               
       
        return val;
       
    }      
    
}