/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ejb;

import com.entities.Usuario;
import com.ticket.Ticket;
import com.ticket.TicketFacade;
import com.ticket.TicketMensaje;
import com.ticket.TicketMensajeFacade;
import java.math.BigDecimal;
import java.util.Date;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author mmixco
 */
@Stateless
public class SB_ticket {

    @EJB
    private TicketMensajeFacade ticketMensajeFacade;        
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    
    public String addMesage( Ticket ticket , String msg, Usuario user){
        String mm = "OK";
        
            TicketMensaje tm = new TicketMensaje();
            
            Long co = ticketMensajeFacade.correlativo(ticket);
            Long vid = ticketMensajeFacade.maxId();
            System.out.println("corelativo -->"+co+1);
            System.out.println("ticke-->"+ticket.getIdticket());
            System.out.println("user-->"+user);
            tm.setCorrelativo(co.intValue()+1);
            tm.setDescripcion(msg);
            tm.setFecha(new Date());
            tm.setIdticket(ticket);        
            tm.setIdusuario(user);
            tm.setIdticketMensaje(vid.intValue()+1);
            System.out.println(" id mesaje ticket -->"+vid.intValue()+1);
            
            
            ticketMensajeFacade.edit(tm);
            
       
        
        
        
        return mm;
    
    }
}
