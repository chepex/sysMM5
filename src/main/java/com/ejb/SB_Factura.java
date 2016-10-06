/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ejb;

import com.entities.Cliente;
import com.entities.Correlativo;
import com.entities.Factura;
import com.entities.FacturaDet;
import com.entities.util.JsfUtil;
import java.math.BigDecimal;

import java.util.List;
import javax.ejb.EJB;

import javax.ejb.Stateless;

/**
 *
 * @author chepe
 */
@Stateless
public class SB_Factura {

    @EJB
    private com.ejb.SB_Cliente sb_Cliente;
    
    @EJB
    private com.entities.CorrelativoFacade correlativoFacade;    
    
    @EJB
    private com.ejb.SB_inventario sb_inventario;   
    @EJB
    private com.entities.ClienteFacade clienteFacade;      
    
    public String crearFactura(Factura factura, List<FacturaDet> detFactura){
     
        String msg = "ok";
        BigDecimal totalUtilidad =new BigDecimal("0");
        Cliente c = factura.getClienteIdcliente();     
        /*validar limite si es compra al credito*/     
        if(factura.getTipoPagoIdtipoPago().getIdtipoPago()!=1){                
            String vlimite = sb_Cliente.validaLimite(factura.getClienteIdcliente(), factura.getTotal());           
            if(!vlimite.equals("ok")){              
                return "Compra super limite del cliente, limite: "+factura.getClienteIdcliente().getLimite();
            }else{            
                sb_Cliente.actualizaSaldo(factura.getClienteIdcliente(), factura.getTotal());
                factura.setSaldo(factura.getTotal());
            }
        }
        msg  =   generarCorrelativo(  factura);  
        if(!msg.equals("ok")){
            return "Error al generar correlativo";
        }
        
        for(FacturaDet detalle :detFactura){
             detalle.setIdfacturaDet(0);               
             double vcostoFijo = ((detalle.getProductoIdproducto().getCosto().doubleValue() *detalle.getProductoIdproducto().getCostoFijo()) /100);
             detalle.setUtilidad(detalle.getPrecio().subtract(detalle.getProductoIdproducto().getCosto()).subtract(new BigDecimal(vcostoFijo)).multiply(new BigDecimal(detalle.getCantidad())));             
             System.out.println("detalleUtilidad"+detalle.getUtilidad());
            totalUtilidad = totalUtilidad.add(detalle.getUtilidad());
             System.out.println("totalUtilidad"+totalUtilidad);
        }
        System.out.println("totalUtilidad"+totalUtilidad);
        factura.setFacturaDetList(detFactura);  
        factura.setUtilidad(totalUtilidad);
            
        List<Object[]>  lobjt =  sb_inventario.facturaToList(detFactura);
        
        //Registrar Salida
        if (!sb_inventario.createDocumento(factura.getDocumento(), lobjt, "2").equals("ok")){
             return "Favor definir un documento de salida";
        }
        
        c.getFacturaList().add(factura);
        clienteFacade.edit(c);
        
            
     
     return msg;
 }    
    
 public String generarCorrelativo(Factura factura){
    String msg = "ok";
        try{
            List<Correlativo> lcort = correlativoFacade.findByNombre("Factura");
            if(!lcort.isEmpty()){
                Correlativo c= lcort.get(0);
                int nuevoValor= c.getValorActual()+1;
                String vcorrelativo = c.getPrefijo()+nuevoValor;
                c.setValorActual(nuevoValor);
                correlativoFacade.edit(c);
                factura.setDocumento(vcorrelativo);
            }else{
                factura.setDocumento("No corelt");
            }
        }catch(Exception ex){
        
            msg = "error";
        }
        
        
    return msg; 
 }    
    
/*valida limite clinete*/
    
/*generar correlativo*/    
    
/*crear documenteo inventario*/    
    
    
    
/*guardar factura*/    
    
    
}
