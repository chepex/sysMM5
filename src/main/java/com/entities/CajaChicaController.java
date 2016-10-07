package com.entities;

import com.entities.util.JsfUtil;
import com.entities.util.JsfUtil.PersistAction;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
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

@ManagedBean(name = "cajaChicaController")
@SessionScoped
public class CajaChicaController implements Serializable {

    @EJB
    private com.entities.CajaChicaFacade ejbFacade;
    private List<CajaChica> items = null;
    private CajaChica selected;

    public CajaChicaController() {
    }

    public CajaChica getSelected() {
        return selected;
    }

    public void setSelected(CajaChica selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private CajaChicaFacade getFacade() {
        return ejbFacade;
    }

    public CajaChica prepareCreate() {
        selected = new CajaChica();
        selected.setFecha(new Date());
        selected.setAbierta(true);
        selected.setIdcajaChica(0);
        selected.setSaldo(BigDecimal.ZERO);
        selected.setMontoFinal(BigDecimal.ZERO);
        initializeEmbeddableKey();
        return selected;
    }

    public String  create() {
        String msg = "ok";
         
        List<CajaChica> lcaja =this.ejbFacade.findByFecha(selected.getFecha());
        
        if(!lcaja.isEmpty()){
            JsfUtil.addErrorMessage("Ya se ha creado una caja chica en esta fecha");
            return "error";
        }
        selected.setSaldo(selected.getMontoInicial());
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("CajaChicaCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
       
        return msg;
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("CajaChicaUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("CajaChicaDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<CajaChica> getItems() {
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

    public List<CajaChica> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<CajaChica> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = CajaChica.class)
    public static class CajaChicaControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            CajaChicaController controller = (CajaChicaController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "cajaChicaController");
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
            if (object instanceof CajaChica) {
                CajaChica o = (CajaChica) object;
                return getStringKey(o.getIdcajaChica());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), CajaChica.class.getName()});
                return null;
            }
        }

    }

}
