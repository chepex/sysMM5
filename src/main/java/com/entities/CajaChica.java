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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author mmixco
 */
@Entity
@Table(name = "caja_chica")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CajaChica.findAll", query = "SELECT c FROM CajaChica c"),
    @NamedQuery(name = "CajaChica.findByIdcajaChica", query = "SELECT c FROM CajaChica c WHERE c.idcajaChica = :idcajaChica"),
    @NamedQuery(name = "CajaChica.findByFecha", query = "SELECT c FROM CajaChica c WHERE c.fecha = :fecha"),
    @NamedQuery(name = "CajaChica.findByMontoInicial", query = "SELECT c FROM CajaChica c WHERE c.montoInicial = :montoInicial"),
    @NamedQuery(name = "CajaChica.findByMontoFinal", query = "SELECT c FROM CajaChica c WHERE c.montoFinal = :montoFinal"),
    @NamedQuery(name = "CajaChica.findByAbierta", query = "SELECT c FROM CajaChica c WHERE c.abierta = :abierta"),
    @NamedQuery(name = "CajaChica.findBySaldo", query = "SELECT c FROM CajaChica c WHERE c.saldo = :saldo")})
public class CajaChica implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idcaja_chica")
    private Integer idcajaChica;
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "monto_inicial")
    private BigDecimal montoInicial;
    @Column(name = "monto_final")
    private BigDecimal montoFinal;
    @Column(name = "abierta")
    private Boolean abierta;
    @Column(name = "saldo")
    private BigDecimal saldo;
    @OneToMany(mappedBy = "idcajaChica")
    private List<TransaccionCaja> transaccionCajaList;

    public CajaChica() {
    }

    public CajaChica(Integer idcajaChica) {
        this.idcajaChica = idcajaChica;
    }

    public Integer getIdcajaChica() {
        return idcajaChica;
    }

    public void setIdcajaChica(Integer idcajaChica) {
        this.idcajaChica = idcajaChica;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public BigDecimal getMontoInicial() {
        return montoInicial;
    }

    public void setMontoInicial(BigDecimal montoInicial) {
        this.montoInicial = montoInicial;
    }

    public BigDecimal getMontoFinal() {
        return montoFinal;
    }

    public void setMontoFinal(BigDecimal montoFinal) {
        this.montoFinal = montoFinal;
    }

    public Boolean getAbierta() {
        return abierta;
    }

    public void setAbierta(Boolean abierta) {
        this.abierta = abierta;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    @XmlTransient
    public List<TransaccionCaja> getTransaccionCajaList() {
        return transaccionCajaList;
    }

    public void setTransaccionCajaList(List<TransaccionCaja> transaccionCajaList) {
        this.transaccionCajaList = transaccionCajaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idcajaChica != null ? idcajaChica.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CajaChica)) {
            return false;
        }
        CajaChica other = (CajaChica) object;
        if ((this.idcajaChica == null && other.idcajaChica != null) || (this.idcajaChica != null && !this.idcajaChica.equals(other.idcajaChica))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.entities.CajaChica[ idcajaChica=" + idcajaChica + " ]";
    }
    
}
