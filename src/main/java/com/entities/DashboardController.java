/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.entities;


import java.io.IOException;
import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.ejb.EJB;
 
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
 
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;

import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.PieChartModel;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;

 
/**
 *
 * @author mmixco
 */

@ManagedBean(name = "dashboardController")
@SessionScoped
public class DashboardController implements Serializable {
    
   @EJB
   private FacturaFacade facturaFacade; 
   @EJB
   private CompaniaFacade companiaFacade;    
   @EJB
   private CompraFacade compraFacade; 
   @EJB
   private ProductoFacade productoFacade;         
   @EJB
   private CajaChicaFacade cajaChicaFacade;     
   @EJB
   private ClienteFacade clienteFacade;        
   
   @EJB
   private TransaccionCajaFacade transaccionCajaFacade;    
   @EJB
   private ProveedorFacade proveedorFacade;       
   private BigDecimal maxLine;          
   private BarChartModel ventaModel;
   private BarChartModel compraModel;   
   private PieChartModel existenciaCategoria;
   private PieChartModel ventaUsuario; 
   private String ventaMes;
   private String compraMes;   
   private String cajaDia;   
   private String existencia;
   private String cliente;
   private String proveedor;   
   private String productoMin;
   private String productoMax;
   private String pendienteCliente;
   private String pendienteProveedor;
   private List<Factura> lfactura;
   private List<Compra> lcompra;   
   private List<Producto>  lprocuto;
   private List<TransaccionCaja> ltcaja;
   private List<Cliente> lcliente;   
   private List<Proveedor> lproveedor;      
   private MapModel gasolineras;
   
    public DashboardController() {
    }

    public MapModel getGasolineras() {
        addMapModel1();
        return gasolineras;
    }

    public void setGasolineras(MapModel gasolineras) {
        this.gasolineras = gasolineras;
    }

    
    
    public List<Proveedor> getLproveedor() {
        return lproveedor;
    }

    public void setLproveedor(List<Proveedor> lproveedor) {
        this.lproveedor = lproveedor;
    }

    
    
    public List<Cliente> getLcliente() {
        return lcliente;
    }

    public void setLcliente(List<Cliente> lcliente) {
        this.lcliente = lcliente;
    }    
    
    public List<TransaccionCaja> getLtcaja() {
        return ltcaja;
    }

    public void setLtcaja(List<TransaccionCaja> ltcaja) {
        this.ltcaja = ltcaja;
    }

    public List<Producto> getLprocuto() {
        return lprocuto;
    }

    public void setLprocuto(List<Producto> lprocuto) {
        this.lprocuto = lprocuto;
    }

    
    
    public List<Compra> getLcompra() {
        return lcompra;
    }

    public void setLcompra(List<Compra> lcompra) {
        this.lcompra = lcompra;
    }

    
    
    public List<Factura> getLfactura() {
        return lfactura;
    }

    public void setLfactura(List<Factura> lfactura) {
        this.lfactura = lfactura;
    }
    
    

    public String getPendienteProveedor() {
        return pendienteProveedor;
    }

    public void setPendienteProveedor(String pendienteProveedor) {
        this.pendienteProveedor = pendienteProveedor;
    }
    
    

    public String getPendienteCliente() {
        return pendienteCliente;
    }

    public void setPendienteCliente(String pendienteCliente) {
        this.pendienteCliente = pendienteCliente;
    }

    
    
    public String getProductoMax() {
        return productoMax;
    }

    public void setProductoMax(String productoMax) {
        this.productoMax = productoMax;
    }

    
    
    public String getProductoMin() {
        return productoMin;
    }

    public void setProductoMin(String productoMin) {
        this.productoMin = productoMin;
    }
    
    

    public String getProveedor() {
        return proveedor;
    }

