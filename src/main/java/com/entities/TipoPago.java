/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author mmixco
 */
@Entity
@Table(name = "tipo_pago")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipoPago.findAll", query = "SELECT t FROM TipoPago t"),
    @NamedQuery(name = "TipoPago.findByIdtipoPago", query = "SELECT t FROM TipoPago t WHERE t.idtipoPago = :idtipoPago"),
    @NamedQuery(name = "TipoPago.findByNombre", query = "SELECT t FROM TipoPago t WHERE t.nombre = :nombre"),
    @NamedQuery(name = "TipoPago.findByActivo", query = "SELECT t FROM TipoPago t WHERE t.activo = true"),
    @NamedQuery(name = "TipoPago.findByUsuarioCreate", query = "SELECT t FROM TipoPago t WHERE t.usuarioCreate = :usuarioCreate"),
    @NamedQuery(name = "TipoPago.findByFechaCreate", query = "SELECT t FROM TipoPago t WHERE t.fechaCreate = :fechaCreate"),
    @NamedQuery(name = "TipoPago.findByUsuarioUpdate", query = "SELECT t FROM TipoPago t WHERE t.usuarioUpdate = :usuarioUpdate"),
    @NamedQuery(name = "TipoPago.findByFechaUpdate", query = "SELECT t FROM TipoPago t WHERE t.fechaUpdate = :fechaUpdate")})
public class TipoPago implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idtipo_pago")
    private Integer idtipoPago;
    @Size(max = 45)
    @Column(name = "nombre")
    private String nombre;
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

    public TipoPago() {
    }

    public TipoPago(Integer idtipoPago) {
        this.idtipoPago = idtipoPago;
    }

    public Integer getIdtipoPago() {
        return idtipoPago;
    }

    public void setIdtipoPago(Integer idtipoPago) {
        this.idtipoPago = idtipoPago;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idtipoPago != null ? idtipoPago.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoPago)) {
            return false;
        }
        TipoPago other = (TipoPago) object;
        if ((this.idtipoPago == null && other.idtipoPago != null) || (this.idtipoPago != null && !this.idtipoPago.equals(other.idtipoPago))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.entities.TipoPago[ idtipoPago=" + idtipoPago + " ]";
    }
    
}
