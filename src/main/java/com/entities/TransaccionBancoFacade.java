/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.entities;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * @author mmixco
 */
@Stateless
public class TransaccionBancoFacade extends AbstractFacade<TransaccionBanco> {
    @PersistenceContext(unitName = "sysMMXPU2")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TransaccionBancoFacade() {
        super(TransaccionBanco.class);
    }
    
    
   
    public List<TransaccionBanco> findByBancoCuenta(Banco banco, CuentaBanco cuenta) {
        TypedQuery<TransaccionBanco> q = null;         
             q = em.createNamedQuery("TransaccionBanco.findByBancoCuenta",TransaccionBanco.class)  
               .setParameter("idbanco",banco.getIdbanco())
               .setParameter("cuenta",cuenta.getIdcuenta());
        return q.getResultList();
    }    
    
    public String findId(){
            String val="0";
       
        
        try{           
            Query q =  em.createNativeQuery(" SELECT max(idtransaccion_banco)  FROM  transaccion_banco  " );  
            val =String.valueOf(q.getSingleResult());
            
            
        }catch(Exception ex){
            
            val ="0";
            
        }               
       
        return val;
       
    }        
        
}
