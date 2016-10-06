/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author mmixco
 */
@Entity
@Table(name = "catalogo_cierre")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CatalogoCierre.findAll", query = "SELECT c FROM CatalogoCierre c"),
    @NamedQuery(name = "CatalogoCierre.findByIdcatalogoCierre", query = "SELECT c FROM CatalogoCierre c WHERE c.idcatalogoCierre = :idcatalogoCierre"),
    @NamedQuery(name = "CatalogoCierre.findByAnio", query = "SELECT c FROM CatalogoCierre c WHERE c.anio = :anio"),
    @NamedQuery(name = "CatalogoCierre.findByMes", query = "SELECT c FROM CatalogoCierre c WHERE c.mes = :mes"),
    @NamedQuery(name = "CatalogoCierre.findByCerrado", query = "SELECT c FROM CatalogoCierre c WHERE c.cerrado = :cerrado")})
public class CatalogoCierre implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idcatalogo_cierre")
    private Integer idcatalogoCierre;
    @Column(name = "anio")
    private Integer anio;
    @Column(name = "mes")
    private Integer mes;
    @Column(name = "cerrado")
    private Boolean cerrado;

    public CatalogoCierre() {
    }

    public CatalogoCierre(Integer idcatalogoCierre) {
        this.idcatalogoCierre = idcatalogoCierre;
    }

    public Integer getIdcatalogoCierre() {
        return idcatalogoCierre;
    }

    public void setIdcatalogoCierre(Integer idcatalogoCierre) {
        this.idcatalogoCierre = idcatalogoCierre;
    }

    public Integer getAnio() {
        return anio;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    }

    public Integer getMes() {
        return mes;
    }

    public void setMes(Integer mes) {
        this.mes = mes;
    }

    public Boolean getCerrado() {
        return cerrado;
    }

    public void setCerrado(Boolean cerrado) {
        this.cerrado = cerrado;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idcatalogoCierre != null ? idcatalogoCierre.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CatalogoCierre)) {
            return false;
        }
        CatalogoCierre other = (CatalogoCierre) object;
        if ((this.idcatalogoCierre == null && other.idcatalogoCierre != null) || (this.idcatalogoCierre != null && !this.idcatalogoCierre.equals(other.idcatalogoCierre))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.entities.CatalogoCierre[ idcatalogoCierre=" + idcatalogoCierre + " ]";
    }
    
}
