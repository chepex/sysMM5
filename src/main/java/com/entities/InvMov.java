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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "inv_mov")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "InvMov.findAll", query = "SELECT i FROM InvMov i"),
    @NamedQuery(name = "InvMov.findByIdinvMov", query = "SELECT i FROM InvMov i WHERE i.idinvMov = :idinvMov"),
    @NamedQuery(name = "InvMov.findByNumDocto", query = "SELECT i FROM InvMov i WHERE i.numDocto = :numDocto"),
    @NamedQuery(name = "InvMov.findByDocumento", query = "SELECT i FROM InvMov i WHERE i.documentoIddocumento.iddocumento = :docto"),    
    @NamedQuery(name = "InvMov.findByDocumentoFecha", query = "SELECT i FROM InvMov i WHERE i.documentoIddocumento.iddocumento = :docto and i.fecha between :fi and :ff"),        
    @NamedQuery(name = "InvMov.findByFecha", query = "SELECT i FROM InvMov i WHERE i.fecha = :fecha"),
    @NamedQuery(name = "InvMov.findByEstado", query = "SELECT i FROM InvMov i WHERE i.estado = :estado"),
    @NamedQuery(name = "InvMov.findByCantidad", query = "SELECT i FROM InvMov i WHERE i.cantidad = :cantidad"),
    @NamedQuery(name = "InvMov.findByNumReferencia", query = "SELECT i FROM InvMov i WHERE i.numReferencia = :numReferencia"),
    @NamedQuery(name = "InvMov.findByUsuarioCreate", query = "SELECT i FROM InvMov i WHERE i.usuarioCreate = :usuarioCreate"),
    @NamedQuery(name = "InvMov.findByFechaCreate", query = "SELECT i FROM InvMov i WHERE i.fechaCreate = :fechaCreate"),
    @NamedQuery(name = "InvMov.findByUsuarioUpdate", query = "SELECT i FROM InvMov i WHERE i.usuarioUpdate = :usuarioUpdate"),
    @NamedQuery(name = "InvMov.findByFechaUpdate", query = "SELECT i FROM InvMov i WHERE i.fechaUpdate = :fechaUpdate")})
public class InvMov implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idinv_mov")
    private Integer idinvMov;
    @Size(max = 45)
    @Column(name = "num_docto")
    private String numDocto;
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Size(max = 1)
    @Column(name = "estado")
    private String estado;
    @Column(name = "cantidad")
    private Integer cantidad;
    @Column(name = "id_proveedor")
    private Integer idProveedor;
    @Size(max = 50)
    @Column(name = "num_referencia")
    private String numReferencia;
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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "invMovIdinvMov")
    private List<InvDetm> invDetmList;
    @JoinColumn(name = "usuario_idusuario", referencedColumnName = "idusuario")
    @ManyToOne(optional = false)
    private Usuario usuarioIdusuario;
    @JoinColumn(name = "documento_iddocumento", referencedColumnName = "iddocumento")
    @ManyToOne(optional = false)
    private Documento documentoIddocumento;
    @JoinColumn(name = "bodega_idbodega", referencedColumnName = "idbodega")
    @ManyToOne(optional = false)
    private Bodega bodegaIdbodega;

    public InvMov() {
    }

    public InvMov(Integer idinvMov) {
        this.idinvMov = idinvMov;
    }

    public Integer getIdinvMov() {
        return idinvMov;
    }

    public void setIdinvMov(Integer idinvMov) {
        this.idinvMov = idinvMov;
    }

    public String getNumDocto() {
        return numDocto;
    }

    public void setNumDocto(String numDocto) {
        this.numDocto = numDocto;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Integer getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(Integer idProveedor) {
        this.idProveedor = idProveedor;
    }

    public String getNumReferencia() {
        return numReferencia;
    }

    public void setNumReferencia(String numReferencia) {
        this.numReferencia = numReferencia;
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
    public List<InvDetm> getInvDetmList() {
        return invDetmList;
    }

    public void setInvDetmList(List<InvDetm> invDetmList) {
        this.invDetmList = invDetmList;
    }

    public Usuario getUsuarioIdusuario() {
        return usuarioIdusuario;
    }

    public void setUsuarioIdusuario(Usuario usuarioIdusuario) {
        this.usuarioIdusuario = usuarioIdusuario;
    }

    public Documento getDocumentoIddocumento() {
        return documentoIddocumento;
    }

    public void setDocumentoIddocumento(Documento documentoIddocumento) {
        this.documentoIddocumento = documentoIddocumento;
    }

    public Bodega getBodegaIdbodega() {
        return bodegaIdbodega;
    }

    public void setBodegaIdbodega(Bodega bodegaIdbodega) {
        this.bodegaIdbodega = bodegaIdbodega;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idinvMov != null ? idinvMov.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof InvMov)) {
            return false;
        }
        InvMov other = (InvMov) object;
        if ((this.idinvMov == null && other.idinvMov != null) || (this.idinvMov != null && !this.idinvMov.equals(other.idinvMov))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.entities.InvMov[ idinvMov=" + idinvMov + " ]";
    }
    
}
