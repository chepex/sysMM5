/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ejb;


import com.entities.Bodega;
import com.entities.Compra;
import com.entities.CompraDet;
import com.entities.Documento;
import com.entities.FacturaDet;

import com.entities.InvDetm;
import com.entities.InvMov;
import com.entities.Producto;
import com.entities.Usuario;
import com.entities.util.JsfUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author mmixco
 */
@Stateless
public class SB_inventario {
    
    @EJB
    private com.entities.ProductoFacade productoFacade;     
    @EJB
    private com.entities.InvMovFacade invMovFacade;   
    @EJB
    private com.entities.DocumentoFacade documentoFacade;     
    

   public String actulizaExistencia(InvMov inv){
       
        for(InvDetm d : inv.getInvDetmList()){
            int existencia = d.getProductoIdproducto().getExistencia();
            int cantidadMovimiento = d.getCantidad();
            
            if(inv.getDocumentoIddocumento().getSumaResta().equals("S")){
                d.getProductoIdproducto().setExistencia(existencia+cantidadMovimiento);
            }
            if(inv.getDocumentoIddocumento().getSumaResta().equals("R")){
                d.getProductoIdproducto().setExistencia(existencia-cantidadMovimiento);
            
            } 
            productoFacade.edit(d.getProductoIdproducto());        
            
            
        }
       
       return "ok";
   
   }
   
   public String ingresoCompra(Compra compra){
       
        List<InvDetm> detInv = new ArrayList<InvDetm>(); 
        
        Documento doc = documentoFacade.find(1);
        InvMov inv = new InvMov(1);
        inv.setFecha(new Date());
        inv.setBodegaIdbodega(new Bodega(1));
        inv.setDocumentoIddocumento(doc);
        inv.setNumReferencia(compra.getDocumento() );
        inv.setUsuarioIdusuario(new Usuario(1));
        
        
        for(CompraDet d: compra.getCompraDetList()){
             InvDetm invd = new InvDetm(1);
             invd.setCantidad(d.getCantidad());
             invd.setInvMovIdinvMov(inv);
             invd.setProductoIdproducto(d.getProductoIdproducto());
             detInv.add(invd);
        }
        inv.setInvDetmList(detInv);
        actulizaExistencia( inv);
        
        invMovFacade.edit(inv);
       
        return "ok";
   }
   
   public String createDocumento(String numReferencia,  List<Object[]> lObject, String documento){
       
       /*
       Object 
       1- idProducto 
       2- cantidad       
       */
       System.out.println("referencia-->"+numReferencia);
       System.out.println("documento-->"+documento);
   
            List<InvDetm> detInv = new ArrayList<InvDetm>(); 

            Documento doc = documentoFacade.find(Integer.parseInt(documento));
            if(doc==null){
                return "Favor crear un documento de ingreso";
            }
            InvMov inv = new InvMov(1);
            inv.setFecha(new Date());
            inv.setBodegaIdbodega(new Bodega(1));
            inv.setDocumentoIddocumento(doc);
            inv.setNumReferencia(numReferencia );
            inv.setUsuarioIdusuario(new Usuario(1));


            for(Object[] obj: lObject){
                 InvDetm invd = new InvDetm(1);
                 String idP = obj[0].toString();
                 String cant = obj[1].toString();
                 System.out.println("idProducto"+idP);
                 System.out.println("cantidad"+cant);
                 Producto p= productoFacade.find(Integer.parseInt( idP));
                 invd.setCantidad( Integer.parseInt(cant));
                 invd.setInvMovIdinvMov(inv);
                 invd.setProductoIdproducto(p);
                 detInv.add(invd);
            }
            inv.setInvDetmList(detInv);
            actulizaExistencia( inv);

            invMovFacade.edit(inv);
   
       
       
        return "ok";
   }   
   
   public  List<Object[]>  compraToList(List<CompraDet> detCompra){
       List<Object[]> lbject = new ArrayList<Object[]>(); 
       
       for(CompraDet d :detCompra){
           Object[] obj =  new Object[2];
           obj[0] = d.getProductoIdproducto().getIdproducto();
           obj[1] =d.getCantidad();
            lbject.add(obj);
       }
       
       return lbject ;
   
   }
   
    public  List<Object[]>  facturaToList(List<FacturaDet> detFactura){
       List<Object[]> lbject = new ArrayList<Object[]>(); 
       
       for(FacturaDet d :detFactura){
           Object[] obj =  new Object[2];
           obj[0] = d.getProductoIdproducto().getIdproducto();
           obj[1] =d.getCantidad();
            lbject.add(obj);
       }
       
       return lbject ;
   
   }  
    
    public String validaEntrada(Producto p, int cantidad){
        
           if( (cantidad+p.getExistencia())>p.getMax() ){
                return"La cantidad excede el maximo establecido, maximo= "+p.getMax();
                
            }   
            return "ok";
    }
    
    public String validaSalida(Producto p, int cantidad){
        
        
      if(cantidad>p.getExistencia()){
                
                return "La cantidad no puede ser mayor a la existencia";
            }
            if( (p.getExistencia()-cantidad)<p.getMin()){
               
                return "La cantidad excede el minimo establecido, minimo= "+p.getMin();
            }       
            return "ok";
    }    

}
