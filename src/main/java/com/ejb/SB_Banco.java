/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ejb;

import com.entities.TransaccionBanco;
import com.entities.util.JsfUtil;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author mmixco
 */
@Stateless
public class SB_Banco {

    @EJB
    private com.entities.BancoFacade bancoFacade;     
    @EJB
    private com.entities.TransaccionBancoFacade transaccionBancoFacade;   
    @EJB
    private com.entities.CuentaBancoFacade cuentaBancoFacade;      
    
    public String agregarTransaccion(TransaccionBanco transaccion){
        
        /*validar sobre giro*/
        /*operacion en cuenta bancaria*/
        /*operacion en banco*/
        try{
            if(transaccion.getCuentaBancoIdcuenta().getSaldo().compareTo(transaccion.getValor())==-1){
                
                  JsfUtil.addErrorMessage("Cuenta sobregirada");  
                  
            }
            
            if(transaccion.getTipoTransaccionIdtipoTransaccion().getSumaResta().equals("R")){
                transaccion.getCuentaBancoIdcuenta().setSaldo(transaccion.getCuentaBancoIdcuenta().getSaldo().subtract(transaccion.getValor()));                    
                transaccion.getBancoIdbanco().setSaldo(transaccion.getBancoIdbanco().getSaldo().subtract(transaccion.getValor()));
            }
            if(transaccion.getTipoTransaccionIdtipoTransaccion().getSumaResta().equals("S")){
                transaccion.getCuentaBancoIdcuenta().setSaldo(transaccion.getCuentaBancoIdcuenta().getSaldo().add(transaccion.getValor()));                    
                transaccion.getBancoIdbanco().setSaldo(transaccion.getBancoIdbanco().getSaldo().add(transaccion.getValor()));
            }        
            
            cuentaBancoFacade.edit(transaccion.getCuentaBancoIdcuenta());
            transaccionBancoFacade.edit(transaccion);
            bancoFacade.edit(transaccion.getBancoIdbanco());
            
        }catch(Exception ex){
             JsfUtil.addErrorMessage("Surgio un error,"+ex);       
             return "error";
        }
        return "ok";
    }
}
