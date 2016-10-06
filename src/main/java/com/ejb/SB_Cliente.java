/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ejb;

import com.entities.Cliente;
import java.math.BigDecimal;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author chepe
 */
@Stateless
public class SB_Cliente {

    @EJB
    private com.entities.ClienteFacade clienteFacade; 

     
    
   public String  validaLimite(Cliente cliente, BigDecimal nuevaCompra){
        String msg= "ok";
        BigDecimal nuevoSaldo = new BigDecimal("0");
        try{
            if(cliente.getSaldo()!=null){
              nuevoSaldo = cliente.getSaldo().add(nuevaCompra);
            }
        }catch(Exception ex){
           
             nuevoSaldo = nuevaCompra;
        }     
       try{              
        if(cliente.getLimite().compareTo(nuevoSaldo)!=1){            
             msg= "error";
        }
       }catch(Exception ex){
        return "error";
       }
     
        return msg;
   }
   
   public void actualizaSaldo(Cliente cliente, BigDecimal nuevaCompra){
       cliente.setSaldo(cliente.getSaldo().add(nuevaCompra));
       clienteFacade.edit(cliente);
       
   }
}
