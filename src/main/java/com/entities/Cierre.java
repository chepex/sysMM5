/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Desarrollo
 */
@Entity
@Table(name = "cierre")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Cierre.findAll", query = "SELECT c FROM Cierre c"),
    @NamedQuery(name = "Cierre.findByIdcierre", query = "SELECT c FROM Cierre c WHERE c.idcierre = :idcierre"),
    @NamedQuery(name = "Cierre.findByAnio", query = "SELECT c FROM Cierre c WHERE c.anio = :anio"),
    @NamedQuery(name = "Cierre.findByMes", query = "SELECT c FROM Cierre c WHERE c.mes = :mes"),
    @NamedQuery(name = "Cierre.findByExistencia", query = "SELECT c FROM Cierre c WHERE c.existencia = :existencia"),
    @NamedQuery(name = "Cierre.findByCostoUnitario", query = "SELECT c FROM Cierre c WHERE c.costoUnitario = :costoUnitario"),
    
    @NamedQuery(name = "Cierre.findByCantidadVenta", query = "SELECT c FROM Cierre c WHERE c.cantidadVenta = :cantidadVenta"),
    @NamedQuery(name = "Cierre.findByPrecio", query = "SELECT c FROM Cierre c WHERE c.precio = :precio"),
    @NamedQuery(name = "Cierre.findByTotalVenta", query = "SELECT c FROM Cierre c WHERE c.totalVenta = :totalVenta"),
    @NamedQuery(name = "Cierre.findByCantidadCompra", query = "SELECT c FROM Cierre c WHERE c.cantidadCompra = :cantidadCompra"),
    
    @NamedQuery(name = "Cierre.findByTotaCompra", query = "SELECT c FROM Cierre c WHERE c.totaCompra = :totaCompra"),
    @NamedQuery(name = "Cierre.findByFecha", query = "SELECT c FROM Cierre c WHERE c.fecha = :fecha"),
    @NamedQuery(name = "Cierre.findByTotalUtilidad", query = "SELECT c FROM Cierre c WHERE c.totalUtilidad = :totalUtilidad"),
    @NamedQuery(name = "Cierre.findByMargen", query = "SELECT c FROM Cierre c WHERE c.margen = :margen")})
public class Cierre implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "idcierre")
    private Integer idcierre;
    @Column(name = "anio")
    private Integer anio;
    @Column(name = "mes")
    private Integer mes;
    @Column(name = "existencia")
    private Integer existencia;
    @Column(name = "cantidad_inicial")
    private Integer cantidadInicial;        
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "costo_unitario")
    private BigDecimal costoUnitario;

    @Column(name = "cantidad_venta")
    private Integer cantidadVenta;
    @Column(name = "precio")
    private BigDecimal precio;
    @Column(name = "total_venta")
    private BigDecimal totalVenta;
    @Column(name = "cantidad_compra")
    private Integer cantidadCompra;
 
    @Column(name = "total_compra")
    private BigDecimal totaCompra;
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Column(name = "total_utilidad")
    private BigDecimal totalUtilidad;
    @Size(max = 45)
    @Column(name = "margen")
    private String margen;
    @JoinColumn(name = "idproducto", referencedColumnName = "idproducto")
    @ManyToOne
    private Producto idproducto;

    public Cierre() {
    }

    public Integer getCantidadInicial() {
        return cantidadInicial;
    }

    public void setCantidadInicial(Integer cantidadInicial) {
        this.cantidadInicial = cantidadInicial;
    }
    
    

    public Cierre(Integer idcierre) {
        this.idcierre = idcierre;
    }

    public Integer getIdcierre() {
        return idcierre;
    }

    public void setIdcierre(Integer idcierre) {
        this.idcierre = idcierre;
    }

    public Integer getAnio() {
        return anio;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    }

    public Integer getMes() {
        return mes;
    }

    public void setMes(Integer mes) {
        this.mes = mes;
    }

    public Integer getExistencia() {
        return existencia;
    }

    public void setExistencia(Integer existencia) {
        this.existencia = existencia;
    }

    public BigDecimal getCostoUnitario() {
        return costoUnitario;
    }

    public void setCostoUnitario(BigDecimal costoUnitario) {
        this.costoUnitario = costoUnitario;
    }



    public Integer getCantidadVenta() {
        return cantidadVenta;
    }

    public void setCantidadVenta(Integer cantidadVenta) {
        this.cantidadVenta = cantidadVenta;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public BigDecimal getTotalVenta() {
        return totalVenta;
    }

    public void setTotalVenta(BigDecimal totalVenta) {
        this.totalVenta = totalVenta;
    }

    public Integer getCantidadCompra() {
        return cantidadCompra;
    }

    public void setCantidadCompra(Integer cantidadCompra) {
        this.cantidadCompra = cantidadCompra;
    }


    public BigDecimal getTotaCompra() {
        return totaCompra;
    }

    public void setTotaCompra(BigDecimal totaCompra) {
        this.totaCompra = totaCompra;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public BigDecimal getTotalUtilidad() {
        return totalUtilidad;
    }

    public void setTotalUtilidad(BigDecimal totalUtilidad) {
        this.totalUtilidad = totalUtilidad;
    }

    public String getMargen() {
        return margen;
    }

    public void setMargen(String margen) {
        this.margen = margen;
    }

    public Producto getIdproducto() {
        return idproducto;
    }

    public void setIdproducto(Producto idproducto) {
        this.idproducto = idproducto;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idcierre != null ? idcierre.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cierre)) {
            return false;
        }
        Cierre other = (Cierre) object;
        if ((this.idcierre == null && other.idcierre != null) || (this.idcierre != null && !this.idcierre.equals(other.idcierre))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.entieies.Cierre[ idcierre=" + idcierre + " ]";
    }
    
}
