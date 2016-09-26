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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "pago_factura")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PagoFactura.findAll", query = "SELECT p FROM PagoFactura p"),
    @NamedQuery(name = "PagoFactura.findByIdpagoFactura", query = "SELECT p FROM PagoFactura p WHERE p.idpagoFactura = :idpagoFactura"),
    @NamedQuery(name = "PagoFactura.findByIdfactura", query = "SELECT p FROM PagoFactura p WHERE p.facturaIdfactura.idfactura = :factura"),    
    @NamedQuery(name = "PagoFactura.findByFecha", query = "SELECT p FROM PagoFactura p WHERE p.fecha = :fecha"),
    @NamedQuery(name = "PagoFactura.findByFechaCreate", query = "SELECT p FROM PagoFactura p WHERE p.fechaCreate = :fechaCreate"),
    @NamedQuery(name = "PagoFactura.findByFechaUpdate", query = "SELECT p FROM PagoFactura p WHERE p.fechaUpdate = :fechaUpdate"),
    @NamedQuery(name = "PagoFactura.findByUsuarioCreate", query = "SELECT p FROM PagoFactura p WHERE p.usuarioCreate = :usuarioCreate"),
    @NamedQuery(name = "PagoFactura.findByUsuarioUpdate", query = "SELECT p FROM PagoFactura p WHERE p.usuarioUpdate = :usuarioUpdate"),
    @NamedQuery(name = "PagoFactura.findByValor", query = "SELECT p FROM PagoFactura p WHERE p.valor = :valor")})
public class PagoFactura implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idpago_factura")
    private Integer idpagoFactura;
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
    @Column(name = "usuario_create")
    private String usuarioCreate;
    @Size(max = 255)
    @Column(name = "usuario_update")
    private String usuarioUpdate;
    @Column(name = "valor")
    private BigInteger valor;
    @JoinColumn(name = "factura_idfactura", referencedColumnName = "idfactura")
    @ManyToOne(optional = false)
    private Factura facturaIdfactura;
    @JoinColumn(name = "cuenta_banco_idcuenta", referencedColumnName = "idcuenta")
    @ManyToOne(optional = false)
    private CuentaBanco cuentaBancoIdcuenta;
    @JoinColumn(name = "banco_idbanco", referencedColumnName = "idbanco")
    @ManyToOne(optional = false)
    private Banco bancoIdbanco;

    public PagoFactura() {
    }

    public PagoFactura(Integer idpagoFactura) {
        this.idpagoFactura = idpagoFactura;
    }

    public Integer getIdpagoFactura() {
        return idpagoFactura;
    }

    public void setIdpagoFactura(Integer idpagoFactura) {
        this.idpagoFactura = idpagoFactura;
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

    public Factura getFacturaIdfactura() {
        return facturaIdfactura;
    }

    public void setFacturaIdfactura(Factura facturaIdfactura) {
        this.facturaIdfactura = facturaIdfactura;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idpagoFactura != null ? idpagoFactura.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PagoFactura)) {
            return false;
        }
        PagoFactura other = (PagoFactura) object;
        if ((this.idpagoFactura == null && other.idpagoFactura != null) || (this.idpagoFactura != null && !this.idpagoFactura.equals(other.idpagoFactura))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.entities.PagoFactura[ idpagoFactura=" + idpagoFactura + " ]";
    }
    
}
