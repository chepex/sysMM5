/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ticket;

 
import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
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
@Table(name = "ticket_problema")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TicketProblema.findAll", query = "SELECT t FROM TicketProblema t"),
    @NamedQuery(name = "TicketProblema.findByIdticketProblema", query = "SELECT t FROM TicketProblema t WHERE t.idticketProblema = :idticketProblema"),
    @NamedQuery(name = "TicketProblema.findByNombre", query = "SELECT t FROM TicketProblema t WHERE t.nombre = :nombre"),
    @NamedQuery(name = "TicketProblema.findByActivo", query = "SELECT t FROM TicketProblema t WHERE t.activo = :activo")})
public class TicketProblema implements Serializable {

    @OneToMany(mappedBy = "idticketProblema")
    private List<Ticket> ticketList;

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "idticket_problema")
    private Integer idticketProblema;
    @Size(max = 45)
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "activo")
    private Short activo;
 

    public TicketProblema() {
    }

    public TicketProblema(Integer idticketProblema) {
        this.idticketProblema = idticketProblema;
    }

    public Integer getIdticketProblema() {
        return idticketProblema;
    }

    public void setIdticketProblema(Integer idticketProblema) {
        this.idticketProblema = idticketProblema;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Short getActivo() {
        return activo;
    }

    public void setActivo(Short activo) {
        this.activo = activo;
    }

    @XmlTransient
 

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idticketProblema != null ? idticketProblema.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TicketProblema)) {
            return false;
        }
        TicketProblema other = (TicketProblema) object;
        if ((this.idticketProblema == null && other.idticketProblema != null) || (this.idticketProblema != null && !this.idticketProblema.equals(other.idticketProblema))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.entities.TicketProblema[ idticketProblema=" + idticketProblema + " ]";
    }

    @XmlTransient
    public List<Ticket> getTicketList() {
        return ticketList;
    }

    public void setTicketList(List<Ticket> ticketList) {
        this.ticketList = ticketList;
    }
    
}
