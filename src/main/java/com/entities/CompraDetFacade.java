/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.entities;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author chepe
 */
@Stateless
public class CompraDetFacade extends AbstractFacade<CompraDet> {
    @PersistenceContext(unitName = "sysMMXPU2")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CompraDetFacade() {
        super(CompraDet.class);
    }
    
    public  List<Object[]> finByMes(int anio, int mes, int producto){
        BigDecimal val=new BigDecimal("0");
       
              List<Object[]>  listOfSimpleEntities = new ArrayList<Object[]>();
        try{           
             Query q =  em.createNativeQuery(
                      " SELECT  d.producto_idproducto, sum(d.cantidad) c, sum(d.total) t " +
                        " FROM sysmmx.compra f , sysmmx.compra_det d  " +
                        " where d.compra_idcompra = f.idcompra" +
                        " and  YEAR(f.fecha)  = ?" +
                        " and MONTH (f.fecha) = ?" +
                        " and d.producto_idproducto = ?" +
                        " group by  d.producto_idproducto" ) ;
                 q.setParameter(1, anio);        
                 q.setParameter(2, mes);        
                 q.setParameter(3, producto);        
                
        listOfSimpleEntities  = q.getResultList();
            
        }catch(Exception ex){
            
         return null;
            
        }               
       
        return listOfSimpleEntities;
       
    }     
    
}
