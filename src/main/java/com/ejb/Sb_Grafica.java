/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ejb;

import com.entities.Producto;
import com.entities.Proveedor;
import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.DateAxis;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;

/**
 *
 * @author Desarrollo
 */
@Stateless
public class Sb_Grafica {

    private LineChartModel chartVentaCompra;   
    @EJB
    private com.entities.ProductoFacade ejbFacade;
    
    public LineChartModel graficaVentas(Producto p ){
   
            chartVentaCompra = initLinearModel(p);
            chartVentaCompra.setTitle("Linear Chart");           
            chartVentaCompra.setExtender("skinChart");            
            chartVentaCompra.setTitle("Zoom for Details");
            chartVentaCompra.setZoom(true);
            chartVentaCompra.setLegendPosition("ne");
            chartVentaCompra.getAxis(AxisType.Y).setLabel(" ");
            DateAxis axis = new DateAxis(" ");
            axis.setTickAngle(-50);
            axis.setMax("2016-10-31");
            axis.setTickFormat("%b %#d, %y");        
            chartVentaCompra.getAxes().put(AxisType.X, axis);            
        return chartVentaCompra;
    }
    
    private LineChartModel initLinearModel(Producto p) {
       LineChartModel model = new LineChartModel(); 
       model.addSeries(compras(p));
       model.addSeries(ventas(p));
         
       return model;
    }   
    
    private LineChartModel initGraficaProveedor(Proveedor p) {
       LineChartModel model = new LineChartModel(); 
       model.addSeries(comprasProveedor(p));
       
         
       return model;
    }      
    
    public LineChartSeries compras(Producto p){
        LineChartSeries series2 = new LineChartSeries();
           series2.setLabel("Compras");
           List<Object[]> lv2 =this.ejbFacade.compraProducto(p.getIdproducto());        
           if(!lv2.isEmpty()){
               Iterator<Object[]>itr = lv2.iterator();                           
               while(itr.hasNext()) {                       
                   Object[] element = itr.next();            
                   BigDecimal valor =new BigDecimal( element[1].toString());
                  
                   series2.set(element[0].toString(), valor);
               }
               
           }    
           return series2;
    
    } 
    
    public LineChartSeries comprasProveedor(Proveedor p){
        LineChartSeries series2 = new LineChartSeries();
           series2.setLabel("Compras");
           List<Object[]> lv2 =this.ejbFacade.compraProveedor(p.getIdproveedor());   
           System.out.println("lista"+lv2);
           if(!lv2.isEmpty()){
               Iterator<Object[]>itr = lv2.iterator();                           
               while(itr.hasNext()) {   
                  
                   Object[] element = itr.next();            
                   BigDecimal valor =new BigDecimal( element[1].toString());
                   System.out.println("dia "+element[0].toString()+" valor:"+valor);
                   series2.set(element[0].toString(), valor);
               }
               
           }    
           return series2;
    
    }     
    
    public LineChartSeries ventas(Producto p){
        LineChartSeries series1 = new LineChartSeries();
        series1.setLabel("Ventas");
        List<Object[]> lv =this.ejbFacade.ventaProducto(p.getIdproducto());        
        if(!lv.isEmpty()){
            Iterator<Object[]>itr = lv.iterator();                           
            while(itr.hasNext()) {                       
                Object[] element = itr.next();            
                BigDecimal valor =new BigDecimal( element[1].toString());
                
                series1.set(element[0].toString(), valor); 
                
            }
              
        }     
           return series1;
    
    }    
    
    public LineChartModel graficaProveedor(Proveedor p ){
        
            chartVentaCompra = initGraficaProveedor(p);
            chartVentaCompra.setTitle("Linear Chart");           
            chartVentaCompra.setExtender("skinChart");            
            chartVentaCompra.setTitle("Zoom for Details");
            chartVentaCompra.setZoom(true);
            chartVentaCompra.setLegendPosition("ne");
            chartVentaCompra.getAxis(AxisType.Y).setLabel(" ");
            DateAxis axis = new DateAxis(" ");
            axis.setTickAngle(-50);
            axis.setMax("2016-10-31");
            axis.setTickFormat("%b %#d, %y");        
            chartVentaCompra.getAxes().put(AxisType.X, axis);            
        return chartVentaCompra;
    }    
      
}
