/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ticket;

import com.entities.Usuario;
import java.io.Serializable;
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
@Table(name = "ticket_mensaje")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TicketMensaje.findAll", query = "SELECT t FROM TicketMensaje t"),
    @NamedQuery(name = "TicketMensaje.findByIdticketMensaje", query = "SELECT t FROM TicketMensaje t WHERE t.idticketMensaje = :idticketMensaje"),
    @NamedQuery(name = "TicketMensaje.findByTIcket", query = "SELECT t FROM TicketMensaje t WHERE t.idticket.idticket = :idticket"),
    @NamedQuery(name = "TicketMensaje.findByDescripcion", query = "SELECT t FROM TicketMensaje t WHERE t.descripcion = :descripcion"),
    @NamedQuery(name = "TicketMensaje.findByFecha", query = "SELECT t FROM TicketMensaje t WHERE t.fecha = :fecha"),
    @NamedQuery(name = "TicketMensaje.findByCorrelativo", query = "SELECT t FROM TicketMensaje t WHERE t.correlativo = :correlativo")})
public class TicketMensaje implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "idticket_mensaje")
    private Integer idticketMensaje;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 545)
    @Column(name = "descripcion")
    private String descripcion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Basic(optional = false)
    @NotNull
    @Column(name = "correlativo")
    private int correlativo;
    @JoinColumn(name = "idticket", referencedColumnName = "idticket")
    @ManyToOne(optional = false)
    private Ticket idticket;
    @JoinColumn(name = "idusuario", referencedColumnName = "idusuario")
    @ManyToOne(optional = false)
    private Usuario idusuario;

    public TicketMensaje() {
    }

    public TicketMensaje(Integer idticketMensaje) {
        this.idticketMensaje = idticketMensaje;
    }

    public TicketMensaje(Integer idticketMensaje, String descripcion, Date fecha, int correlativo) {
        this.idticketMensaje = idticketMensaje;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.correlativo = correlativo;
    }

    public Integer getIdticketMensaje() {
        return idticketMensaje;
    }

    public void setIdticketMensaje(Integer idticketMensaje) {
        this.idticketMensaje = idticketMensaje;
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

    public int getCorrelativo() {
        return correlativo;
    }

    public void setCorrelativo(int correlativo) {
        this.correlativo = correlativo;
    }

    public Ticket getIdticket() {
        return idticket;
    }

    public void setIdticket(Ticket idticket) {
        this.idticket = idticket;
    }

    public Usuario getIdusuario() {
        return idusuario;
    }

    public void setIdusuario(Usuario idusuario) {
        this.idusuario = idusuario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idticketMensaje != null ? idticketMensaje.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TicketMensaje)) {
            return false;
        }
        TicketMensaje other = (TicketMensaje) object;
        if ((this.idticketMensaje == null && other.idticketMensaje != null) || (this.idticketMensaje != null && !this.idticketMensaje.equals(other.idticketMensaje))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.entities.TicketMensaje[ idticketMensaje=" + idticketMensaje + " ]";
    }
    
}
