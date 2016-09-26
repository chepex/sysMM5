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
@Table(name = "factura")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Factura.findAll", query = "SELECT f FROM Factura f"),
    @NamedQuery(name = "Factura.findByIdfactura", query = "SELECT f FROM Factura f WHERE f.idfactura = :idfactura"),
    @NamedQuery(name = "Factura.findByDocumentoFecha", query = "SELECT f FROM Factura f WHERE f.fecha between :fi and :ff and f.clienteIdcliente.idcliente = :cliente "),
    
    @NamedQuery(name = "Factura.findByIdcliente", query = "SELECT f FROM Factura f WHERE f.clienteIdcliente.idcliente = :cliente"),    
    @NamedQuery(name = "Factura.findByIdclienteFecha", query = "SELECT f FROM Factura f WHERE f.clienteIdcliente.idcliente = :cliente and f.fecha between :fi and :ff"),  
    @NamedQuery(name = "Factura.findByDocumento", query = "SELECT f FROM Factura f WHERE f.documento = :documento"),
    @NamedQuery(name = "Factura.findByProducto", query = "SELECT f FROM Factura f, FacturaDet d WHERE f.idfactura = d.facturaIdfactura.idfactura and d.productoIdproducto.idproducto = :producto"),
    @NamedQuery(name = "Factura.findByClientePendiente", query = "SELECT f FROM Factura f WHERE f.saldo > 0 and f.clienteIdcliente.idcliente = :cliente "),    
    @NamedQuery(name = "Factura.findByFecha", query = "SELECT f FROM Factura f WHERE f.fecha = :fecha"),
    @NamedQuery(name = "Factura.findByCantidad", query = "SELECT f FROM Factura f WHERE f.cantidad = :cantidad"),
    @NamedQuery(name = "Factura.findByIva", query = "SELECT f FROM Factura f WHERE f.iva = :iva"),
    @NamedQuery(name = "Factura.findByRetencion", query = "SELECT f FROM Factura f WHERE f.retencion = :retencion"),
    @NamedQuery(name = "Factura.findByTotal", query = "SELECT f FROM Factura f WHERE f.total = :total"),
    @NamedQuery(name = "Factura.findByUsuarioCreate", query = "SELECT f FROM Factura f WHERE f.usuarioCreate = :usuarioCreate"),
    @NamedQuery(name = "Factura.findByFechaCreate", query = "SELECT f FROM Factura f WHERE f.fechaCreate = :fechaCreate"),
    @NamedQuery(name = "Factura.findByUsuarioUpdate", query = "SELECT f FROM Factura f WHERE f.usuarioUpdate = :usuarioUpdate"),
    @NamedQuery(name = "Factura.findByFechaUpdate", query = "SELECT f FROM Factura f WHERE f.fechaUpdate = :fechaUpdate")})
public class Factura implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idfactura")
    private Integer idfactura;
    @Size(max = 45)
    @Column(name = "documento")
    private String documento;
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Column(name = "cantidad")
    private Integer cantidad;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "iva")
    private BigDecimal iva;
    @Column(name = "retencion")
    private BigDecimal retencion;
    @Column(name = "saldo")
    private BigDecimal saldo;    
    @Column(name = "total")
    private BigDecimal total;
    @Column(name = "sub_total")
    private BigDecimal subTotal;    
    @Size(max = 45)
    @Column(name = "usuario_create")
    private String usuarioCreate;
    @Column(name = "fecha_create")
    @Temporal(TemporalType.DATE)
    private Date fechaCreate;
    @Size(max = 45)
    @Column(name = "usuario_update")
    private String usuarioUpdate;
    @Column(name = "fecha_update")
    @Temporal(TemporalType.DATE)
    private Date fechaUpdate;
    @JoinColumn(name = "usuario_idusuario", referencedColumnName = "idusuario")
    @ManyToOne(optional = false)
    private Usuario usuarioIdusuario;
    @JoinColumn(name = "cliente_idcliente", referencedColumnName = "idcliente")
    @ManyToOne(optional = false)
    private Cliente clienteIdcliente;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "facturaIdfactura")
    private List<FacturaDet> facturaDetList;
    @JoinColumn(name = "tipo_pago_idtipo_pago", referencedColumnName = "idtipo_pago")
    @ManyToOne(optional = false)
    private TipoPago tipoPagoIdtipoPago;    

    public Factura() {
    }

    public TipoPago getTipoPagoIdtipoPago() {
        return tipoPagoIdtipoPago;
    }

    public void setTipoPagoIdtipoPago(TipoPago tipoPagoIdtipoPago) {
        this.tipoPagoIdtipoPago = tipoPagoIdtipoPago;
    }
    
    

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    
    
    public Factura(Integer idfactura) {
        this.idfactura = idfactura;
    }

    public Integer getIdfactura() {
        return idfactura;
    }

    public BigDecimal getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(BigDecimal subTotal) {
        this.subTotal = subTotal;
    }

    
    public void setIdfactura(Integer idfactura) {
        this.idfactura = idfactura;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public BigDecimal getIva() {
        return iva;
    }

    public void setIva(BigDecimal iva) {
        this.iva = iva;
    }

    public BigDecimal getRetencion() {
        return retencion;
    }

    public void setRetencion(BigDecimal retencion) {
        this.retencion = retencion;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public String getUsuarioCreate() {
        return usuarioCreate;
    }

    public void setUsuarioCreate(String usuarioCreate) {
        this.usuarioCreate = usuarioCreate;
    }

    public Date getFechaCreate() {
        return fechaCreate;
    }

    public void setFechaCreate(Date fechaCreate) {
        this.fechaCreate = fechaCreate;
    }

    public String getUsuarioUpdate() {
        return usuarioUpdate;
    }

    public void setUsuarioUpdate(String usuarioUpdate) {
        this.usuarioUpdate = usuarioUpdate;
    }

    public Date getFechaUpdate() {
        return fechaUpdate;
    }

    public void setFechaUpdate(Date fechaUpdate) {
        this.fechaUpdate = fechaUpdate;
    }

    public Usuario getUsuarioIdusuario() {
        return usuarioIdusuario;
    }

    public void setUsuarioIdusuario(Usuario usuarioIdusuario) {
        this.usuarioIdusuario = usuarioIdusuario;
    }

    public Cliente getClienteIdcliente() {
        return clienteIdcliente;
    }

    public void setClienteIdcliente(Cliente clienteIdcliente) {
        this.clienteIdcliente = clienteIdcliente;
    }

    @XmlTransient
    public List<FacturaDet> getFacturaDetList() {
        return facturaDetList;
    }

    public void setFacturaDetList(List<FacturaDet> facturaDetList) {
        this.facturaDetList = facturaDetList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idfactura != null ? idfactura.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Factura)) {
            return false;
        }
        Factura other = (Factura) object;
        if ((this.idfactura == null && other.idfactura != null) || (this.idfactura != null && !this.idfactura.equals(other.idfactura))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.entities.Factura[ idfactura=" + idfactura + " ]";
    }
    
}
