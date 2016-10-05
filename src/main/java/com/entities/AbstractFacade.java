/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.entities;

import com.entities.util.JsfUtil;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.persistence.EntityManager;
 
/**
 *
 * @author chepe
 */
public abstract class AbstractFacade<T> {
    private Class<T> entityClass;
    @EJB
    private com.entities.LoginBean loginBean;    

    public AbstractFacade(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    protected abstract EntityManager getEntityManager();

    public void create(T entity)  {
        getEntityManager().persist(entity);
          getEntityManager().close();
    }
    
     public T  auditCreate(T entity)  {
      //  try {
         //   System.out.println("entidad ---->" + entity.getClass().getSimpleName());

                try{
                java.lang.reflect.Field usr = entity.getClass().getDeclaredField("usuarioCreate");
                usr.setAccessible(true);
                usr.set(entity, loginBean.ssuser());
                java.lang.reflect.Field creacion = entity.getClass().getDeclaredField("fechaCreate");            
                creacion.setAccessible(true);
                creacion.set(entity, new Date());
                }catch(Exception ex){
                
                }
                //saveLog(entity,lb.ssuser(),lb.sdate(),"CREATE");
                    
        /*}catch(IllegalArgumentException | IllegalAccessException | NoSuchFieldException ex ){
         
            System.out.print("error campo usuarioCreacion o fechaCreacion no existe clase"+entity.getClass().getSimpleName());
             JsfUtil.addErrorMessage("error campo usuario_creacion o fecha_creacion no existe clase"+entity.getClass().getSimpleName());
        }*/
        return entity;
    }
     
     public T  auditUpdate(T entity) {
     //   try {
         //   System.out.println("entidad ---->" + entity.getClass().getSimpleName());
            try{


                
                java.lang.reflect.Field usr = entity.getClass().getDeclaredField("usuarioUpdate");
                usr.setAccessible(true);
                usr.set(entity, loginBean.ssuser());
                java.lang.reflect.Field creacion = entity.getClass().getDeclaredField("fechaUpdate");            
                creacion.setAccessible(true);
                creacion.set(entity, new Date());
                //saveLog(entity,lb.ssuser(),lb.sdate(),"CREATE");
        }catch( IllegalArgumentException  ex) {
        
        } catch (IllegalAccessException ex) {
            Logger.getLogger(AbstractFacade.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchFieldException ex) {
            Logger.getLogger(AbstractFacade.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            Logger.getLogger(AbstractFacade.class.getName()).log(Level.SEVERE, null, ex);
        }                   
      /*  }catch(IllegalArgumentException | IllegalAccessException | NoSuchFieldException ex ){
         
            System.out.print("error campo usuarioCreacion o fechaCreacion no existe clase"+entity.getClass().getSimpleName());
             JsfUtil.addErrorMessage("error campo usuario_creacion o fecha_creacion no existe clase"+entity.getClass().getSimpleName());
        }*/
        return entity;
    }     

    public void edit(T entity) {
        getEntityManager().merge(entity);
       
    }

    public void remove(T entity) {
        getEntityManager().remove(getEntityManager().merge(entity));
    }

    public T find(Object id) {
        return getEntityManager().find(entityClass, id);
    }

    public List<T> findAll() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        return getEntityManager().createQuery(cq).getResultList();
    }

    public List<T> findRange(int[] range) {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        q.setMaxResults(range[1] - range[0] + 1);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }

    public int count() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        javax.persistence.criteria.Root<T> rt = cq.from(entityClass);
        cq.select(getEntityManager().getCriteriaBuilder().count(rt));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }
    
    
    protected void finalize(T entity)  { 
       
         getEntityManager().close();
         

    }      


}
