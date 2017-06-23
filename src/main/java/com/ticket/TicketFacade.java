/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ticket;

import com.entities.AbstractFacade;
import java.math.BigDecimal;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author mmixco
 */
@Stateless
public class TicketFacade extends AbstractFacade<Ticket> {

    @PersistenceContext(unitName = "sysMMXPU2")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TicketFacade() {
        super(Ticket.class);
    }
    
    public Long correlativo( ){
        Long val=new Long("0");
       
          
            Query q =  em.createNativeQuery(" SELECT IFNULL( max(idticket) ,0) FROM  ticket" );  
           
            val = (Long)q.getSingleResult();
          
            System.out.println("val-->"+val);
               
       
        return val;
       
    }   
    
    public Long totalCreados( ){
        Long val=new Long("0");
       
          
            Query q =  em.createNativeQuery(" select COUNT(*) from ticket " +
                        "  where fecha_creacion " +
                        "  between DATE_ADD(CURDATE(), INTERVAL -30 DAY)  " +
                        "  AND CURDATE()    " );  
           
            val = (Long)q.getSingleResult();
          
            System.out.println("val-->"+val);
            System.out.println("val-->"+val);
            System.out.println("val-->"+val);
               
       
        return val;
       
    }    
    
    public Long totalFinalizados( ){
        Long val=new Long("0");
       
          
            Query q =  em.createNativeQuery(" select COUNT(*) from ticket " +
                        "  where fecha_finalizado " +
                        "  between DATE_ADD(CURDATE(), INTERVAL -30 DAY)  " +
                        "  AND CURDATE()    " );  
           
            val = (Long)q.getSingleResult();
          
            System.out.println("val-->"+val);
            System.out.println("val-->"+val);
            System.out.println("val-->"+val);
               
       
        return val;
       
    }   
    
    public Long totalAsignados( ){
        Long val=new Long("0");
       
          
            Query q =  em.createNativeQuery(" select COUNT(*) from ticket " +
                        "  where fecha_asignado " +
                        "  between DATE_ADD(CURDATE(), INTERVAL -30 DAY)  " +
                        "  AND CURDATE()    " );  
           
            val = (Long)q.getSingleResult();
          
            System.out.println("val-->"+val);
            System.out.println("val-->"+val);
            System.out.println("val-->"+val);
               
       
        return val;
       
    }      
        
    
    

    
}
