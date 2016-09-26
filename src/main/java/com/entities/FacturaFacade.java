/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.entities;

import java.util.Date;
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
public class FacturaFacade extends AbstractFacade<Factura> {
    @PersistenceContext(unitName = "sysMMXPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public FacturaFacade() {
        super(Factura.class);
    }
    
public List<Factura> findByClienteFecha(Date fi, Date ff, Cliente client) {
        TypedQuery<Factura> q = null;
         System.out.println("fi:"+fi);
         System.out.println("ff:"+ff);
         System.out.println("client:"+client);
  
        if(null!=fi && client!=null){
             q = em.createNamedQuery("Factura.findByDocumentoFecha",Factura.class)
                .setParameter("fi", fi)
                .setParameter("ff", ff)
                .setParameter("cliente", client.getIdcliente());
        }        
        if(null==fi &&  client!=null){
             q = em.createNamedQuery("Factura.findByIdcliente",Factura.class)               
                .setParameter("cliente",  client.getIdcliente());
        }
       
        
        return q.getResultList();
    } 

    public List<Object[]> ventaActual(){
        Query q  = null;
        List<Object[]> lo = null;       
        String query="                     " +
        "   SELECT m.nombre ,sum(total) " +
        " FROM sysmmx.factura f, sysmmx.meses m" +
        " where  DATE_FORMAT(fecha, '%Y-%m-%d') between CONCAT(DATE_FORMAT(now(), '%Y') ,'-01-01') and DATE_FORMAT(now(), '%Y-%m-%d')" +
        " and DATE_FORMAT(fecha, '%m')  = m.idmes" +
        " group by m.nombre\n" +
        " order by   m.idmes  ";                             
        try{            
            q=  em.createNativeQuery(query);          
            lo= q.getResultList();
        }catch(Exception ex){
            lo= null;
            System.out.println("::::"+ex);
        }          
       return lo;        
  } 

    public List<Object[]> ventaAnterior(){
        Query q  = null;
        List<Object[]> lo = null;  
       
        String query=" SELECT m.nombre ,sum(total) " +
        " FROM sysmmx.factura f, sysmmx.meses m" +
        " where  DATE_FORMAT(fecha, '%Y-%m-%d') between CONCAT(DATE_FORMAT(DATE_SUB(CURDATE(), INTERVAL 365 DAY), '%Y') ,'-01-01') and DATE_FORMAT(DATE_SUB(CURDATE(), INTERVAL 365 DAY), '%Y-%m-%d')" +
        " and DATE_FORMAT(fecha, '%m')  = m.idmes" +
        " group by m.nombre" +
        " order by   m.idmes ";                             
        try{            
            q=  em.createNativeQuery(query);  
            
          
            lo= q.getResultList();
        }catch(Exception ex){
            lo= null;
            System.out.println("::::"+ex);
        }
            
       return lo;        
              
               
    }     
    
    public List<Object[]> ventaUsuario(){
        Query q  = null;
        List<Object[]> lo = null;  
       
        String query="SELECT u.usuario, sum(cantidad) FROM factura f, usuario u \n" +
            " where f.usuario_idusuario = u.idUsuario\n" +
            " and f.fecha between  date_add(NOW(), INTERVAL -30 DAY) and now()\n" +
            " group by u.usuario ";                             
        try{            
            q=  em.createNativeQuery(query);  
            
          
            lo= q.getResultList();
        }catch(Exception ex){
            lo= null;
            System.out.println("::::"+ex);
        }
            
       return lo;        
              
               
    } 
    
    public List<Factura> findByProducto(Producto p) {
       TypedQuery<Factura> q = null;
       q = em.createNamedQuery("Factura.findByProducto",Factura.class)
            .setParameter("producto", p.getIdproducto());
       return q.getResultList();
    }    
    
    public List<Factura> findByClientePendiente(Cliente c) {
       TypedQuery<Factura> q = null;
       q = em.createNamedQuery("Factura.findByClientePendiente",Factura.class)
            .setParameter("cliente", c.getIdcliente());
       return q.getResultList();
    }         
    
    
    
    

    
}
