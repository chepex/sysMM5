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
 * @author mmixco
 */
@Entity
@Table(name = "detalle_caja")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DetalleCaja.findAll", query = "SELECT d FROM DetalleCaja d"),
    @NamedQuery(name = "DetalleCaja.findByIddetalle", query = "SELECT d FROM DetalleCaja d WHERE d.iddetalle = :iddetalle"),
    @NamedQuery(name = "DetalleCaja.findByCantidad", query = "SELECT d FROM DetalleCaja d WHERE d.cantidad = :cantidad"),
    @NamedQuery(name = "DetalleCaja.findByPrecio", query = "SELECT d FROM DetalleCaja d WHERE d.precio = :precio"),
    @NamedQuery(name = "DetalleCaja.findByTotal", query = "SELECT d FROM DetalleCaja d WHERE d.total = :total")})
public class DetalleCaja implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "iddetalle")
    private Integer iddetalle;
    @Column(name = "cantidad")
    private Integer cantidad;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "precio")
    private BigDecimal precio;
    @Column(name = "total")
    private BigDecimal total;
    @JoinColumn(name = "idproducto", referencedColumnName = "idproducto")
    @ManyToOne
    private Producto idproducto;
    @JoinColumn(name = "idtransaccion", referencedColumnName = "idtransaccion")
    @ManyToOne
    private TransaccionCaja idtransaccion;

    public DetalleCaja() {
    }

    public DetalleCaja(Integer iddetalle) {
        this.iddetalle = iddetalle;
    }

    public Integer getIddetalle() {
        return iddetalle;
    }

    public void setIddetalle(Integer iddetalle) {
        this.iddetalle = iddetalle;
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

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public Producto getIdproducto() {
        return idproducto;
    }

    public void setIdproducto(Producto idproducto) {
        this.idproducto = idproducto;
    }

    public TransaccionCaja getIdtransaccion() {
        return idtransaccion;
    }

    public void setIdtransaccion(TransaccionCaja idtransaccion) {
        this.idtransaccion = idtransaccion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iddetalle != null ? iddetalle.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DetalleCaja)) {
            return false;
        }
        DetalleCaja other = (DetalleCaja) object;
        if ((this.iddetalle == null && other.iddetalle != null) || (this.iddetalle != null && !this.iddetalle.equals(other.iddetalle))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.entities.DetalleCaja[ iddetalle=" + iddetalle + " ]";
    }
    
}
