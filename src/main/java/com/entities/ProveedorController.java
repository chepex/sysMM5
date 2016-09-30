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
import org.primefaces.model.chart.LineChartModel;

@ManagedBean(name = "proveedorController")
@SessionScoped
public class ProveedorController implements Serializable {

    @EJB
    private com.entities.ProveedorFacade ejbFacade;
    private List<Proveedor> items = null;
    private Proveedor selected;
    private String nombre;
    private LineChartModel chartCompra;   
    @EJB
    private com.ejb.Sb_Grafica sb_Grafica;     
    public ProveedorController() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public LineChartModel getChartCompra() {
        return chartCompra;
    }

    public void setChartCompra(LineChartModel chartCompra) {
        this.chartCompra = chartCompra;
    }
    
    
    

    public Proveedor getSelected() {
        return selected;
    }

    public void setSelected(Proveedor selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
        selected.setIdproveedor(0);
    }

    private ProveedorFacade getFacade() {
        return ejbFacade;
    }

    public Proveedor prepareCreate() {
        selected = new Proveedor();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
         selected = this.getFacade().auditCreate(selected);
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("ProveedorCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
         selected = this.getFacade().auditUpdate(selected);
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("ProveedorUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("ProveedorDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Proveedor> getItems() {
       /* if (items == null) {
            items = getFacade().findAll();
        }*/
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

    public List<Proveedor> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Proveedor> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = Proveedor.class)
    public static class ProveedorControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ProveedorController controller = (ProveedorController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "proveedorController");
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
            if (object instanceof Proveedor) {
                Proveedor o = (Proveedor) object;
                return getStringKey(o.getIdproveedor());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Proveedor.class.getName()});
                return null;
            }
        }

    }
    
    public List<Proveedor> autoCompleteProveedor(String valor) {
        
        return getFacade().findByNombreCodigo(valor);
        
    }       
    
    public void consultaProveedor(){
        items = null;
       
            this.items = this.ejbFacade.findByNombreCodigo(nombre);
            if(items.isEmpty()){
             JsfUtil.addErrorMessage("No se encontraron registros");
            }
       
        
     this.selected= null;
 
     
       
    }    
    
    public void generaGrafica(){
        System.out.println("char--->"+chartCompra);
        this.chartCompra = sb_Grafica.graficaProveedor(selected);
        System.out.println("char--->"+chartCompra);
            
    }
    
    public void limpiar(){
        selected= null;
        items = null;
        chartCompra = null;
        nombre= null;
    
    }

}
