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

@ManagedBean(name = "facturaController")
@SessionScoped
public class FacturaController implements Serializable {

    @EJB
    private com.entities.FacturaFacade ejbFacade;
    @EJB
    private com.entities.UsuarioFacade usuarioFacade; 
    @EJB
    private com.entities.FacturaDetFacade facturaDetFacade;     
    @EJB
    private com.entities.CorrelativoFacade correlativoFacade;    
    @EJB
    private com.ejb.SB_inventario sb_inventario;   
    @EJB
    private com.ejb.SB_Cliente sb_Cliente;     
    private List<Factura> items = null;
    private Factura selected;
    private List<FacturaDet> detFactura = new ArrayList<FacturaDet>();
    private Cliente cliente;
    private Date finicio;
    private Date ffinal;
    private Producto productoIdproducto;
    private int cantidad;
    private int existencia;    
    private BigDecimal precio;       

    public FacturaController() {
    }

    public int getExistencia() {
        return existencia;
    }

    public void setExistencia(int existencia) {
        this.existencia = existencia;
    }

    
    public List<FacturaDet> getDetFactura() {
        return detFactura;
    }

    public void setDetFactura(List<FacturaDet> detFactura) {
        this.detFactura = detFactura;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Date getFinicio() {
        return finicio;
    }

    public void setFinicio(Date finicio) {
        this.finicio = finicio;
    }

    public Date getFfinal() {
        return ffinal;
    }

    public void setFfinal(Date ffinal) {
        this.ffinal = ffinal;
    }

    public Producto getProductoIdproducto() {
        return productoIdproducto;
    }

    public void setProductoIdproducto(Producto productoIdproducto) {
        this.productoIdproducto = productoIdproducto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }
    
    

    public Factura getSelected() {
        return selected;
    }

    public void setSelected(Factura selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private FacturaFacade getFacade() {
        return ejbFacade;
    }

    public Factura prepareCreate() {
        selected = new Factura();
        selected.setFecha(new Date());
        Usuario usuario = usuarioFacade.find(1);
        this.productoIdproducto =null;
        selected.setUsuarioIdusuario(usuario);
        selected.setCantidad(0);
        selected.setTotal(new BigDecimal("0"));        
        selected.setIva(new BigDecimal("0"));        
        selected.setSubTotal(new BigDecimal("0"));        
        initializeEmbeddableKey();
        return selected;
    }

    public String  create() {
        
        
        if(detFactura.isEmpty()){
           JsfUtil.addErrorMessage("Agregue un producto");
           return "error";
           
       }
        /*si es pago al credito*/
        if(selected.getTipoPagoIdtipoPago().getIdtipoPago()!=1){        
        
            String vlimite = sb_Cliente.validaLimite(selected.getClienteIdcliente(), this.selected.getTotal());
            System.out.println("vlimite"+vlimite);
            if(!vlimite.equals("ok")){
               JsfUtil.addErrorMessage("El valor de la compra ha sobre pasado el limite de credito del cliente");
               return "error";
            }
        }
        
        List<Correlativo> lcort = correlativoFacade.findByNombre("Factura");
        if(!lcort.isEmpty()){
            Correlativo c= lcort.get(0);
            int nuevoValor= c.getValorActual()+1;
            String vcorrelativo = c.getPrefijo()+nuevoValor;
            c.setValorActual(nuevoValor);
            correlativoFacade.edit(c);
            selected.setDocumento(vcorrelativo);
        
        }else{
            selected.setDocumento("No corelt");
        }
        for(FacturaDet detalle :detFactura){
            BigDecimal vid= facturaDetFacade.finById();
            detalle.setIdfacturaDet(vid.intValue());
            
        }
       
        selected.setFacturaDetList(detFactura);  
        
        
        List<Object[]>  lobjt =  sb_inventario.facturaToList(detFactura);
        
        //Registrar Salida
        sb_inventario.createDocumento(selected.getDocumento(), lobjt, "2");
        selected = this.getFacade().auditCreate(selected);
        sb_Cliente.actualizaSaldo(selected.getClienteIdcliente(), selected.getTotal());
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("FacturaCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
        
        return "ok";
    }

    public void update() {
         selected = this.getFacade().auditUpdate(selected);
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("FacturaUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("FacturaDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Factura> getItems() {
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

    public List<Factura> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Factura> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = Factura.class)
    public static class FacturaControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            FacturaController controller = (FacturaController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "facturaController");
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
            if (object instanceof Factura) {
                Factura o = (Factura) object;
                return getStringKey(o.getIdfactura());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Factura.class.getName()});
                return null;
            }
        }

    }
    
    public void limpiar(){
        selected= null;
         this.detFactura =  new ArrayList<FacturaDet>();
        this.cantidad =0;
        this.precio = new BigDecimal("0");
    }   
    
    public String addDetalle(){
        
        String msg="";
        
        if(this.productoIdproducto==null){
           JsfUtil.addErrorMessage("Selecione un producto");
           return "error";
        }
        
        if(this.cantidad==0||cantidad<0){
         JsfUtil.addErrorMessage("Digite una cantidad valida");
            return "error";
        }
        
        if(this.precio.compareTo(new BigDecimal(0))==-1){
         JsfUtil.addErrorMessage("Digite una precio valida");
            return "error";
        }       
       
        msg= this.sb_inventario.validaSalida(productoIdproducto, cantidad);        
        if(!msg.equals("ok")){
           JsfUtil.addErrorMessage(msg);
           return "error";
        }        
        
       int id= 1;
       if(detFactura!= null){
        id= detFactura.size()+1;
       }
        if(!detFactura.isEmpty()){
            for(FacturaDet df :detFactura ){
                if(df.getProductoIdproducto().equals(this.productoIdproducto)){
                    JsfUtil.addErrorMessage("Este producto ya fue adicionado");
                    return "error";
                }
            }
        }
            
        FacturaDet detalle = new FacturaDet();
        detalle.setIdfacturaDet(id);
        detalle.setFacturaIdfactura(selected);
        detalle.setCantidad(cantidad);
        detalle.setPrecio(precio);
        detalle.setTotal(precio.multiply(new BigDecimal(cantidad)));
        
        detalle.setProductoIdproducto(productoIdproducto);
        
        selected.setCantidad(selected.getCantidad()+cantidad);
        selected.setSubTotal(selected.getSubTotal().add(precio.multiply(new BigDecimal(cantidad))));
        selected.setIva(selected.getSubTotal().multiply(new BigDecimal(".13")));        
        selected.setTotal(selected.getSubTotal().add(selected.getIva()));
        this.detFactura.add(detalle);
        this.cantidad =1;
        this.precio = new BigDecimal("0");
        this.productoIdproducto = null;
        this.existencia=0;       

        return "error";
    }    
    
   public void buscar(){
            items = this.ejbFacade.findByClienteFecha(finicio, ffinal, cliente);
    }    
   
   public void selecionar(){
        if(selected.getFacturaDetList() !=null){
            this.detFactura = selected.getFacturaDetList();
        }
    
    }   

   public void updateExistencia(){
       this.existencia =  this.productoIdproducto.getExistencia();   
       this.precio = this.productoIdproducto.getPrecio();
   }

}
