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
@Table(name = "inv_detm")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "InvDetm.findAll", query = "SELECT i FROM InvDetm i"),
    @NamedQuery(name = "InvDetm.findByIdinvDetm", query = "SELECT i FROM InvDetm i WHERE i.idinvDetm = :idinvDetm"),
    @NamedQuery(name = "InvDetm.findByProducto", query = "SELECT i FROM InvDetm i WHERE i.productoIdproducto = :producto"),
    @NamedQuery(name = "InvDetm.findByCantidad", query = "SELECT i FROM InvDetm i WHERE i.cantidad = :cantidad")})
public class InvDetm implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idinv_detm")
    private Integer idinvDetm;
    @Column(name = "cantidad")
    private Integer cantidad;
    @Column(name = "precio")
    private BigDecimal precio;    
    @Column(name = "costo")
    private BigDecimal costo;        
    @JoinColumn(name = "producto_idproducto", referencedColumnName = "idproducto")
    @ManyToOne(optional = false)
    private Producto productoIdproducto;
    @JoinColumn(name = "inv_mov_idinv_mov", referencedColumnName = "idinv_mov")
    @ManyToOne(optional = false)
    private InvMov invMovIdinvMov;

    public InvDetm() {
    }

    public InvDetm(Integer idinvDetm) {
        this.idinvDetm = idinvDetm;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public BigDecimal getCosto() {
        return costo;
    }

    public void setCosto(BigDecimal costo) {
        this.costo = costo;
    }

    
    
    public Integer getIdinvDetm() {
        return idinvDetm;
    }

    public void setIdinvDetm(Integer idinvDetm) {
        this.idinvDetm = idinvDetm;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Producto getProductoIdproducto() {
        return productoIdproducto;
    }

    public void setProductoIdproducto(Producto productoIdproducto) {
        this.productoIdproducto = productoIdproducto;
    }

    public InvMov getInvMovIdinvMov() {
        return invMovIdinvMov;
    }

    public void setInvMovIdinvMov(InvMov invMovIdinvMov) {
        this.invMovIdinvMov = invMovIdinvMov;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idinvDetm != null ? idinvDetm.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof InvDetm)) {
            return false;
        }
        InvDetm other = (InvDetm) object;
        if ((this.idinvDetm == null && other.idinvDetm != null) || (this.idinvDetm != null && !this.idinvDetm.equals(other.idinvDetm))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.entities.InvDetm[ idinvDetm=" + idinvDetm + " ]";
    }
    
}
