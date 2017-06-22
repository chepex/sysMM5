/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ticket;

import java.io.Serializable;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author mmixco
 */
@Entity
@Table(name = "ticket_estado")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TicketEstado.findAll", query = "SELECT t FROM TicketEstado t"),
    @NamedQuery(name = "TicketEstado.findByIdticketEstado", query = "SELECT t FROM TicketEstado t WHERE t.idticketEstado = :idticketEstado"),
    @NamedQuery(name = "TicketEstado.findByNombre", query = "SELECT t FROM TicketEstado t WHERE t.nombre = :nombre")})
public class TicketEstado implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idticket_estado")
    private Integer idticketEstado;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "nombre")
    private String nombre;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idestado")
    private List<Ticket> ticketList;

    public TicketEstado() {
    }

    public TicketEstado(Integer idticketEstado) {
        this.idticketEstado = idticketEstado;
    }

    public TicketEstado(Integer idticketEstado, String nombre) {
        this.idticketEstado = idticketEstado;
        this.nombre = nombre;
    }

    public Integer getIdticketEstado() {
        return idticketEstado;
    }

    public void setIdticketEstado(Integer idticketEstado) {
        this.idticketEstado = idticketEstado;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idticketEstado != null ? idticketEstado.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TicketEstado)) {
            return false;
        }
        TicketEstado other = (TicketEstado) object;
        if ((this.idticketEstado == null && other.idticketEstado != null) || (this.idticketEstado != null && !this.idticketEstado.equals(other.idticketEstado))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.entities.TicketEstado[ idticketEstado=" + idticketEstado + " ]";
    }
    
}
