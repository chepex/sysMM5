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
    
    public String saldoClientes(){
        String val="0";
        
        
        try{           
            Query q =  em.createNativeQuery(" SELECT  sum(saldo) FROM  cliente where activo= true and saldo > 0 "  );  
            val = String.valueOf(q.getSingleResult());
            
        }catch(Exception ex){
            
            val ="0";
            System.out.println("aqio2");
            System.out.println("--->"+ex);
            
        }               
       
        return val;
       
    }       
    
    public List<Cliente> saldoPendiente( ) {
        Query q = null;
        
        
        try{           
           q  =  em.createNativeQuery(" SELECT  * FROM  cliente where activo= true and saldo > 0 " ,Cliente.class );  
           
            
        }catch(Exception ex){
            
            System.out.println("error-->"+ex);
            
        }               
       
        return q.getResultList();
    }     
        
    
}
