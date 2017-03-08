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
public class ProveedorFacade extends AbstractFacade<Proveedor> {
    @PersistenceContext(unitName = "sysMMXPU2")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ProveedorFacade() {
        super(Proveedor.class);
    }
    
    @Override
    public List<Proveedor> findAll() {
        TypedQuery<Proveedor> q = null;         
             q = em.createNamedQuery("Proveedor.findByActivo",Proveedor.class) ;   
        return q.getResultList();
    }    
    
    public List<Proveedor> findByNombreCodigo(String  nombre) {
        TypedQuery<Proveedor> q = null;
         System.out.println("2nombre-->"+nombre);
             q = em.createNamedQuery("Proveedor.findByNombreCodigo",Proveedor.class)               
                .setParameter("nombre", "%"+nombre+"%");
              
        return q.getResultList();
    }    
    
    public String  saldoProveedores(){
        String  val="0";
        
        
        try{           
            Query q =  em.createNativeQuery(" SELECT  sum(saldo) FROM  proveedor where activo= true and saldo > 0 "  );  
            val = String.valueOf(q.getSingleResult());
            
        }catch(Exception ex){
            
            val = "0";
            System.out.println("aqio2");
            System.out.println("--->"+ex);
            
        }               
       
        return val;
       
    }      
    
    public List<Proveedor> saldoProveedor( ) {
        Query q = null;
        try{           
           q  =  em.createNativeQuery(" SELECT  * FROM  proveedor where activo= true and saldo > 0 " ,Proveedor.class );  
        }catch(Exception ex){            
            System.out.println("error-->"+ex);
        }               
        return q.getResultList();
    }     
    
}
