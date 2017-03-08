/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.entities;

import java.math.BigDecimal;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Desarrollo
 */
@Stateless
public class CierreFacade extends AbstractFacade<Cierre> {
    @PersistenceContext(unitName = "sysMMXPU2")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CierreFacade() {
        super(Cierre.class);
    }
    

    public BigDecimal finById(){
        BigDecimal val=new BigDecimal("0");
       
        
        try{           
             Query q =  em.createNativeQuery("SELECT IFNULL( max(idfactura_det) ,1) FROM  factura_det" );  
            val = (BigDecimal)q.getSingleResult();
            
        }catch(Exception ex){
            
            val = new BigDecimal("1");
            
        }               
       
        return val;
       
    }      
    
}
