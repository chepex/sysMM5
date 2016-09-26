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

@ManagedBean(name = "invMovController")
@SessionScoped
public class InvMovController implements Serializable {

    @EJB
    private com.entities.InvMovFacade ejbFacade;
    @EJB
    private com.entities.UsuarioFacade usuarioFacade; 

    @EJB
    private com.entities.ProductoFacade productoFacade;    

    @EJB
    private com.ejb.SB_inventario sb_inventario;     
    
    private List<InvMov> items = null;
    private InvMov selected;
    private Date finicio;
    private Date ffinal;
    private Producto productoIdproducto;
    private int cantidad;
    private BigDecimal precio;   
    private Documento documento;    
    private List<InvDetm> detInvmov = new ArrayList<InvDetm>();
    private int existencia;      
     

    public InvMovController() {
    }

    public int getExistencia() {
        return existencia;
    }

    public void setExistencia(int existencia) {
        this.existencia = existencia;
    }

    
    public List<InvDetm> getDetInvmov() {
        return detInvmov;
    }

    public void setDetInvmov(List<InvDetm> detInvmov) {
        this.detInvmov = detInvmov;
    }

    
    
    public Documento getDocumento() {
        return documento;
    }

    public void setDocumento(Documento documento) {
        this.documento = documento;
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

    
    
    public InvMov getSelected() {
        return selected;
    }

    public void setSelected(InvMov selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private InvMovFacade getFacade() {
        return ejbFacade;
    }

    public InvMov prepareCreate() {
        selected = new InvMov();
        selected.setFecha(new Date());
        Usuario usuario = usuarioFacade.find(1);
        
        selected.setUsuarioIdusuario(usuario);
        selected.setCantidad(0);      
        this.productoIdproducto= null;
        
        initializeEmbeddableKey();
        return selected;
    }

    public String  create() {
        
        if(detInvmov.isEmpty()){
           JsfUtil.addErrorMessage("Agregue un producto");
           return "error";
           
       }
              
        selected.setInvDetmList(this.detInvmov);  
        if(sb_inventario.actulizaExistencia(selected).equals("ok")){
             selected = this.getFacade().auditCreate(selected);
            persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("InvMovCreated"));
        }else{
           JsfUtil.addErrorMessage("Surgio un error");
        }
         
        
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
        
        return "ok";
    }

    public void update() {
         selected = this.getFacade().auditUpdate(selected);
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("InvMovUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("InvMovDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<InvMov> getItems() {
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

    public List<InvMov> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<InvMov> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = InvMov.class)
    public static class InvMovControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            InvMovController controller = (InvMovController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "invMovController");
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
            if (object instanceof InvMov) {
                InvMov o = (InvMov) object;
                return getStringKey(o.getIdinvMov());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), InvMov.class.getName()});
                return null;
            }
        }

    }
    public void limpiar(){
        selected= null;
        this.detInvmov =  new ArrayList<InvDetm>();
        this.cantidad =0;
        //this.precio = new BigDecimal("0");*/
    }       
    
    
    public String addDetalle(){
        
        String msg ="";
        System.out.println("aqui-->");
        System.out.println("documento -->"+selected.getDocumentoIddocumento().getSumaResta());
        System.out.println("cantidad-->"+this.cantidad);
        System.out.println("existencia-->"+this.productoIdproducto.getExistencia());
        System.out.println("producto"+this.productoIdproducto.getNombre());
        if(this.productoIdproducto==null){
           JsfUtil.addErrorMessage("Selecione un producto");
           return "error";
        }
        
        if(this.cantidad==0||cantidad<0){
         JsfUtil.addErrorMessage("Digite una cantidad valida");
            return "error";
        }
        
       if(selected.getDocumentoIddocumento().getSumaResta().equals("S")) {               
          msg = sb_inventario.validaEntrada(productoIdproducto,   cantidad);       
       }
       
       if(selected.getDocumentoIddocumento().getSumaResta().equals("R")) {
            msg =   sb_inventario.validaSalida(productoIdproducto, cantidad);
       }       
        
       if(!msg.equals("ok")){
            JsfUtil.addErrorMessage(msg);
            return "error";
       }
       int id= 1;
       if(detInvmov!= null){
       id= detInvmov.size()+1;
       }
       
        InvDetm detalle = new InvDetm();
        detalle.setIdinvDetm(id);
        detalle.setInvMovIdinvMov(selected);
        detalle.setCantidad(cantidad);
        detalle.setPrecio(productoIdproducto.getPrecio());
        detalle.setCosto(productoIdproducto.getCosto());
        //detalle.setTotal(precio.multiply(new BigDecimal(cantidad)));
        
        detalle.setProductoIdproducto(productoIdproducto);
        System.out.println("aqui-3");
        selected.setCantidad(selected.getCantidad()+cantidad);
        
        this.detInvmov.add(detalle);
        
        return "ok";
        
    }     
    
    public void buscar(){
    items = this.ejbFacade.findByDocumentoFecha(finicio, ffinal, documento);
    }    
   
   public void selecionar(){
        if(selected.getInvDetmList() !=null){
            this.detInvmov = selected.getInvDetmList();
        }
    
    }     
   
   public void updateExistencia(){
       this.existencia =  this.productoIdproducto.getExistencia();
        
   }       

}
