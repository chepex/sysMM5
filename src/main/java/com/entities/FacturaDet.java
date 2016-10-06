/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author chepe
 */
@Entity
@Table(name = "factura_det")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FacturaDet.findAll", query = "SELECT f FROM FacturaDet f"),
    @NamedQuery(name = "FacturaDet.findByIdfacturaDet", query = "SELECT f FROM FacturaDet f WHERE f.idfacturaDet = :idfacturaDet"),
    @NamedQuery(name = "FacturaDet.findByCantidad", query = "SELECT f FROM FacturaDet f WHERE  f.cantidad = :cantidad"),
    @NamedQuery(name = "FacturaDet.findByPrecio", query = "SELECT f FROM FacturaDet f WHERE f.precio = :precio")})
public class FacturaDet implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idfactura_det")
    private Integer idfacturaDet;
    @Column(name = "cantidad")
    private Integer cantidad;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "precio")
    private BigDecimal precio;
    @Column(name = "total")
    private BigDecimal total;    
    @Column(name = "utilidad")
    private BigDecimal utilidad;      
    @JoinColumn(name = "producto_idproducto", referencedColumnName = "idproducto")
    @ManyToOne(optional = false)
    private Producto productoIdproducto;
    @JoinColumn(name = "factura_idfactura", referencedColumnName = "idfactura")
    @ManyToOne(optional = false)
    private Factura facturaIdfactura;

    public FacturaDet() {
    }

    public BigDecimal getUtilidad() {
        return utilidad;
    }

    public void setUtilidad(BigDecimal utilidad) {
        this.utilidad = utilidad;
    }

    
    
    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    
    public FacturaDet(Integer idfacturaDet) {
        this.idfacturaDet = idfacturaDet;
    }

    public Integer getIdfacturaDet() {
        return idfacturaDet;
    }

    public void setIdfacturaDet(Integer idfacturaDet) {
        this.idfacturaDet = idfacturaDet;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public Producto getProductoIdproducto() {
        return productoIdproducto;
    }

    public void setProductoIdproducto(Producto productoIdproducto) {
        this.productoIdproducto = productoIdproducto;
    }

    public Factura getFacturaIdfactura() {
        return facturaIdfactura;
    }

    public void setFacturaIdfactura(Factura facturaIdfactura) {
        this.facturaIdfactura = facturaIdfactura;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idfacturaDet != null ? idfacturaDet.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FacturaDet)) {
            return false;
        }
        FacturaDet other = (FacturaDet) object;
        if ((this.idfacturaDet == null && other.idfacturaDet != null) || (this.idfacturaDet != null && !this.idfacturaDet.equals(other.idfacturaDet))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.entities.FacturaDet[ idfacturaDet=" + idfacturaDet + " ]";
    }
    
}
