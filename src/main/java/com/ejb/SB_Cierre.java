/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ejb;

import com.entities.Cierre;
import com.entities.FacturaDet;
import com.entities.Producto;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author mmixco
 */
@Stateless
public class SB_Cierre {

    @EJB
    private com.entities.ProductoFacade productoFacade; 
    @EJB
    private com.entities.FacturaDetFacade facturaDetFacade;      
    @EJB
    private com.entities.CompraDetFacade compraDetFacade;       
    @EJB
    private com.entities.CierreFacade cierreFacade;    
    
    public String cerrar(int anio, int mes){
        String msg = "ok";
        List<Producto> lproductos = productoFacade.findAll();
        if(lproductos.isEmpty()){
            return "No existen productos activos";
        }
        for(Producto p: lproductos){
            Cierre cierre = new Cierre(0);
            cierre.setAnio(anio);
            cierre.setMes(mes);
            cierre.setIdproducto(p);
            cierre.setCostoUnitario(p.getCosto());
            cierre.setPrecio(p.getPrecio());
            cierre.setExistencia(p.getExistencia());
            cierre.setMargen( String.valueOf(p.getMargen()));
            cierre.setFecha(new Date());
            
            
            List<Object[]>  ld =  facturaDetFacade.finByMes(anio, mes, p.getIdproducto());
            
            if(!ld.isEmpty()){                  
                int cantidad =Integer.valueOf( ld.get(0)[1].toString());
                BigDecimal total = new BigDecimal(ld.get(0)[2].toString());
                BigDecimal utilidad = new BigDecimal(ld.get(0)[3].toString());
                cierre.setCantidadVenta(cantidad);
                cierre.setTotalVenta(total);               
                cierre.setTotalUtilidad(utilidad);
            }      
            
            List<Object[]>  ldc =  compraDetFacade.finByMes(anio, mes, p.getIdproducto());
           
            
            if(!ldc.isEmpty()){                  
                int cantidad =Integer.valueOf( ldc.get(0)[1].toString());
                BigDecimal total = new BigDecimal(ldc.get(0)[2].toString());
                
                cierre.setCantidadCompra(cantidad);
                cierre.setTotaCompra(total);               
              
            }      
                        
           
            cierreFacade.edit(cierre);
        
        }
        
    
        return msg;
    }
}
