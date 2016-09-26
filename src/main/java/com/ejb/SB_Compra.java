/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ejb;

import com.entities.Compra;
import com.entities.CompraDet;
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
    private com.entities.ProductoFacade productoFacade;  
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    public String actualizaCosto(Compra compra){
        String msg = "error";
        
        for(CompraDet cd:compra.getCompraDetList()){
            cd.getProductoIdproducto().setCosto(cd.getPrecio());
            productoFacade.edit(cd.getProductoIdproducto());
        }
        msg ="ok";
        
    return msg;
    }
}
