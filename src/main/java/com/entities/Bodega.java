/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author mmixco
 */
@Entity
@Table(name = "bodega")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Bodega.findAll", query = "SELECT b FROM Bodega b"),
    @NamedQuery(name = "Bodega.findByIdbodega", query = "SELECT b FROM Bodega b WHERE b.idbodega = :idbodega"),
    @NamedQuery(name = "Bodega.findByNombre", query = "SELECT b FROM Bodega b WHERE b.nombre = :nombre"),
    @NamedQuery(name = "Bodega.findByDescripcion", query = "SELECT b FROM Bodega b WHERE b.descripcion = :descripcion"),
    @NamedQuery(name = "Bodega.findByActivo", query = "SELECT b FROM Bodega b WHERE b.activo = true"),
    @NamedQuery(name = "Bodega.findByUsuarioCreate", query = "SELECT b FROM Bodega b WHERE b.usuarioCreate = :usuarioCreate"),
    @NamedQuery(name = "Bodega.findByFechaCreate", query = "SELECT b FROM Bodega b WHERE b.fechaCreate = :fechaCreate"),
    @NamedQuery(name = "Bodega.findByUsuarioUpdate", query = "SELECT b FROM Bodega b WHERE b.usuarioUpdate = :usuarioUpdate"),
    @NamedQuery(name = "Bodega.findByFechaUpdate", query = "SELECT b FROM Bodega b WHERE b.fechaUpdate = :fechaUpdate")})
public class Bodega implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idbodega")
    private Integer idbodega;
    @Size(max = 45)
    @Column(name = "nombre")
    private String nombre;
    @Size(max = 45)
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "activo")
    private Boolean activo;
    @Size(max = 45)
    @Column(name = "usuario_create")
    private String usuarioCreate;
    @Column(name = "fecha_create")
    @Temporal(TemporalType.DATE)
    private Date fechaCreate;
    @Size(max = 45)
    @Column(name = "usuario_update")
    private String usuarioUpdate;
    @Column(name = "fecha_update")
    @Temporal(TemporalType.DATE)
    private Date fechaUpdate;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "bodegaIdbodega")
    private List<InvMov> invMovList;

    public Bodega() {
    }

    public Bodega(Integer idbodega) {
        this.idbodega = idbodega;
    }

    public Integer getIdbodega() {
        return idbodega;
    }

    public void setIdbodega(Integer idbodega) {
        this.idbodega = idbodega;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public String getUsuarioCreate() {
        return usuarioCreate;
    }

    public void setUsuarioCreate(String usuarioCreate) {
        this.usuarioCreate = usuarioCreate;
    }

    public Date getFechaCreate() {
        return fechaCreate;
    }

    public void setFechaCreate(Date fechaCreate) {
        this.fechaCreate = fechaCreate;
    }

    public String getUsuarioUpdate() {
        return usuarioUpdate;
    }

    public void setUsuarioUpdate(String usuarioUpdate) {
        this.usuarioUpdate = usuarioUpdate;
    }

    public Date getFechaUpdate() {
        return fechaUpdate;
    }

    public void setFechaUpdate(Date fechaUpdate) {
        this.fechaUpdate = fechaUpdate;
    }

    @XmlTransient
    public List<InvMov> getInvMovList() {
        return invMovList;
    }

    public void setInvMovList(List<InvMov> invMovList) {
        this.invMovList = invMovList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idbodega != null ? idbodega.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Bodega)) {
            return false;
        }
        Bodega other = (Bodega) object;
        if ((this.idbodega == null && other.idbodega != null) || (this.idbodega != null && !this.idbodega.equals(other.idbodega))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.entities.Bodega[ idbodega=" + idbodega + " ]";
    }
    
}
