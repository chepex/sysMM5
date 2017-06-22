/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ticket;

import com.entities.AbstractFacade;
import java.math.BigDecimal;
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
public class TicketMensajeFacade extends AbstractFacade<TicketMensaje> {

    @PersistenceContext(unitName = "sysMMXPU2")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TicketMensajeFacade() {
        super(TicketMensaje.class);
    }
    
    
 
    
    
    public Long correlativo(Ticket idT){
        Long val=new Long("0");
       
        
               
            Query q =  em.createNativeQuery(" SELECT IFNULL( max(correlativo) ,0) FROM  ticket_mensaje where idticket= ?" );  
            q.setParameter(1, idT.getIdticket());
            val = (Long)q.getSingleResult();
          
            
                 
       
        return val;
       
    }   
    
    public Long maxId( ){
        Long val=new Long("0");
       
        
               
            Query q =  em.createNativeQuery(" SELECT IFNULL( max(idticket_mensaje) ,0) FROM  ticket_mensaje  " );  
           
            val = (Long)q.getSingleResult();
          
            
                 
       
        return val;
       
    }      
    
    
    public List<TicketMensaje> findByTIcket(Ticket ticket) {
        TypedQuery<TicketMensaje> q = null;
        System.out.println("ticket --->"+ticket.getIdticket());
        System.out.println("ticket --->"+ticket.getIdticket());
        System.out.println("ticket --->"+ticket.getIdticket());
             q = em.createNamedQuery("TicketMensaje.findByTIcket",TicketMensaje.class)               
                .setParameter("idticket",ticket.getIdticket());
              System.out.println("lsita--->"+q.getResultList());
        return q.getResultList();
    }         
        
    
}