    public void setProveedor(String proveedor) {
        this.proveedor = proveedor;
    }

    
    
    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }
    
    

    public String getExistencia() {
        return existencia;
    }

    public void setExistencia(String existencia) {
        this.existencia = existencia;
    }

    
    public String getCajaDia() {
        return cajaDia;
    }

    public void setCajaDia(String cajaDia) {
        this.cajaDia = cajaDia;
    }

    
    
    public String getCompraMes() {
        return compraMes;
    }

    public void setCompraMes(String compraMes) {
        this.compraMes = compraMes;
    }

    
    
    public String getVentaMes() {
        

        return ventaMes;
    }

    public void setVentaMes(String ventaMes) {
        this.ventaMes = ventaMes;
    }
    
    
    
    @PostConstruct
    public void init() {              
         createBarModels();         
    }

    public PieChartModel getVentaUsuario() {
        return ventaUsuario;
    }

    public void setVentaUsuario(PieChartModel ventaUsuario) {
        this.ventaUsuario = ventaUsuario;
    }

    
    
    public PieChartModel getExistenciaCategoria() {
        return existenciaCategoria;
    }

    public void setExistenciaCategoria(PieChartModel existenciaCategoria) {
        this.existenciaCategoria = existenciaCategoria;
    }

    
    
    public BarChartModel getVentaModel() {
        System.out.println("venta-->"+ventaModel);
        return ventaModel;
    }

    public BarChartModel getCompraModel() {
        return compraModel;
    }
    
    
    
    

  
    private BarChartModel initBarVenta() {
        BarChartModel model = new BarChartModel();
        maxLine = new BigDecimal("1");          
        ChartSeries series1 = new ChartSeries();
        Calendar cal = Calendar.getInstance();       
        Date date1 = cal.getTime();
        SimpleDateFormat formateador = new SimpleDateFormat("yyyy");        
        series1.setLabel(formateador.format(date1));           
        List<Object[]> lv = this.facturaFacade.ventaActual();
        if(lv!=null){
            Iterator<Object[]>itr = lv.iterator();  
            while(itr.hasNext()) {
               Object[] element = itr.next();            
               BigDecimal valor =new BigDecimal( element[1].toString());
               if(valor.compareTo(maxLine)==1){
                maxLine = valor;
               }                
                System.out.println("mes:"+element[0].toString()+" valor:"+valor);
               series1.set( element[0].toString(),valor );                    
           }               
        }      
        
        ChartSeries series2 = new ChartSeries();       
        Calendar cal2 = Calendar.getInstance(); 
        cal2.add(Calendar.DATE, -365);
        Date date2 = cal2.getTime(); 
        series2.setLabel(formateador.format(date2));          
        List<Object[]> lv2 = this.facturaFacade.ventaAnterior();
        if(lv2!=null){
            Iterator<Object[]>itr = lv2.iterator();  
            while(itr.hasNext()) {
               Object[] element = itr.next();            
               BigDecimal valor =new BigDecimal( element[1].toString());
               if(valor.compareTo(maxLine)==1){
                maxLine = valor;
               }                
                series2.set( element[0].toString(),valor );  
                System.out.println("mes:"+element[0].toString()+" valor:"+valor);
            }                     
        }  
        model.addSeries(series1);
        model.addSeries(series2);         
        return model;
    }
    
    private BarChartModel initBarCompra() {
        BarChartModel model = new BarChartModel();
        maxLine = new BigDecimal("1");          
        ChartSeries series1 = new ChartSeries();
        Calendar cal = Calendar.getInstance();       
        Date date1 = cal.getTime();
        SimpleDateFormat formateador = new SimpleDateFormat("yyyy");        
        series1.setLabel(formateador.format(date1));           
        List<Object[]> lv = this.compraFacade.compraActual();
        if(lv!=null){
            Iterator<Object[]>itr = lv.iterator();  
            while(itr.hasNext()) {
               Object[] element = itr.next();            
               BigDecimal valor =new BigDecimal( element[1].toString());
               if(valor.compareTo(maxLine)==1){
                maxLine = valor;
               }                
                System.out.println("mes:"+element[0].toString()+" valor:"+valor);
               series1.set( element[0].toString(),valor );                    
           }               
        }      
        
        ChartSeries series2 = new ChartSeries();       
        Calendar cal2 = Calendar.getInstance(); 
        cal2.add(Calendar.DATE, -365);
        Date date2 = cal2.getTime(); 
        series2.setLabel(formateador.format(date2));          
        List<Object[]> lv2 = this.compraFacade.compraAnterior();
        if(lv2!=null){
            Iterator<Object[]>itr = lv2.iterator();  
            while(itr.hasNext()) {
               Object[] element = itr.next();            
               BigDecimal valor =new BigDecimal( element[1].toString());
               if(valor.compareTo(maxLine)==1){
                maxLine = valor;
               }                
                series2.set( element[0].toString(),valor );  
                System.out.println("mes:"+element[0].toString()+" valor:"+valor);
            }                     
        }  
        model.addSeries(series1);
        model.addSeries(series2);         
        return model;
    }    
 
    private void createBarModels() {
        createBarVenta();    
        createBarCompra();
        createPieModel1();
        createPieVentaUsuario();
        llenarVariables();
        
    }
    
    private void llenarVariables(){
    
        /*Ventas*/
        List<Object[]> lv = this.facturaFacade.ventaMes();
        if(!lv.isEmpty()){            
            Object[] vtaMes =  lv.get(0);             
            BigDecimal valor =new BigDecimal( vtaMes[1].toString());
            valor = valor.setScale(2, RoundingMode.CEILING);
            ventaMes =  valor.toString();
        }else{
            ventaMes = "0";
        } 
        
        /*Compras*/
        
        List<Object[]> lv2 = this.compraFacade.compraMes();
        if(!lv2.isEmpty()){   
            
            if(!lv2.isEmpty()) {
                Object[] vcompraMes =  lv2.get(0);             
                BigDecimal valor =new BigDecimal( vcompraMes[1].toString());
                valor = valor.setScale(2, RoundingMode.CEILING);
                compraMes =  valor.toString();
            }
        }
        if(compraMes ==null)           
        {
            compraMes = "0";
        } 
        
        this.cajaDia  = cajaChicaFacade.saldoDia().toString();
        existencia = productoFacade.existenciaDia().toString();
        
        cliente = String.valueOf(clienteFacade.count());
        proveedor = String.valueOf(proveedorFacade.count());
        System.out.println("existencia min*--->"+productoFacade.existenciaMin());
        System.out.println("existencia max*--->"+productoFacade.existenciaMax());
        productoMin =   productoFacade.existenciaMin() ;        
        productoMax =   productoFacade.existenciaMax();
        
        BigDecimal clientePen= new BigDecimal(clienteFacade.saldoClientes());
        clientePen = clientePen.setScale(2, RoundingMode.CEILING);
        pendienteCliente = String.valueOf(clientePen); 
        
        BigDecimal proveedorPen= new BigDecimal(proveedorFacade.saldoProveedores());
        proveedorPen = proveedorPen.setScale(2, RoundingMode.CEILING);
        pendienteProveedor = String.valueOf(proveedorPen);
        
    }
     
    private void createBarVenta() {
        ventaModel = initBarVenta();         
        ventaModel.setTitle("Comparativo Ventas");
        ventaModel.setLegendPosition("ne");         
        ventaModel.setExtender("skinBar");
        
        System.out.println("max-->"+maxLine.add(new BigDecimal("10")));
        Axis yAxis = ventaModel.getAxis(AxisType.Y);
        yAxis.setLabel("Ventas");
        yAxis.setMin(0);
        yAxis.setMax(maxLine.add(new BigDecimal("10")));
    }  
    
    private void createBarCompra() {
        this.compraModel = initBarCompra();         
        compraModel.setTitle("Comparativo Compras");
        compraModel.setLegendPosition("ne");   
        compraModel.setExtender("skinBar");
        Axis xAxis = compraModel.getAxis(AxisType.X);         
        Axis yAxis = compraModel.getAxis(AxisType.Y);
        yAxis.setLabel("Compra");
        yAxis.setMin(0);
        yAxis.setMax(maxLine.add(new BigDecimal("10")));
    }     
 
    private void createPieModel1() {
        existenciaCategoria = new PieChartModel();
        List<Object[]> lv = this.productoFacade.existenciaCategoria();
        if(lv!=null){
            Iterator<Object[]>itr = lv.iterator();  
            while(itr.hasNext()) {
               Object[] element = itr.next();            
               BigDecimal valor =new BigDecimal( element[1].toString());
                            
                existenciaCategoria.set( element[0].toString(),valor );  
                System.out.println("mesPie:"+element[0].toString()+" valor:"+valor);
            }                     
        }          
         
         existenciaCategoria.setExtender("skinPie");
        existenciaCategoria.setTitle("Categoria Existencia");
        existenciaCategoria.setLegendPosition("w");
      
    }
    
    private void createPieVentaUsuario() {
        ventaUsuario = new PieChartModel();
        List<Object[]> lv = this.facturaFacade.ventaUsuario();
        if(lv!=null){
            Iterator<Object[]>itr = lv.iterator();  
            while(itr.hasNext()) {
               Object[] element = itr.next();            
               BigDecimal valor =new BigDecimal( element[1].toString());                            
                ventaUsuario.set( element[0].toString(),valor );  
                System.out.println("mesVenta:"+element[0].toString()+" valor:"+valor);
            }                     
        }          
          ventaUsuario.setExtender("skinPie");
        ventaUsuario.setTitle("Venta Usuario");
        ventaUsuario.setLegendPosition("w");
      
    }    
    
    public void consultaVenta(){   
        this.lfactura  = null;
        this.lfactura = this.facturaFacade.ListaVentaMes();
        System.out.println(" lista ventas-->"+lfactura);
    }
    
    public void consultaCompra(){    
        this.lcompra = null;
        this.lcompra = this.compraFacade.ListaCompraMes2();
        System.out.println(" lista ventas-->"+lcompra);
        System.out.println(" lista ventas-->"+lcompra);
        System.out.println(" lista ventas-->"+lcompra);
    }   
    
    public void consultaProducto(){
        this.lprocuto = null;
        this.lprocuto = this.productoFacade.findAll();
        System.out.println(" lista productos-->"+lprocuto);
    
    }
    
    
    public void consultaProductoMin() throws IOException{
        this.lprocuto = null;
        this.lprocuto = this.productoFacade.findAllMin();
        System.out.println(" lista productos-->"+lprocuto);
           FacesContext.getCurrentInstance().getExternalContext().redirect("dashboard/ListProducto.xhtml");
    
    }    
    
    public void consultaProductoMax() throws IOException{
        this.lprocuto = null;
        this.lprocuto = this.productoFacade.findAllMax();
        System.out.println(" lista productos-->"+lprocuto);
           FacesContext.getCurrentInstance().getExternalContext().redirect("dashboard/ListProducto.xhtml");
    
    }    
    
    public void consultaTransaccionCaja(){
        this.ltcaja = null;
        this.ltcaja = this.transaccionCajaFacade.findByFecha();
    }
    
    public void consultaClientePendiente() throws IOException{    
        lcliente = this.clienteFacade.saldoPendiente(); 
        FacesContext.getCurrentInstance().getExternalContext().redirect("dashboard/ListCliente.xhtml");
    }
    
    public void consultaProveedorPendiente() throws IOException{    
        lproveedor = this.proveedorFacade.saldoProveedor(); 
        FacesContext.getCurrentInstance().getExternalContext().redirect("dashboard/ListProveedor.xhtml");
    }
       
    public void addMapModel1() {
        gasolineras = new DefaultMapModel();
        System.out.println("<--->"); 
        //Shared coordinates
        List<Compania> lv = this.companiaFacade.findAll();
        System.out.println("lv--->"+lv);
        for(Compania v :lv){
            Double lat =  Double.parseDouble(v.getLatitud());
            Double lon =  Double.parseDouble(v.getLongitud());            
            LatLng coord1 = new LatLng(lat, lon);   
             System.out.println("nombre--->"+ v.getNombre());
            Marker marker1 = new Marker(coord1, v.getNombre(), null, "texaco-logo.png");
            gasolineras.addOverlay(marker1);
        }        
        
    }     
    
 
}
