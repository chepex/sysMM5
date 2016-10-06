package com.entities;

import com.entities.util.JsfUtil;
import com.entities.util.JsfUtil.PersistAction;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
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
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.DateAxis;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;

@ManagedBean(name = "productoController")
@SessionScoped
public class ProductoController implements Serializable {

    @EJB
    private com.entities.ProductoFacade ejbFacade;
    @EJB
    private com.ejb.Sb_Grafica sb_Grafica;    
    @EJB
    private com.entities.CompraFacade compraFacade;    
    @EJB
    private com.entities.FacturaFacade facturaFacade;     
    @EJB
    private com.entities.FacturaDetFacade facturaDetFacade;      
    private List<Producto> items = null;
    private Producto selected;
    private Categoria categoria;
    private List<Compra> lcompras;
    private List<Factura> lfactura;
    private Factura selectedFactura;
    private Compra selectedCompra;
    private String nombre;
    private LineChartModel chartVentaCompra;    

    public ProductoController() {
    }

    public LineChartModel getChartVentaCompra() {
        System.out.println("char--->"+chartVentaCompra);
        return chartVentaCompra;
    }

    public void setChartVentaCompra(LineChartModel chartVentaCompra) {
        this.chartVentaCompra = chartVentaCompra;
    }

    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    
    public Factura getSelectedFactura() {
        return selectedFactura;
    }

    public void setSelectedFactura(Factura selectedFactura) {
        this.selectedFactura = selectedFactura;
    }

    public Compra getSelectedCompra() {
        return selectedCompra;
    }

    public void setSelectedCompra(Compra selectedCompra) {
        this.selectedCompra = selectedCompra;
    }

    
    public List<Factura> getLfactura() {
        if(selected!=null){
            lfactura = this.facturaFacade.findByProducto(selected);
        }        
        return lfactura;
    }

    public void setLfactura(List<Factura> lfactura) {
        this.lfactura = lfactura;
    }

    
    public List<Compra> getLcompras() {
        if(selected!=null){
            lcompras = this.compraFacade.findByProducto(selected);
        }
        return lcompras;
    }

    public void setLcompras(List<Compra> lcompras) {
        this.lcompras = lcompras;
    }

    
    
    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    
    public Producto getSelected() {
        return selected;
    }

    public void setSelected(Producto selected) {
        this.selected = selected;
    }

   

    
    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
        selected.setIdproducto(0);
    }

    private ProductoFacade getFacade() {
        return ejbFacade;
    }

    public Producto prepareCreate() {
        selected = new Producto();
        initializeEmbeddableKey();
        selected.setMargen(1);
        return selected;
    }

    public void create() {
        selected.setExistencia(0);
        selected.setCosto(BigDecimal.ZERO);
        selected.setPrecio(BigDecimal.ZERO);
        
       
            selected = this.getFacade().auditCreate(selected);
            persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("ProductoCreated"));
            if (!JsfUtil.isValidationFailed()) {
                items = null;    // Invalidate list of items to trigger re-query.
            }
        
        consultaCategoria();
    }

    public void update() {
        if(selected.getMargen()==0){
              JsfUtil.addErrorMessage("El margen debe ser mayor a cero ");
        }else{
            selected = this.getFacade().auditUpdate(selected);
            persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("ProductoUpdated"));
        }
        consultaCategoria();
        
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("ProductoDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Producto> getItems() {
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

    public List<Producto> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Producto> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }
    

    public List<Producto> autoCompleteProducto(String valor) {
        System.out.println("nombre-->"+valor);
        return getFacade().findByNombreCodigo(valor);
        
    }    

    @FacesConverter(forClass = Producto.class)
    public static class ProductoControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ProductoController controller = (ProductoController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "productoController");
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
            if (object instanceof Producto) {
                Producto o = (Producto) object;
                return getStringKey(o.getIdproducto());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Producto.class.getName()});
                return null;
            }
        }

    }
    
    public void consultaCategoria(){
        if(nombre!=""){
            this.items = this.ejbFacade.findByCategoriaNombre(this.categoria, this.nombre);
        }else{
            this.items = this.ejbFacade.findByCategoria(this.categoria);
        }
        
     this.selected= null;
     this.lcompras = null;
     this.lfactura = null;
     
       
    }
    public void geneararGrafica(){
        System.out.println("generar grafica");
        if(selected!=null){
          chartVentaCompra =  sb_Grafica.graficaVentas(selected )   ;
        }
        this.selectedCompra = null;
        this.selectedFactura = null;
        
           
    }
    
     

}
