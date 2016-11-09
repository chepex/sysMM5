/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.entities;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author mmixco
 */
@Entity
@Table(name = "transaccion_banco")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TransaccionBanco.findAll", query = "SELECT t FROM TransaccionBanco t"),
    @NamedQuery(name = "TransaccionBanco.findByIdtransaccionBanco", query = "SELECT t FROM TransaccionBanco t WHERE t.idtransaccionBanco = :idtransaccionBanco"),
    @NamedQuery(name = "TransaccionBanco.findByDescripcion", query = "SELECT t FROM TransaccionBanco t WHERE t.descripcion = :descripcion"),
    @NamedQuery(name = "TransaccionBanco.findByBancoCuenta", query = "SELECT t FROM TransaccionBanco t WHERE t.bancoIdbanco.idbanco = :idbanco and t.cuentaBancoIdcuenta.idcuenta = :cuenta"),    
    @NamedQuery(name = "TransaccionBanco.findByFecha", query = "SELECT t FROM TransaccionBanco t WHERE t.fecha = :fecha"),
    @NamedQuery(name = "TransaccionBanco.findByFechaCreate", query = "SELECT t FROM TransaccionBanco t WHERE t.fechaCreate = :fechaCreate"),
    @NamedQuery(name = "TransaccionBanco.findByFechaUpdate", query = "SELECT t FROM TransaccionBanco t WHERE t.fechaUpdate = :fechaUpdate"),
    @NamedQuery(name = "TransaccionBanco.findByReferencia", query = "SELECT t FROM TransaccionBanco t WHERE t.referencia = :referencia"),
    @NamedQuery(name = "TransaccionBanco.findByUsuarioCreate", query = "SELECT t FROM TransaccionBanco t WHERE t.usuarioCreate = :usuarioCreate"),
    @NamedQuery(name = "TransaccionBanco.findByUsuarioUpdate", query = "SELECT t FROM TransaccionBanco t WHERE t.usuarioUpdate = :usuarioUpdate"),
    @NamedQuery(name = "TransaccionBanco.findByValor", query = "SELECT t FROM TransaccionBanco t WHERE t.valor = :valor")})
public class TransaccionBanco implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "idtransaccion_banco")
    private Integer idtransaccionBanco;
    @Size(max = 255)
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Column(name = "fecha_create")
    @Temporal(TemporalType.DATE)
    private Date fechaCreate;
    @Column(name = "fecha_update")
    @Temporal(TemporalType.DATE)
    private Date fechaUpdate;
    @Size(max = 255)
    @Column(name = "referencia")
    private String referencia;
    @Size(max = 255)
    @Column(name = "usuario_create")
    private String usuarioCreate;
    @Size(max = 255)
    @Column(name = "usuario_update")
    private String usuarioUpdate;
    @Column(name = "valor")
    private BigInteger valor;
    @JoinColumn(name = "cuenta_banco_idcuenta", referencedColumnName = "idcuenta")
    @ManyToOne(optional = false)
    private CuentaBanco cuentaBancoIdcuenta;
    @JoinColumn(name = "banco_idbanco", referencedColumnName = "idbanco")
    @ManyToOne(optional = false)
    private Banco bancoIdbanco;
    @JoinColumn(name = "tipo_transaccion_idtipo_transaccion", referencedColumnName = "idtipo_transaccion")
    @ManyToOne(optional = false)
    private TipoTransaccion tipoTransaccionIdtipoTransaccion;

    public TransaccionBanco() {
    }

    public TransaccionBanco(Integer idtransaccionBanco) {
        this.idtransaccionBanco = idtransaccionBanco;
    }

    public Integer getIdtransaccionBanco() {
        return idtransaccionBanco;
    }

    public void setIdtransaccionBanco(Integer idtransaccionBanco) {
        this.idtransaccionBanco = idtransaccionBanco;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Date getFechaCreate() {
        return fechaCreate;
    }

    public void setFechaCreate(Date fechaCreate) {
        this.fechaCreate = fechaCreate;
    }

    public Date getFechaUpdate() {
        return fechaUpdate;
    }

    public void setFechaUpdate(Date fechaUpdate) {
        this.fechaUpdate = fechaUpdate;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public String getUsuarioCreate() {
        return usuarioCreate;
    }

    public void setUsuarioCreate(String usuarioCreate) {
        this.usuarioCreate = usuarioCreate;
    }

    public String getUsuarioUpdate() {
        return usuarioUpdate;
    }

    public void setUsuarioUpdate(String usuarioUpdate) {
        this.usuarioUpdate = usuarioUpdate;
    }

    public BigInteger getValor() {
        return valor;
    }

    public void setValor(BigInteger valor) {
        this.valor = valor;
    }

    public CuentaBanco getCuentaBancoIdcuenta() {
        return cuentaBancoIdcuenta;
    }

    public void setCuentaBancoIdcuenta(CuentaBanco cuentaBancoIdcuenta) {
        this.cuentaBancoIdcuenta = cuentaBancoIdcuenta;
    }

    public Banco getBancoIdbanco() {
        return bancoIdbanco;
    }

    public void setBancoIdbanco(Banco bancoIdbanco) {
        this.bancoIdbanco = bancoIdbanco;
    }

    public TipoTransaccion getTipoTransaccionIdtipoTransaccion() {
        return tipoTransaccionIdtipoTransaccion;
    }

    public void setTipoTransaccionIdtipoTransaccion(TipoTransaccion tipoTransaccionIdtipoTransaccion) {
        this.tipoTransaccionIdtipoTransaccion = tipoTransaccionIdtipoTransaccion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idtransaccionBanco != null ? idtransaccionBanco.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TransaccionBanco)) {
            return false;
        }
        TransaccionBanco other = (TransaccionBanco) object;
        if ((this.idtransaccionBanco == null && other.idtransaccionBanco != null) || (this.idtransaccionBanco != null && !this.idtransaccionBanco.equals(other.idtransaccionBanco))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.entities.TransaccionBanco[ idtransaccionBanco=" + idtransaccionBanco + " ]";
    }
    
}
