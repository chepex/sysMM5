package com.entities;

import com.entities.util.JsfUtil;
import com.entities.util.JsfUtil.PersistAction;

import java.io.Serializable;
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

@ManagedBean(name = "pagoFacturaController")
@SessionScoped
public class PagoFacturaController implements Serializable {

    @EJB
    private com.entities.PagoFacturaFacade ejbFacade;
    @EJB
    private com.entities.FacturaFacade facturaFacade; 
    @EJB
    private com.entities.ClienteFacade clienteFacade;     
    @EJB
    private com.entities.CuentaBancoFacade cuentaBancoFacade;      
    private List<PagoFactura> items = null;
    private PagoFactura selected;
    private Cliente cliente;
    private List<Factura> lfactura ;
    private Factura selectedFactura ;
    private List<CuentaBanco> lcuentaBanco = null;     

    public PagoFacturaController() {
    }

    public List<CuentaBanco> getLcuentaBanco() {
        return lcuentaBanco;
    }

    public void setLcuentaBanco(List<CuentaBanco> lcuentaBanco) {
        this.lcuentaBanco = lcuentaBanco;
    }

    
    public Factura getSelectedFactura() {
        return selectedFactura;
    }

    public void setSelectedFactura(Factura selectedFactura) {
        this.selectedFactura = selectedFactura;
    }

    
    
    public List<Factura> getLfactura() {
        return lfactura;
    }

    public void setLfactura(List<Factura> lfactura) {
        this.lfactura = lfactura;
    }

    
    
    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    
    
    public PagoFactura getSelected() {
        return selected;
    }

    public void setSelected(PagoFactura selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
        this.selected.setIdpagoFactura(0);
    }

    private PagoFacturaFacade getFacade() {
        return ejbFacade;
    }

    public PagoFactura prepareCreate() {
        selected = new PagoFactura();        
        this.lcuentaBanco = null;
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        
        selected.setFecha(new Date());
        
        selectedFactura.setSaldo(selectedFactura.getSaldo().subtract(selected.getValor()));
     
        selectedFactura.getClienteIdcliente().setSaldo(selectedFactura.getClienteIdcliente().getSaldo().subtract(selected.getValor()));
        selected.setFacturaIdfactura(selectedFactura);
        
        clienteFacade.edit(selectedFactura.getClienteIdcliente());
        facturaFacade.edit(selectedFactura);
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("PagoFacturaCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
        consulta();
        
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("PagoFacturaUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("PagoFacturaDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<PagoFactura> getItems() {
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

    public List<PagoFactura> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<PagoFactura> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = PagoFactura.class)
    public static class PagoFacturaControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            PagoFacturaController controller = (PagoFacturaController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "pagoFacturaController");
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
            if (object instanceof PagoFactura) {
                PagoFactura o = (PagoFactura) object;
                return getStringKey(o.getIdpagoFactura());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), PagoFactura.class.getName()});
                return null;
            }
        }

    }
    
    public void consulta(){
     lfactura = facturaFacade.findByClientePendiente(this.cliente);
    }
    
    public void consultaPendiente(){
        System.out.println(" aui--->");
        
       
        lfactura = facturaFacade.findByClientePendiente(this.cliente);
        System.out.println(" lfactura--->"+lfactura);
        System.out.println(" selectedFactura--->"+selectedFactura);
        items = null;
        System.out.println(" items--->"+items);
        if(lfactura.isEmpty()){
            JsfUtil.addErrorMessage("No se encontraron facturas pendientes");
        }
         this.selectedFactura = null;
   
    }      

    public void consultaCuenta(){
   
    lcuentaBanco = cuentaBancoFacade.findByIdbanco(this.selected.getBancoIdbanco());
        if(lcuentaBanco.isEmpty()){
         JsfUtil.addErrorMessage("No se encontraron cuentas asociados a ese banco");
        }
   
    }      


    public void consultaPagos(){
                System.out.println("items-->"+items);
        items = null;
        try{
            if(this.selectedFactura!=null){
                items= this.ejbFacade.findByCompra(this.selectedFactura);
            }
        }catch(Exception ex){
            items= null;
        }
        
                System.out.println("items-->"+items);

        
    }

}
