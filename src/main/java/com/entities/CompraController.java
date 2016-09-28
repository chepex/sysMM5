package com.entities;

import com.entities.util.JsfUtil;
import com.entities.util.JsfUtil.PersistAction;
import com.entities.util.ManejadorFechas;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
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

@ManagedBean(name = "compraController")
@SessionScoped
public class CompraController implements Serializable {

    @EJB
    private com.entities.CompraFacade ejbFacade;
    @EJB
    private com.entities.CorrelativoFacade correlativoFacade;   
    @EJB
    private com.entities.ProveedorFacade proveedorFacade;      
    @EJB
    private com.entities.UsuarioFacade usuarioFacade;    
    @EJB
    private com.ejb.SB_Compra sb_Compra; 
    @EJB
    private com.ejb.SB_inventario sb_inventario;     
    
    private List<Compra> items = null;
    private Compra selected;
    private List<CompraDet> detCompra = new ArrayList<CompraDet>();
    private Proveedor proveedor;
    private Date finicio;
    private Date ffinal;
    private Producto productoIdproducto;
    private int cantidad;
    private BigDecimal precio;  
    private int existencia;      
    
    

    public CompraController() {
    }

    public int getExistencia() {
        return existencia;
    }

    public void setExistencia(int existencia) {
        this.existencia = existencia;
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
    
    

    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }
    
    

    public List<CompraDet> getDetCompra() {
        return detCompra;
    }

    public void setDetCompra(List<CompraDet> detCompra) {
        this.detCompra = detCompra;
    }

    
    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    
    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    

    public Producto getProductoIdproducto() {
        return productoIdproducto;
    }

    public void setProductoIdproducto(Producto productoIdproducto) {
        this.productoIdproducto = productoIdproducto;
    }
    
    

    public Compra getSelected() {
        return selected;
    }

    public void setSelected(Compra selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private CompraFacade getFacade() {
        return ejbFacade;
    }

  
    
    public Compra prepareCreate() {
        
        selected = new Compra();
        selected.setFecha(new Date());
        Usuario usuario = usuarioFacade.find(1);
        this.productoIdproducto=null;
        selected.setUsuarioIdusuario(usuario);
        selected.setCantidad(0);
        selected.setTotal(new BigDecimal("0"));
        selected.setSubTotal(new BigDecimal("0"));
        selected.setIva(new BigDecimal("0"));
        initializeEmbeddableKey();
        return selected;
    }

    public String create() {
        
        if(selected.getProveedorIdproveedor()==null){
                JsfUtil.addErrorMessage("Selecione un proveedor");
                return "error";
        }
        
        if(detCompra.isEmpty()){
            JsfUtil.addErrorMessage("Agregue un producto");
            return "error";

        }
       
        if(this.selected.getDocumento().isEmpty()){
             JsfUtil.addErrorMessage("Agregue un numero de factura");
            return "error";
        }
       
        for(CompraDet detalle :detCompra){
             detalle.setIdcompraDet(0);
            System.out.println("idDetakke--->"+detalle.getIdcompraDet());
        }
        selected.setCompraDetList(detCompra);
        sb_Compra.actualizaCosto(selected);
        List<Object[]>  lobjt =  sb_inventario.compraToList(detCompra);
        //sb_inventario.ingresoCompra(selected);
        
        /*tipo de pago*/
        /*Efectivo*/
        if(selected.getTipoPagoIdtipoPago().getIdtipoPago().equals(1)){
            selected.setSaldo(new BigDecimal("0"));
        }
        /*Credito*/
        if(selected.getTipoPagoIdtipoPago().getIdtipoPago().equals(2)){
            selected.setSaldo(this.selected.getTotal());
            System.out.println("saldo--->"+selected.getSaldo());
            System.out.println("saldo-->"+selected.getProveedorIdproveedor().getSaldo());
            selected.getProveedorIdproveedor().setSaldo(selected.getProveedorIdproveedor().getSaldo().add(selected.getSaldo()));
            System.out.println("proveedor"+selected.getProveedorIdproveedor());
            System.out.println("saldo-->"+selected.getProveedorIdproveedor().getSaldo());
            
        }        
        
        proveedorFacade.edit(selected.getProveedorIdproveedor());
        //Registrar Entrada
        String msgDocumento = sb_inventario.createDocumento(selected.getDocumento(), lobjt,"1");
        
        if(msgDocumento.equals("ok")){
            selected = this.getFacade().auditCreate(selected);
        
            persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("CompraCreated"));
            if (!JsfUtil.isValidationFailed()) {
                items = null;    // Invalidate list of items to trigger re-query.
            }
        }else{
             JsfUtil.addErrorMessage(msgDocumento);
           return "error";
        }
        
        
        return "ok";
    }

    public void update() {
        selected = this.getFacade().auditUpdate(selected);
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("CompraUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("CompraDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Compra> getItems() {
        /*if (items == null) {
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

    public List<Compra> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Compra> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = Compra.class)
    public static class CompraControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            CompraController controller = (CompraController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "compraController");
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
            if (object instanceof Compra) {
                Compra o = (Compra) object;
                return getStringKey(o.getIdcompra());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Compra.class.getName()});
                return null;
            }
        }

    }
    
    public String addDetalle(){
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
        
        String msg = this.sb_inventario.validaEntrada(productoIdproducto, cantidad);        
        if(!msg.equals("ok")){
           JsfUtil.addErrorMessage(msg);
           return "error";
        }
        
       int id= 1;
       if(detCompra!= null){
       id= detCompra.size()+1;
       }
        if(!detCompra.isEmpty()){
            for(CompraDet df :detCompra ){
                if(df.getProductoIdproducto().equals(this.productoIdproducto)){
                    JsfUtil.addErrorMessage("Este producto ya fue adicionado");
                    return "error";
                }
            }
        }        
        CompraDet detalle = new CompraDet();
        detalle.setIdcompraDet(id);
        detalle.setCompraIdcompra(selected);
        detalle.setCantidad(cantidad);
        detalle.setPrecio(precio);
        detalle.setTotal(precio.multiply(new BigDecimal(cantidad)));
        
        
        detalle.setProductoIdproducto(productoIdproducto);
        
        selected.setCantidad(selected.getCantidad()+cantidad);
        
        selected.setSubTotal(selected.getSubTotal().add(precio.multiply(new BigDecimal(cantidad))));
        
        selected.setIva(selected.getSubTotal().multiply(new BigDecimal(".13")));
        selected.setTotal(selected.getSubTotal().add(selected.getIva()));
        this.detCompra.add(detalle);
        this.cantidad =1;
        this.precio = new BigDecimal("0");
        this.productoIdproducto = null;
        this.existencia=0;
        return "ok";
    }
    
    public void buscar(){
    items = this.ejbFacade.findByOrdenId(finicio, ffinal, proveedor);
    }
    
    public void selecionar(){
        if(selected.getCompraDetList()!=null){
            this.detCompra = selected.getCompraDetList();
        }
    
    }
    
    public void limpiar(){
        selected= null;
        this.detCompra =  new ArrayList<CompraDet>();
        this.cantidad =0;
        this.precio = new BigDecimal("0");
    }    
    

   public void updateExistencia(){
       this.existencia =  this.productoIdproducto.getExistencia();
       this.precio = this.productoIdproducto.getCosto();        
   }    

}
