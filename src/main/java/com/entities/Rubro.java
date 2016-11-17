/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author mmixco
 */
@Entity
@Table(name = "rubro")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Rubro.findAll", query = "SELECT r FROM Rubro r"),
    @NamedQuery(name = "Rubro.findByIdrubro", query = "SELECT r FROM Rubro r WHERE r.idrubro = :idrubro"),
    @NamedQuery(name = "Rubro.findByNombre", query = "SELECT r FROM Rubro r WHERE r.nombre = :nombre"),
    @NamedQuery(name = "Rubro.findByActivo", query = "SELECT r FROM Rubro r WHERE r.activo = :activo")})
public class Rubro implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idrubro")
    private Integer idrubro;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "activo")
    private Boolean activo;
    @OneToMany(mappedBy = "rubroIdrubro")
    private List<Clasificacion> clasificacionList;

    public Rubro() {
    }

    public Rubro(Integer idrubro) {
        this.idrubro = idrubro;
    }

    public Rubro(Integer idrubro, String nombre) {
        this.idrubro = idrubro;
        this.nombre = nombre;
    }

    public Integer getIdrubro() {
        return idrubro;
    }

    public void setIdrubro(Integer idrubro) {
        this.idrubro = idrubro;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    @XmlTransient
    public List<Clasificacion> getClasificacionList() {
        return clasificacionList;
    }

    public void setClasificacionList(List<Clasificacion> clasificacionList) {
        this.clasificacionList = clasificacionList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idrubro != null ? idrubro.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Rubro)) {
            return false;
        }
        Rubro other = (Rubro) object;
        if ((this.idrubro == null && other.idrubro != null) || (this.idrubro != null && !this.idrubro.equals(other.idrubro))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.entities.Rubro[ idrubro=" + idrubro + " ]";
    }
    
}
