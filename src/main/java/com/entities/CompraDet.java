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
@Table(name = "compra_det")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CompraDet.findAll", query = "SELECT c FROM CompraDet c"),
    @NamedQuery(name = "CompraDet.findByIdcompraDet", query = "SELECT c FROM CompraDet c WHERE c.idcompraDet = :idcompraDet"),
    @NamedQuery(name = "CompraDet.findByCantidad", query = "SELECT c FROM CompraDet c WHERE c.cantidad = :cantidad"),
    @NamedQuery(name = "CompraDet.findByPrecio", query = "SELECT c FROM CompraDet c WHERE c.precio = :precio")})
public class CompraDet implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idcompra_det")
    private Integer idcompraDet;
    @Column(name = "cantidad")
    private Integer cantidad;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "precio")
    private BigDecimal precio;
    @Column(name = "total")
    private BigDecimal total;    
    @JoinColumn(name = "producto_idproducto", referencedColumnName = "idproducto")
    @ManyToOne(optional = false)
    private Producto productoIdproducto;
    @JoinColumn(name = "compra_idcompra", referencedColumnName = "idcompra")
    @ManyToOne(optional = false)
    private Compra compraIdcompra;

    public CompraDet() {
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public CompraDet(Integer idcompraDet) {
        this.idcompraDet = idcompraDet;
    }

    public Integer getIdcompraDet() {
        return idcompraDet;
    }

    public void setIdcompraDet(Integer idcompraDet) {
        this.idcompraDet = idcompraDet;
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

    public Compra getCompraIdcompra() {
        return compraIdcompra;
    }

    public void setCompraIdcompra(Compra compraIdcompra) {
        this.compraIdcompra = compraIdcompra;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idcompraDet != null ? idcompraDet.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CompraDet)) {
            return false;
        }
        CompraDet other = (CompraDet) object;
        if ((this.idcompraDet == null && other.idcompraDet != null) || (this.idcompraDet != null && !this.idcompraDet.equals(other.idcompraDet))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.entities.CompraDet[ idcompraDet=" + idcompraDet + " ]";
    }
    
}
