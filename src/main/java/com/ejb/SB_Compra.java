/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ejb;

import com.entities.Compra;
import com.entities.CompraDet;
import com.entities.Producto;
import com.entities.util.JsfUtil;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author mmixco
 */
@Stateless
public class SB_Compra {
    
    @EJB
    private com.entities.ProveedorFacade proveedorFacade;  
    @EJB
    private com.entities.ProductoFacade productoFacade;  
    @EJB
    private com.ejb.SB_inventario sb_inventario;      
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    
    public String crarCompra(Compra compra, List<CompraDet> detCompra){
        String msg = "ok";
        
        for(CompraDet detalle :detCompra){
             detalle.setIdcompraDet(0);
        }
        compra.setCompraDetList(detCompra);
        
        actualizaCosto(compra);
        
        List<Object[]>  lobjt =  sb_inventario.compraToList(detCompra);
        
        /*tipo de pago*/
        /*Efectivo*/
        if(compra.getTipoPagoIdtipoPago().getIdtipoPago().equals(1)){
            compra.setSaldo(new BigDecimal("0"));
        }
        /*Credito*/
        if(compra.getTipoPagoIdtipoPago().getIdtipoPago().equals(2)){
            compra.setSaldo(compra.getTotal());
          
            compra.getProveedorIdproveedor().setSaldo(compra.getProveedorIdproveedor().getSaldo().add(compra.getSaldo()));
        }         
        
        //Registrar Entrada
       msg= sb_inventario.createDocumento(compra.getDocumento(), lobjt,"1");
        
        if(msg.equals("ok")){
            try{
                compra.getProveedorIdproveedor().getCompraList().add(compra);
                proveedorFacade.edit(compra.getProveedorIdproveedor());
               
             
            }catch(Exception ex){
              msg =ex.getMessage();
            }
            
        }else{
           
           return msg;
        }        
        
        return msg;
    }
    
    public String actualizaCosto(Compra compra){
        
        
        String msg = "error";
        
        for(CompraDet cd:compra.getCompraDetList()){
          
                Producto p  =productoFacade.find(cd.getProductoIdproducto().getIdproducto());

                BigDecimal existenciaNueva = new BigDecimal(cd.getCantidad() );
                BigDecimal existenciaAnterior = new BigDecimal(p.getExistencia());
                
            if(existenciaAnterior.compareTo(BigDecimal.ZERO)==0){
                p.setCosto(cd.getPrecio());
                p.setExistencia(cd.getCantidad());
                
            }else{
                BigDecimal vtatalExistencia = existenciaAnterior.add(existenciaNueva);

              
                BigDecimal totalCostoAnterio = p.getCosto().multiply( existenciaAnterior);
                BigDecimal totalCostoNuevo = cd.getPrecio().multiply(existenciaNueva);

            

                double nuevoCosto= totalCostoAnterio.doubleValue()+totalCostoNuevo.doubleValue();
             
                nuevoCosto = nuevoCosto/ vtatalExistencia.doubleValue();
              
                BigDecimal vcosto = new BigDecimal(nuevoCosto);
                vcosto = vcosto.setScale(2, RoundingMode.CEILING);
                p.setCosto(vcosto);
            }
            
                double vcostoFijo = (p.getCosto().doubleValue()*p.getCostoFijo()/100);
                double vprecio = (p.getCosto().doubleValue()*p.getMargen()/100)+p.getCosto().doubleValue();
                vprecio = vprecio +vcostoFijo;
                BigDecimal value = new BigDecimal(vprecio);
                value = value.setScale(2, RoundingMode.CEILING);

                
                p.setPrecio(value);
                productoFacade.edit(p);
            
            
        }
        msg ="ok";
        
    return msg;
    }
}
