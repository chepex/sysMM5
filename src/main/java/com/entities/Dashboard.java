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
@Table(name = "cuenta_banco")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CuentaBanco.findAll", query = "SELECT c FROM CuentaBanco c"),
    @NamedQuery(name = "CuentaBanco.findByIdcuenta", query = "SELECT c FROM CuentaBanco c WHERE c.idcuenta = :idcuenta"),
    @NamedQuery(name = "CuentaBanco.findByIdbanco", query = "SELECT c FROM CuentaBanco c WHERE c.bancoIdbanco.idbanco = :banco  and c.activo = true"),
    @NamedQuery(name = "CuentaBanco.findByActivo", query = "SELECT c FROM CuentaBanco c WHERE c.activo = :activo"),
    @NamedQuery(name = "CuentaBanco.findByFechaCreate", query = "SELECT c FROM CuentaBanco c WHERE c.fechaCreate = :fechaCreate"),
    @NamedQuery(name = "CuentaBanco.findByFechaUpdate", query = "SELECT c FROM CuentaBanco c WHERE c.fechaUpdate = :fechaUpdate"),
    @NamedQuery(name = "CuentaBanco.findByNumero", query = "SELECT c FROM CuentaBanco c WHERE c.numero = :numero"),
    @NamedQuery(name = "CuentaBanco.findByUsuarioCreate", query = "SELECT c FROM CuentaBanco c WHERE c.usuarioCreate = :usuarioCreate"),
    @NamedQuery(name = "CuentaBanco.findByUsuarioUpdate", query = "SELECT c FROM CuentaBanco c WHERE c.usuarioUpdate = :usuarioUpdate"),
    @NamedQuery(name = "CuentaBanco.findBySaldo", query = "SELECT c FROM CuentaBanco c WHERE c.saldo = :saldo")})
public class Dashboard implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idcuenta")
    private Integer idcuenta;
    @Column(name = "activo")
    private Boolean activo;
    @Column(name = "fecha_create")
    @Temporal(TemporalType.DATE)
    private Date fechaCreate;
    @Column(name = "fecha_update")
    @Temporal(TemporalType.DATE)
    private Date fechaUpdate;
    @Size(max = 255)
    @Column(name = "numero")
    private String numero;
    @Size(max = 255)
    @Column(name = "usuario_create")
    private String usuarioCreate;
    @Size(max = 255)
    @Column(name = "usuario_update")
    private String usuarioUpdate;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "saldo")
    private BigDecimal saldo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cuentaBancoIdcuenta")
    private List<PagoFactura> pagoFacturaList;
    @JoinColumn(name = "banco_idbanco", referencedColumnName = "idbanco")
    @ManyToOne(optional = false)
    private Banco bancoIdbanco;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cuentaBancoIdcuenta")
    private List<TransaccionBanco> transaccionBancoList;
 

    public Dashboard() {
    }

    public Dashboard(Integer idcuenta) {
        this.idcuenta = idcuenta;
    }

    public Integer getIdcuenta() {
        return idcuenta;
    }

    public void setIdcuenta(Integer idcuenta) {
        this.idcuenta = idcuenta;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
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

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
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

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    @XmlTransient
    public List<PagoFactura> getPagoFacturaList() {
        return pagoFacturaList;
    }

    public void setPagoFacturaList(List<PagoFactura> pagoFacturaList) {
        this.pagoFacturaList = pagoFacturaList;
    }

    public Banco getBancoIdbanco() {
        return bancoIdbanco;
    }

    public void setBancoIdbanco(Banco bancoIdbanco) {
        this.bancoIdbanco = bancoIdbanco;
    }

    @XmlTransient
    public List<TransaccionBanco> getTransaccionBancoList() {
        return transaccionBancoList;
    }

    public void setTransaccionBancoList(List<TransaccionBanco> transaccionBancoList) {
        this.transaccionBancoList = transaccionBancoList;
    }

 

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idcuenta != null ? idcuenta.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Dashboard)) {
            return false;
        }
        Dashboard other = (Dashboard) object;
        if ((this.idcuenta == null && other.idcuenta != null) || (this.idcuenta != null && !this.idcuenta.equals(other.idcuenta))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.entities.CuentaBanco[ idcuenta=" + idcuenta + " ]";
    }
    
}
