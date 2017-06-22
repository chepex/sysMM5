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
@Table(name = "ticket_solucion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TicketSolucion.findAll", query = "SELECT t FROM TicketSolucion t"),
    @NamedQuery(name = "TicketSolucion.findByIdticketSolucion", query = "SELECT t FROM TicketSolucion t WHERE t.idticketSolucion = :idticketSolucion"),
    @NamedQuery(name = "TicketSolucion.findByNombre", query = "SELECT t FROM TicketSolucion t WHERE t.nombre = :nombre"),
    @NamedQuery(name = "TicketSolucion.findByActivo", query = "SELECT t FROM TicketSolucion t WHERE t.activo = :activo")})
public class TicketSolucion implements Serializable {

    @OneToMany(mappedBy = "idticketSolucion")
    private List<Ticket> ticketList;

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "idticket_solucion")
    private Integer idticketSolucion;
    @Size(max = 45)
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "activo")
    private Short activo;
 

    public TicketSolucion() {
    }

    public TicketSolucion(Integer idticketSolucion) {
        this.idticketSolucion = idticketSolucion;
    }

    public Integer getIdticketSolucion() {
        return idticketSolucion;
    }

    public void setIdticketSolucion(Integer idticketSolucion) {
        this.idticketSolucion = idticketSolucion;
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
        hash += (idticketSolucion != null ? idticketSolucion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TicketSolucion)) {
            return false;
        }
        TicketSolucion other = (TicketSolucion) object;
        if ((this.idticketSolucion == null && other.idticketSolucion != null) || (this.idticketSolucion != null && !this.idticketSolucion.equals(other.idticketSolucion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.entities.TicketSolucion[ idticketSolucion=" + idticketSolucion + " ]";
    }

    @XmlTransient
    public List<Ticket> getTicketList() {
        return ticketList;
    }

    public void setTicketList(List<Ticket> ticketList) {
        this.ticketList = ticketList;
    }
    
}
