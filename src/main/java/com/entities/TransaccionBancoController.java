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

@ManagedBean(name = "transaccionBancoController")
@SessionScoped
public class TransaccionBancoController implements Serializable {

    @EJB
    private com.entities.TransaccionBancoFacade ejbFacade;
    @EJB
    private com.ejb.SB_Banco sb_banco;    
    private List<TransaccionBanco> items = null;
    private TransaccionBanco selected;
    private Banco banco;
    private CuentaBanco cuentaBanco;

    public TransaccionBancoController() {
    }

    public CuentaBanco getCuentaBanco() {
        return cuentaBanco;
    }

    public void setCuentaBanco(CuentaBanco cuentaBanco) {
        this.cuentaBanco = cuentaBanco;
    }

    
    
    public Banco getBanco() {
        return banco;
    }

    public void setBanco(Banco banco) {
        this.banco = banco;
    }

    
    public TransaccionBanco getSelected() {
        return selected;
    }

    public void setSelected(TransaccionBanco selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private TransaccionBancoFacade getFacade() {
        return ejbFacade;
    }

    public TransaccionBanco prepareCreate() {
        selected = new TransaccionBanco(0);
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        
        String msg = sb_banco.agregarTransaccion(selected);
        if(msg.equals("ok")){
           JsfUtil.addErrorMessage("Transaccion creada correctamente");
        }else{
          JsfUtil.addErrorMessage("Surgio un error, " +msg);  
        }
        
        
        consulta();
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("TransaccionBancoUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("TransaccionBancoDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<TransaccionBanco> getItems() {
       
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

    public List<TransaccionBanco> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<TransaccionBanco> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = TransaccionBanco.class)
    public static class TransaccionBancoControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            TransaccionBancoController controller = (TransaccionBancoController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "transaccionBancoController");
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
            if (object instanceof TransaccionBanco) {
                TransaccionBanco o = (TransaccionBanco) object;
                return getStringKey(o.getIdtransaccionBanco());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), TransaccionBanco.class.getName()});
                return null;
            }
        }

    }
    
    public String consulta()
    {
        System.out.println("consulta1");
        System.out.println("banco-->"+banco);
        items= this.ejbFacade.findByBancoCuenta(banco,this.cuentaBanco);
        if(items.isEmpty()){
               JsfUtil.addErrorMessage("No se encontraron datos");
               
        }
        System.out.println("items---:"+items);
        System.out.println("consulta2");
        return "ok";
    }

}
