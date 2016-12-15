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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author mmixco
 */
@Entity
@Table(name = "compania")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Compania.findAll", query = "SELECT c FROM Compania c"),
    @NamedQuery(name = "Compania.findByIdtienda", query = "SELECT c FROM Compania c WHERE c.idtienda = :idtienda"),
    @NamedQuery(name = "Compania.findByNombre", query = "SELECT c FROM Compania c WHERE c.nombre = :nombre"),
    @NamedQuery(name = "Compania.findByDireccion", query = "SELECT c FROM Compania c WHERE c.direccion = :direccion"),
    @NamedQuery(name = "Compania.findByLatitud", query = "SELECT c FROM Compania c WHERE c.latitud = :latitud"),
    @NamedQuery(name = "Compania.findByLongitud", query = "SELECT c FROM Compania c WHERE c.longitud = :longitud"),
    @NamedQuery(name = "Compania.findByVenta", query = "SELECT c FROM Compania c WHERE c.venta = :venta")})
public class Compania implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idtienda")
    private Integer idtienda;
    @Size(max = 255)
    @Column(name = "nombre")
    private String nombre;
    @Size(max = 255)
    @Column(name = "direccion")
    private String direccion;
    @Size(max = 255)
    @Column(name = "latitud")
    private String latitud;
    @Size(max = 255)
    @Column(name = "longitud")
    private String longitud;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "venta")
    private BigDecimal venta;

    public Compania() {
    }

    public Compania(Integer idtienda) {
        this.idtienda = idtienda;
    }

    public Integer getIdtienda() {
        return idtienda;
    }

    public void setIdtienda(Integer idtienda) {
        this.idtienda = idtienda;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public BigDecimal getVenta() {
        return venta;
    }

    public void setVenta(BigDecimal venta) {
        this.venta = venta;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idtienda != null ? idtienda.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Compania)) {
            return false;
        }
        Compania other = (Compania) object;
        if ((this.idtienda == null && other.idtienda != null) || (this.idtienda != null && !this.idtienda.equals(other.idtienda))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.entities.Compania[ idtienda=" + idtienda + " ]";
    }
    
}
