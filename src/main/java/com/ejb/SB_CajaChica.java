/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ejb;

import com.entities.CajaChica;
import com.entities.TransaccionCaja;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.Date;
import javax.ejb.Stateless;

/**
 *
 * @author mmixco
 */
@Stateless
public class SB_CajaChica {


    
    public String apertura(){
        String msg ="ok";
        
        
        return msg;
    
    }
    
    public String cierre(){
        String msg ="ok";
        
        
        return msg;        
    
    }
    
    public String agregar(TransaccionCaja tc, CajaChica caja){
        String msg ="ok";
  
        caja.setSaldo(caja.getSaldo().subtract(tc.getTotal(), MathContext.UNLIMITED));
        if(caja.getTransaccionCajaList().isEmpty()){
         caja.setTransaccionCajaList(   new ArrayList<TransaccionCaja>());
        }
        caja.getTransaccionCajaList().add(tc);    
        return msg;        
    
    }    
}
