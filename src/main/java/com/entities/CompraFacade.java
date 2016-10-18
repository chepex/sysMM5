/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.entities;

import java.util.Calendar;
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
public class CompraFacade extends AbstractFacade<Compra> {
    @PersistenceContext(unitName = "sysMMXPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CompraFacade() {
        super(Compra.class);
    }
    
    public List<Compra> findByProducto(Producto p) {
       TypedQuery<Compra> q = null;
       q = em.createNamedQuery("Compra.findByProducto",Compra.class)
            .setParameter("producto", p.getIdproducto());

       return q.getResultList();
    }    
    
    public List<Compra> findByProveedorPendiente(Proveedor p) {
       TypedQuery<Compra> q = null;
       q = em.createNamedQuery("Compra.findByProveedorPendiente",Compra.class)
            .setParameter("proveedor", p.getIdproveedor());

       return q.getResultList();
    }       
    
    public List<Compra> findByOrdenId(Date fi, Date ff, Proveedor prov) {
        TypedQuery<Compra> q = null;
        System.out.println("fi-->"+fi);
        System.out.println("ff-->"+ff);
        System.out.println("prov-->"+prov);
        if(prov==null){
                 q = em.createNamedQuery("Compra.findByFecha",Compra.class)
                .setParameter("fi", fi)
                .setParameter("ff", ff);
        } 
        if(null!=fi && prov!=null){
             q = em.createNamedQuery("Compra.findByFechaProv",Compra.class)
                .setParameter("fi", fi)
                .setParameter("ff", ff)
                .setParameter("prov", prov.getIdproveedor());
        }        
        if(null==fi){
             q = em.createNamedQuery("Compra.findByProveedor",Compra.class)               
                .setParameter("prov", prov.getIdproveedor());
        }
       
        
        return q.getResultList();
    }    
    
    public List<Compra> ListaCompraMes2(){
       
        List<Compra> lo = null;       
        Date dia  = new Date();
        Calendar cal = Calendar.getInstance();
        // 1 de septiembre
        cal.set(Calendar.DATE, 1);
        cal.set(Calendar.MONTH, dia.getMonth());
        cal.set(Calendar.YEAR, dia.getYear());
        Date fi =  cal.getTime();
        TypedQuery<Compra> q = null;
        System.out.println("INICIO:"+fi);
        System.out.println("FIN:"+dia);
        
                 q = em.createNamedQuery("Compra.findByFecha",Compra.class)
                .setParameter("fi", fi)
                .setParameter("ff", dia);
        
                lo = q.getResultList();
                       
        
       return lo;        
    } 
    
    public List<Compra> ListaCompraMes(){
        Query q  = null;
        List<Compra> lo = null;       
        Date dia  = new Date();
        Calendar cal = Calendar.getInstance();
        // 1 de septiembre
        cal.set(Calendar.DATE, 1);
        cal.set(Calendar.MONTH, dia.getMonth());
        cal.set(Calendar.YEAR, dia.getYear());
        
        String query="                     " +
        "   SELECT f.*" +
        " FROM sysmmx.compra f " +
        " where  DATE_FORMAT(fecha, '%Y-%m-%d') between CONCAT(DATE_FORMAT(CURDATE(), '%Y-%m-') , '01') and DATE_FORMAT(CURDATE(), '%Y-%m-%d')  " ;        
        try{            
            q=  em.createNativeQuery(query,Compra.class);          
            lo= q.getResultList();
        }catch(Exception ex){
            lo= null;
            System.out.println("::::"+ex);
        }          
       return lo;        
    }     
    
    public List<Object[]> compraMes(){
        Query q  = null;
        List<Object[]> lo = null;       
        String query="                     " +
        "   SELECT m.nombre ,sum(total) " +
        " FROM sysmmx.compra f, sysmmx.meses m" +
        " where  DATE_FORMAT(fecha, '%Y-%m-%d') between CONCAT(DATE_FORMAT(CURDATE(), '%Y-%m-') , '01') and DATE_FORMAT(CURDATE(), '%Y-%m-%d')  " +
        " and DATE_FORMAT(fecha, '%m')  = m.idmes" +
        " group by m.nombre " +
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
    
    public List<Object[]> compraActual(){
        Query q  = null;
        List<Object[]> lo = null;       
        String query="                     " +
        "   SELECT m.nombre ,sum(total) " +
        " FROM sysmmx.compra f, sysmmx.meses m" +
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
    
    public List<Object[]> compraAnterior(){
        Query q  = null;
        List<Object[]> lo = null;  
       
        String query=" SELECT m.nombre ,sum(total) " +
        " FROM sysmmx.compra f, sysmmx.meses m" +
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
    
}
