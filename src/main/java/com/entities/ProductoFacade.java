/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.entities;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
    
    public List<Object[]> compraProducto( int producto){
        Query q  = null;
        List<Object[]> lo = null;  
       
        String query="	SELECT  DATE_FORMAT(c.fecha, '%Y-%m-%d') fecha, sum(d.total)  total" +
        "	FROM sysmmx.compra c  ,sysmmx.compra_det d" +
        "	where c.idcompra = d.compra_idcompra" +
        "       and d.producto_idproducto = ?" +
        "	group by DATE_FORMAT(fecha, '%Y-%m-%d')" +
        "	order by DATE_FORMAT(fecha, '%Y-%m-%d')";                             
        try{            
            q=  em.createNativeQuery(query);  
            
            q.setParameter(1, producto);            
            /*q.setParameter(2, unidad);  */          
            lo= q.getResultList();
        }catch(Exception ex){
            lo= null;
            System.out.println("::::"+ex);
        }
            
       return lo;        
              
               
    }  
    
    public List<Object[]> ventaCliente( int cliente){
        Query q  = null;
        List<Object[]> lo = null;  
       
        String query="	 SELECT  DATE_FORMAT(f.fecha, '%Y-%m-%d') fecha, sum(d.total)  total " +
"                    FROM sysmmx.factura f  ,sysmmx.factura_det d " +
"                     where f.idfactura = d.factura_idfactura "+
"                     and f.cliente_idcliente = ?" +
"                     group by DATE_FORMAT(fecha, '%Y-%m-%d') " +
"                     order by DATE_FORMAT(fecha, '%Y-%m-%d')";                             
        try{            
            q=  em.createNativeQuery(query);  
            
            q.setParameter(1, cliente);            
            /*q.setParameter(2, unidad);  */          
            lo= q.getResultList();
        }catch(Exception ex){
            lo= null;
            System.out.println("::::"+ex);
        }
            
       return lo;        
              
               
    }    

    public List<Object[]> compraProveedor( int proveedor){
        Query q  = null;
        List<Object[]> lo = null;  
       
        String query="	SELECT  DATE_FORMAT(c.fecha, '%Y-%m-%d') fecha, sum(d.total)  total" +
        "	FROM sysmmx.compra c  ,sysmmx.compra_det d" +
        "	where c.idcompra = d.compra_idcompra" +
        "       and c.proveedor_idproveedor = ?" +
        "	group by DATE_FORMAT(fecha, '%Y-%m-%d')" +
        "	order by DATE_FORMAT(fecha, '%Y-%m-%d')";                             
        try{            
            q=  em.createNativeQuery(query);  
            
            q.setParameter(1, proveedor);            
            /*q.setParameter(2, unidad);  */          
            lo= q.getResultList();
        }catch(Exception ex){
            lo= null;
            System.out.println("::::"+ex);
        }
            
       return lo;        
              
               
    }     
        
    
    public List<Object[]> ventaProducto( int producto){
        Query q  = null;
        List<Object[]> lo = null;  
       
        String query=" SELECT  DATE_FORMAT(f.fecha, '%Y-%m-%d') fecha, sum(d.total)  total " +
"                    FROM sysmmx.factura f  ,sysmmx.factura_det d " +
"                     where f.idfactura = d.factura_idfactura "+
"                     and d.producto_idproducto = ?" +
"                     group by DATE_FORMAT(fecha, '%Y-%m-%d') " +
"                     order by DATE_FORMAT(fecha, '%Y-%m-%d')";                             
        try{            
            q=  em.createNativeQuery(query);  
            
            q.setParameter(1, producto);            
            /*q.setParameter(2, unidad);  */          
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
    
    public BigDecimal existenciaDia(){
        BigDecimal val=new BigDecimal("0");
       
        
        try{           
            Query q =  em.createNativeQuery(" SELECT IFNULL( sum(existencia) ,0) FROM  producto where activo= true" );  
            val = (BigDecimal)q.getSingleResult();
            val = val.setScale(2, RoundingMode.CEILING);
            
        }catch(Exception ex){
            
            val = new BigDecimal("0");
            
        }               
       
        return val;
       
    }   
    
    public BigDecimal existenciaMin(){
        BigDecimal val=new BigDecimal("0");
       
        
        try{           
            Query q =  em.createNativeQuery(" SELECT IFNULL( sum(existencia) ,0) FROM  producto  where activo= true and existencia <=  min" );  
            val = (BigDecimal)q.getSingleResult();
            val = val.setScale(2, RoundingMode.CEILING);
            
        }catch(Exception ex){
            
            val = new BigDecimal("0");
            
        }               
       
        return val;
       
    }    
    
    public BigDecimal existenciaMax(){
        BigDecimal val=new BigDecimal("0");
       
        
        try{           
            Query q =  em.createNativeQuery(" SELECT IFNULL( sum(existencia) ,0) FROM  producto  where activo= true and existencia  = max" );  
            val = (BigDecimal)q.getSingleResult();
            val = val.setScale(2, RoundingMode.CEILING);
            
        }catch(Exception ex){
            
            val = new BigDecimal("0");
            
        }               
       
        return val;
       
    }       
        
    
    
}
