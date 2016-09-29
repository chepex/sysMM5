/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ejb;

import com.entities.Compra;
import com.entities.CompraDet;
import com.entities.Producto;
import java.math.BigDecimal;
import java.math.RoundingMode;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author mmixco
 */
@Stateless
public class SB_Compra {
    @EJB
    private com.entities.ProductoFacade productoFacade;  
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
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

                System.out.println("Total existencia:"+vtatalExistencia);
                BigDecimal totalCostoAnterio = p.getCosto().multiply( existenciaAnterior);
                BigDecimal totalCostoNuevo = cd.getPrecio().multiply(existenciaNueva);

                System.out.println("totalCostoAnterio:"+totalCostoAnterio);
                System.out.println("totalCostoNuevo:"+totalCostoNuevo);

                double nuevoCosto= totalCostoAnterio.doubleValue()+totalCostoNuevo.doubleValue();
                System.out.println("nuevoCosto:"+nuevoCosto);
                nuevoCosto = nuevoCosto/ vtatalExistencia.doubleValue();
                System.out.println("nuevoCosto:"+nuevoCosto);
                BigDecimal vcosto = new BigDecimal(nuevoCosto);
                vcosto = vcosto.setScale(2, RoundingMode.CEILING);
                p.setCosto(vcosto);
            }
            
            
            double vprecio = (p.getCosto().doubleValue()*p.getMargen()/100)+p.getCosto().doubleValue();
            BigDecimal value = new BigDecimal(vprecio);
            value = value.setScale(2, RoundingMode.CEILING);


            p.setPrecio(value);
            productoFacade.edit(p);
            
            
        }
        msg ="ok";
        
    return msg;
    }
}
