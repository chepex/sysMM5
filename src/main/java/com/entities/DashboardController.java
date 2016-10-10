/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.entities;


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
 
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;

import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.PieChartModel;

 
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
   private CompraFacade compraFacade; 
   @EJB
   private ProductoFacade productoFacade;         
   private BigDecimal maxLine;          
   private BarChartModel ventaModel;
   private BarChartModel compraModel;   
   private PieChartModel existenciaCategoria;
   private PieChartModel ventaUsuario; 
   private String ventaMes;
   private String compraMes;   
 
    public DashboardController() {
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
        //createLineModels();
        System.out.println("aqui--->");
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
        if(lv!=null){            
            Object[] vtaMes =  lv.get(0);             
            BigDecimal valor =new BigDecimal( vtaMes[1].toString());
            valor = valor.setScale(2, RoundingMode.CEILING);
            ventaMes =  valor.toString();
        }else{
            ventaMes = "0";
        } 
        
        /*Compras*/
        
        List<Object[]> lv2 = this.compraFacade.compraMes();
        if(lv2!=null){   
            
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
   
    
 
}
