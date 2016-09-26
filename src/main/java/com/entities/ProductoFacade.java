/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.entities;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * @author chepe
 */
@Stateless
public class ProductoFacade extends AbstractFacade<Producto> {
    @PersistenceContext(unitName = "sysMMXPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ProductoFacade() {
        super(Producto.class);
    }
    
    
    public List<Object[]> ventaProducto( ){
        Query q  = null;
        List<Object[]> lo = null;  
       
        String query="SELECT  DATE_FORMAT(f.fecha, '%Y-%m-%d') fecha, sum(d.total)  total \n" +
"                    FROM sysmmx.factura f  ,sysmmx.factura_det d \n" +
"                     where f.idfactura = d.factura_idfactura \n" +
"                     group by DATE_FORMAT(fecha, '%Y-%m-%d') \n" +
"                     order by DATE_FORMAT(fecha, '%Y-%m-%d')";                             
        try{            
            q=  em.createNativeQuery(query);  
            
            /*q.setParameter(1, plan);            
            q.setParameter(2, unidad);  */          
            lo= q.getResultList();
        }catch(Exception ex){
            lo= null;
            System.out.println("::::"+ex);
        }
            
       return lo;        
              
               
    }  
    
    public List<Producto> findByCategoria(Categoria cat) {
        TypedQuery<Producto> q = null;
         
             q = em.createNamedQuery("Producto.findByCategoria",Producto.class)               
                .setParameter("categoria", cat);
              
        return q.getResultList();
    } 
    
    public List<Producto> findByCategoriaNombre(Categoria cat,String nombre) {
        TypedQuery<Producto> q = null;
         
             q = em.createNamedQuery("Producto.findByCategoriaNombre",Producto.class)               
                .setParameter("categoria", cat)
                .setParameter("nombre", "%"+nombre+"%");
              
        return q.getResultList();
    }     
    
    public List<Producto> findByNombreCodigo(String  nombre) {
        TypedQuery<Producto> q = null;
         System.out.println("2nombre-->"+nombre);
             q = em.createNamedQuery("Producto.findByNombreCodigo",Producto.class)               
                .setParameter("nombre", "%"+nombre+"%");
              
        return q.getResultList();
    }     
    
    @Override
    public List<Producto> findAll() {
        TypedQuery<Producto> q = null;         
             q = em.createNamedQuery("Producto.findByActivo",Producto.class) ;   
        return q.getResultList();
    }     

    
    public List<Object[]> existenciaCategoria(){
        Query q  = null;
        List<Object[]> lo = null;  
       
        String query="  select c.nombre, sum(existencia) from producto p, categoria c\n" +
                " where p.categoria_idcategoria = c.idcategoria\n" +
                " group by c.nombre";                             
        try{            
            q=  em.createNativeQuery(query);            
            lo= q.getResultList();
        }catch(Exception ex){
            lo= null;
            System.out.println("::::"+ex);
        }
            
       return lo;        
              
               
    }    
    
    
}
