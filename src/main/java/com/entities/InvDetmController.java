package com.entities;

import com.entities.util.JsfUtil;
import com.entities.util.JsfUtil.PersistAction;

import java.io.Serializable;
import java.util.List;
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

@ManagedBean(name = "invDetmController")
@SessionScoped
public class InvDetmController implements Serializable {

    @EJB
    private com.entities.InvDetmFacade ejbFacade;
    private List<InvDetm> items = null;
    private InvDetm selected;
    private int existencia;
    private Producto producto ;

    public InvDetmController() {
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    
    
    public int getExistencia() {
        return existencia;
    }

    public void setExistencia(int existencia) {
        this.existencia = existencia;
    }

    
    
    
    

    public InvDetm getSelected() {
        return selected;
    }

    public void setSelected(InvDetm selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private InvDetmFacade getFacade() {
        return ejbFacade;
    }

    public InvDetm prepareCreate() {
        selected = new InvDetm();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("InvDetmCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("InvDetmUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("InvDetmDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<InvDetm> getItems() {
      /*  if (items == null) {
            items = getFacade().findAll();
        }*/
        this.existencia=0;
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

    public List<InvDetm> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<InvDetm> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = InvDetm.class)
    public static class InvDetmControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            InvDetmController controller = (InvDetmController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "invDetmController");
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
            if (object instanceof InvDetm) {
                InvDetm o = (InvDetm) object;
                return getStringKey(o.getIdinvDetm());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), InvDetm.class.getName()});
                return null;
            }
        }

    }
    
    public int  addExistencia(InvDetm mov){
        if(mov.getInvMovIdinvMov().getDocumentoIddocumento().getSumaResta().equals("S")){
            this.existencia = existencia +mov.getCantidad();
             return this.existencia;
        }
        if(mov.getInvMovIdinvMov().getDocumentoIddocumento().getSumaResta().equals("R")){
            this.existencia = existencia -mov.getCantidad();
             return this.existencia;
        }    
    
        return this.existencia;
    }
    
    public void consulta(){    
        this.existencia =0;
        this.items = this.ejbFacade.findByNombre(this.producto);
        
    }
    
    
}
