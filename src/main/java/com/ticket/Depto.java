/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ticket;

import com.entities.Usuario;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name = "depto")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Depto.findAll", query = "SELECT d FROM Depto d"),
    @NamedQuery(name = "Depto.findByIddepto", query = "SELECT d FROM Depto d WHERE d.iddepto = :iddepto"),
    @NamedQuery(name = "Depto.findByNombre", query = "SELECT d FROM Depto d WHERE d.nombre = :nombre")})
public class Depto implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "iddepto")
    private Integer iddepto;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "nombre")
    private String nombre;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "iddepto")
    private List<Ticket> ticketList;
    @OneToMany(mappedBy = "iddepto")
    private List<Usuario> usuarioList;

    public Depto() {
    }

    public Depto(Integer iddepto) {
        this.iddepto = iddepto;
    }

    public Depto(Integer iddepto, String nombre) {
        this.iddepto = iddepto;
        this.nombre = nombre;
    }

    public Integer getIddepto() {
        return iddepto;
    }

    public void setIddepto(Integer iddepto) {
        this.iddepto = iddepto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @XmlTransient
    public List<Ticket> getTicketList() {
        return ticketList;
    }

    public void setTicketList(List<Ticket> ticketList) {
        this.ticketList = ticketList;
    }

    @XmlTransient
    public List<Usuario> getUsuarioList() {
        return usuarioList;
    }

    public void setUsuarioList(List<Usuario> usuarioList) {
        this.usuarioList = usuarioList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iddepto != null ? iddepto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Depto)) {
            return false;
        }
        Depto other = (Depto) object;
        if ((this.iddepto == null && other.iddepto != null) || (this.iddepto != null && !this.iddepto.equals(other.iddepto))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.entities.Depto[ iddepto=" + iddepto + " ]";
    }
    
}
