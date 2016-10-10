package com.entities;

import com.entities.util.JsfUtil;
import com.entities.util.JsfUtil.PersistAction;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
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
    

    @EJB
    private com.entities.TransaccionCajaFacade transaccionCajaFacade;    
    private TransaccionCaja selectedTransaccion;
    private List<CajaChica> items = null;
    private CajaChica selected;
    private Producto producto;
    private BigDecimal monto;
    private String descripcion;
    private String referencia;
    private Date vfecha;
    private List<TransaccionCaja> ltransaccion = new ArrayList<TransaccionCaja>();

    public CajaChicaController() {
    }

    public TransaccionCaja getSelectedTransaccion() {
        return selectedTransaccion;
    }

    public void setSelectedTransaccion(TransaccionCaja selectedTransaccion) {
        this.selectedTransaccion = selectedTransaccion;
    }

    
    
    public Date getVfecha() {
        return vfecha;
    }

    public void setVfecha(Date vfecha) {
        this.vfecha = vfecha;
    }

    
    
    public List<TransaccionCaja> getLtransaccion() {
        return ltransaccion;
    }

    public void setLtransaccion(List<TransaccionCaja> ltransaccion) {
        this.ltransaccion = ltransaccion;
    }

    
    
    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }
    
    

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }



    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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
        
        List<CajaChica> lcaja2 =this.ejbFacade.findByAbierta();
        
        
        if(!lcaja.isEmpty()){
            JsfUtil.addErrorMessage("Ya se ha creado una caja chica en esta fecha");
            return "error";
        }
        
        if(!lcaja2.isEmpty()){
            JsfUtil.addErrorMessage("Existe una caja abierta, favor cerrarla antes de continuar fecha: "+lcaja2.get(0).getFecha());
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
    
    public String addDetalle(){
        String msg = "ok";
        TransaccionCaja tc = new TransaccionCaja ();
        tc.setIdtransaccion(0);
        tc.setFecha(new Date());
        tc.setDescripcion(descripcion);
        tc.setDocReferencia(referencia);
        tc.setSubtotal(monto);
        tc.setTotal(monto);
        tc.setIdproducto(producto);
        tc.setIdcajaChica(selected);  
        this.selected.getTransaccionCajaList().add(tc);
        this.ejbFacade.edit(selected);
         this.selected = ejbFacade.find(selected.getIdcajaChica());
         
                 this.monto =new BigDecimal(0);
        this.referencia ="";
        this.producto = null;
        this.descripcion = "";
        // ltransaccion =  selected.getTransaccionCajaList();
        return msg;
    
    }
    
    public String buscar(){
        String msg= "ok";
        List<CajaChica> cc =  this.ejbFacade.findByFecha(vfecha);
        if(cc.isEmpty()){        
            JsfUtil.addErrorMessage("No se encontro caja chica con esta fecha");  
        }else{
            selected = cc.get(0);                
            ltransaccion =  selected.getTransaccionCajaList();
        }        
            JsfUtil.addSuccessMessage("Consulta realizada correctamente");
        
        return msg;
    
    }
    
    
    public String eliminar(){
        String msg = "ok";
        
        
        //ltransaccion.remove(this.selectedTransaccion) ;
        selected.getTransaccionCajaList().remove(selectedTransaccion);
        this.transaccionCajaFacade.remove(selectedTransaccion);        
        //this.ejbFacade.edit(selected);
        JsfUtil.addErrorMessage("Registro eliminado correctamente");  
        return msg;
    
    }
    
    public String limpiar(){
        String msg = "ok";
        this.selected = null;
        this.selectedTransaccion = null;
        this.monto =new BigDecimal(0);
        this.referencia ="";
        this.producto = null;
        this.descripcion = "";
        
        
        return msg;
    }
    
    public String cerrar(){
        String msg = "ok";
        selected.setAbierta(false);
        BigDecimal total = new BigDecimal("0");
        for(TransaccionCaja tc : selected.getTransaccionCajaList()){
            total = total.add(tc.getTotal());        
        }
        
        selected.setMontoFinal(total);
        this.ejbFacade.edit(selected);
         JsfUtil.addSuccessMessage("Cierre de caja ejecutado correctamente");
        return msg;
    
    }

}
