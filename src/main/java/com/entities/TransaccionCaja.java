/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
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
@Table(name = "transaccion_caja")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TransaccionCaja.findAll", query = "SELECT t FROM TransaccionCaja t"),
    @NamedQuery(name = "TransaccionCaja.findByIdtransaccion", query = "SELECT t FROM TransaccionCaja t WHERE t.idtransaccion = :idtransaccion"),
    @NamedQuery(name = "TransaccionCaja.findByFecha", query = "SELECT t FROM TransaccionCaja t WHERE t.fecha = :fecha"),
    @NamedQuery(name = "TransaccionCaja.findBySubtotal", query = "SELECT t FROM TransaccionCaja t WHERE t.subtotal = :subtotal"),
    @NamedQuery(name = "TransaccionCaja.findByIva", query = "SELECT t FROM TransaccionCaja t WHERE t.iva = :iva"),
    @NamedQuery(name = "TransaccionCaja.findByTotal", query = "SELECT t FROM TransaccionCaja t WHERE t.total = :total"),
    @NamedQuery(name = "TransaccionCaja.findByDescripcion", query = "SELECT t FROM TransaccionCaja t WHERE t.descripcion = :descripcion"),
    @NamedQuery(name = "TransaccionCaja.findByDocReferencia", query = "SELECT t FROM TransaccionCaja t WHERE t.docReferencia = :docReferencia")})
public class TransaccionCaja implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idtransaccion")
    private Integer idtransaccion;
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "subtotal")
    private BigDecimal subtotal;
    @Column(name = "iva")
    private BigDecimal iva;
    @Column(name = "total")
    private BigDecimal total;
    @Size(max = 250)
    @Column(name = "descripcion")
    private String descripcion;
    @Size(max = 250)
    @Column(name = "doc_referencia")
    private String docReferencia;
    @JoinColumn(name = "idcaja_chica", referencedColumnName = "idcaja_chica")
    @ManyToOne
    private CajaChica idcajaChica;
    @JoinColumn(name = "idusuario", referencedColumnName = "idusuario")
    @ManyToOne
    private Usuario idusuario;
    @OneToMany(mappedBy = "idtransaccion")
    private List<DetalleCaja> detalleCajaList;

    public TransaccionCaja() {
    }

    public TransaccionCaja(Integer idtransaccion) {
        this.idtransaccion = idtransaccion;
    }

    public Integer getIdtransaccion() {
        return idtransaccion;
    }

    public void setIdtransaccion(Integer idtransaccion) {
        this.idtransaccion = idtransaccion;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    public BigDecimal getIva() {
        return iva;
    }

    public void setIva(BigDecimal iva) {
        this.iva = iva;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDocReferencia() {
        return docReferencia;
    }

    public void setDocReferencia(String docReferencia) {
        this.docReferencia = docReferencia;
    }

    public CajaChica getIdcajaChica() {
        return idcajaChica;
    }

    public void setIdcajaChica(CajaChica idcajaChica) {
        this.idcajaChica = idcajaChica;
    }

    public Usuario getIdusuario() {
        return idusuario;
    }

    public void setIdusuario(Usuario idusuario) {
        this.idusuario = idusuario;
    }

    @XmlTransient
    public List<DetalleCaja> getDetalleCajaList() {
        return detalleCajaList;
    }

    public void setDetalleCajaList(List<DetalleCaja> detalleCajaList) {
        this.detalleCajaList = detalleCajaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idtransaccion != null ? idtransaccion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TransaccionCaja)) {
            return false;
        }
        TransaccionCaja other = (TransaccionCaja) object;
        if ((this.idtransaccion == null && other.idtransaccion != null) || (this.idtransaccion != null && !this.idtransaccion.equals(other.idtransaccion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.entities.TransaccionCaja[ idtransaccion=" + idtransaccion + " ]";
    }
    
}
