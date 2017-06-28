/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ticket;

import com.entities.Usuario;
import java.io.Serializable;
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
import javax.validation.constraints.NotNull;
 
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author mmixco
 */
@Entity
@Table(name = "ticket")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Ticket.findAll", query = "SELECT t FROM Ticket t"),
    @NamedQuery(name = "Ticket.findByIdticket", query = "SELECT t FROM Ticket t WHERE t.idticket = :idticket"),
    @NamedQuery(name = "Ticket.findByFechaCreacion", query = "SELECT t FROM Ticket t WHERE t.fechaCreacion = :fechaCreacion"),
    @NamedQuery(name = "Ticket.findByIdusuarioAsignado", query = "SELECT t FROM Ticket t WHERE t.idusuarioAsignado = :idusuarioAsignado"),
    @NamedQuery(name = "Ticket.findByUsuario", query = "SELECT t FROM Ticket t WHERE t.idusuario.idusuario = :idusuario  "),
    @NamedQuery(name = "Ticket.findByUsuarioDepto", query = "SELECT t FROM Ticket t WHERE t.idusuario.iddepto.iddepto = :iddepto  "),
    @NamedQuery(name = "Ticket.findByTecnico", query = "SELECT t FROM Ticket t WHERE t.idusuarioAsignado.idusuario = :idusuario"),    
    @NamedQuery(name = "Ticket.findByTecnicoDepto", query = "SELECT t FROM Ticket t WHERE t.idusuarioAsignado.iddepto.iddepto = :iddepto"),    
    @NamedQuery(name = "Ticket.findByDepto", query = "SELECT t FROM Ticket t WHERE t.iddepto.iddepto = :iddepto"),
    @NamedQuery(name = "Ticket.findByTitulo", query = "SELECT t FROM Ticket t WHERE t.titulo = :titulo"),
    @NamedQuery(name = "Ticket.findByFechaAsignado", query = "SELECT t FROM Ticket t WHERE t.fechaAsignado = :fechaAsignado"),
    @NamedQuery(name = "Ticket.findByFechaFinalizado", query = "SELECT t FROM Ticket t WHERE t.fechaFinalizado = :fechaFinalizado")})
public class Ticket implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idticket")
    private Integer idticket;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_creacion")
    @Temporal(TemporalType.DATE)
    private Date fechaCreacion;
    
    @Column(name = "titulo")
    private String titulo;
    @Column(name = "fecha_asignado")
    @Temporal(TemporalType.DATE)
    private Date fechaAsignado;
    @Column(name = "fecha_finalizado")
    @Temporal(TemporalType.DATE)
    private Date fechaFinalizado;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idticket")
    private List<TicketMensaje> ticketMensajeList;
    @JoinColumn(name = "idusuario", referencedColumnName = "idusuario")
    @ManyToOne(optional = false)
    private Usuario idusuario;
    @JoinColumn(name = "idestado", referencedColumnName = "idticket_estado")
    @ManyToOne(optional = false)
    private TicketEstado idestado;
    @JoinColumn(name = "iddepto", referencedColumnName = "iddepto")
    @ManyToOne(optional = false)
    private Depto iddepto;
    @JoinColumn(name = "idusuario_asignado", referencedColumnName = "idusuario")
    @ManyToOne(optional = false)
    private Usuario idusuarioAsignado;  
    @JoinColumn(name = "idticket_solucion", referencedColumnName = "idticket_solucion")
    @ManyToOne
    private TicketSolucion idticketSolucion;
    @JoinColumn(name = "idticket_problema", referencedColumnName = "idticket_problema")
    @ManyToOne
    private TicketProblema idticketProblema;    

    public Ticket() {
    }

    public TicketSolucion getIdticketSolucion() {
        return idticketSolucion;
    }

    public void setIdticketSolucion(TicketSolucion idticketSolucion) {
        this.idticketSolucion = idticketSolucion;
    }

    public TicketProblema getIdticketProblema() {
        return idticketProblema;
    }

    public void setIdticketProblema(TicketProblema idticketProblema) {
        this.idticketProblema = idticketProblema;
    }

    
    
    public Ticket(Integer idticket) {
        this.idticket = idticket;
    }

    public Ticket(Integer idticket, Date fechaCreacion, String titulo) {
        this.idticket = idticket;
        this.fechaCreacion = fechaCreacion;
        this.titulo = titulo;
    }

    public Integer getIdticket() {
        return idticket;
    }

    public void setIdticket(Integer idticket) {
        this.idticket = idticket;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Usuario getIdusuarioAsignado() {
        return idusuarioAsignado;
    }

    public void setIdusuarioAsignado(Usuario idusuarioAsignado) {
        this.idusuarioAsignado = idusuarioAsignado;
    }

    
    

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Date getFechaAsignado() {
        return fechaAsignado;
    }

    public void setFechaAsignado(Date fechaAsignado) {
        this.fechaAsignado = fechaAsignado;
    }

    public Date getFechaFinalizado() {
        return fechaFinalizado;
    }

    public void setFechaFinalizado(Date fechaFinalizado) {
        this.fechaFinalizado = fechaFinalizado;
    }

    @XmlTransient
    public List<TicketMensaje> getTicketMensajeList() {
        return ticketMensajeList;
    }

    public void setTicketMensajeList(List<TicketMensaje> ticketMensajeList) {
        this.ticketMensajeList = ticketMensajeList;
    }

    public Usuario getIdusuario() {
        return idusuario;
    }

    public void setIdusuario(Usuario idusuario) {
        this.idusuario = idusuario;
    }

    public TicketEstado getIdestado() {
        return idestado;
    }

    public void setIdestado(TicketEstado idestado) {
        this.idestado = idestado;
    }

    public Depto getIddepto() {
        return iddepto;
    }

    public void setIddepto(Depto iddepto) {
        this.iddepto = iddepto;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idticket != null ? idticket.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Ticket)) {
            return false;
        }
        Ticket other = (Ticket) object;
        if ((this.idticket == null && other.idticket != null) || (this.idticket != null && !this.idticket.equals(other.idticket))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.entities.Ticket[ idticket=" + idticket + " ]";
    }
    
}
