package com.entities;

import com.entities.util.JsfUtil;
import com.entities.util.JsfUtil.PersistAction;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@ManagedBean(name = "bodegaController")
@SessionScoped
public class BodegaController implements Serializable {

    @EJB
    private com.entities.BodegaFacade ejbFacade;
    @EJB
    private com.entities.LoginBean loginBean;
    
    
    private List<Bodega> items = null;
    private Bodega selected;

    public BodegaController() {
    }

    public Bodega getSelected() {
        return selected;
    }

    public void setSelected(Bodega selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
        selected.setIdbodega(0);
    }

    private BodegaFacade getFacade() {
        return ejbFacade;
    }

    public Bodega prepareCreate() throws MessagingException {
        selected = new Bodega();
        
        initializeEmbeddableKey();
        
 
        return selected;
    }

    public void create() {
        selected=  getFacade().auditCreate(selected);
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("BodegaCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        selected= getFacade().auditUpdate(selected);
        System.out.println("fecha update"+selected.getFechaUpdate());
        System.out.println("usuario update"+selected.getUsuarioUpdate());
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("BodegaUpdated"));
    }

    public void destroy() {
      
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("BodegaDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Bodega> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }

    private void persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            setEmbeddableKeys();
            try {
                if (persistAction != PersistAction.DELETE) {
                    getFacade().edit(selected);
                } else {
                    getFacade().remove(selected);
                }
                JsfUtil.addSuccessMessage(successMessage);
            } catch (EJBException ex) {
                String msg = "";
                Throwable cause = ex.getCause();
                if (cause != null) {
                    msg = cause.getLocalizedMessage();
                }
                if (msg.length() > 0) {
                    JsfUtil.addErrorMessage(msg);
                } else {
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            }
        }
    }

    public List<Bodega> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Bodega> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = Bodega.class)
    public static class BodegaControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            BodegaController controller = (BodegaController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "bodegaController");
            return controller.getFacade().find(getKey(value));
        }

        java.lang.Integer getKey(String value) {
            java.lang.Integer key;
            key = Integer.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Integer value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Bodega) {
                Bodega o = (Bodega) object;
                return getStringKey(o.getIdbodega());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Bodega.class.getName()});
                return null;
            }
        }

    }
    
   

}
