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
@Table(name = "compra")
@XmlRootElement
@NamedQueries({
@NamedQuery(name = "Compra.findAll", query = "SELECT c FROM Compra c"),
    @NamedQuery(name = "Compra.findByIdcompra", query = "SELECT c FROM Compra c WHERE c.idcompra = :idcompra"),
    @NamedQuery(name = "Compra.findByProveedor", query = "SELECT c FROM Compra c WHERE c.proveedorIdproveedor.idproveedor = :prov"),
    @NamedQuery(name = "Compra.findByDocumento", query = "SELECT c FROM Compra c WHERE c.documento = :documento"),
    @NamedQuery(name = "Compra.findByProveedorPendiente", query = "SELECT c FROM Compra c WHERE c.proveedorIdproveedor.idproveedor = :proveedor and c.saldo > 0 "),    
    @NamedQuery(name = "Compra.findByProducto", query = "SELECT c FROM Compra c, CompraDet d WHERE c.idcompra =  d.compraIdcompra.idcompra and d.productoIdproducto.idproducto =  :producto"),
    @NamedQuery(name = "Compra.findByFecha", query = "SELECT c FROM Compra c WHERE c.fecha between :fi and :ff"),
    @NamedQuery(name = "Compra.findByFechaProv", query = "SELECT c FROM Compra c WHERE c.fecha between :fi and :ff and c.proveedorIdproveedor.idproveedor = :prov "),
    @NamedQuery(name = "Compra.findByTotal", query = "SELECT c FROM Compra c WHERE c.total = :total"),
    @NamedQuery(name = "Compra.findByUsuarioCreate", query = "SELECT c FROM Compra c WHERE c.usuarioCreate = :usuarioCreate"),
    @NamedQuery(name = "Compra.findByFechaCreate", query = "SELECT c FROM Compra c WHERE c.fechaCreate = :fechaCreate"),
    @NamedQuery(name = "Compra.findByUsuarioUpdate", query = "SELECT c FROM Compra c WHERE c.usuarioUpdate = :usuarioUpdate"),
    @NamedQuery(name = "Compra.findByFechaUpdate", query = "SELECT c FROM Compra c WHERE c.fechaUpdate = :fechaUpdate")})
public class Compra implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idcompra")
    private Integer idcompra;
    @Size(max = 45)
    @Column(name = "documento")
    private String documento;
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Column(name = "cantidad")
    private Integer cantidad;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "total")
    private BigDecimal total;
    @Column(name = "saldo")
    private BigDecimal saldo;    
    @Column(name = "iva")
    private BigDecimal iva;    
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
    @JoinColumn(name = "proveedor_idproveedor", referencedColumnName = "idproveedor")
    @ManyToOne(optional = false)
    private Proveedor proveedorIdproveedor;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "compraIdcompra")
    private List<CompraDet> compraDetList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "compraIdcompra")
    private List<PagoCompra> pagoCompraList;    
    @JoinColumn(name = "tipo_pago_idtipo_pago", referencedColumnName = "idtipo_pago")
    @ManyToOne(optional = false)
    private TipoPago tipoPagoIdtipoPago;
    
    public Compra() {
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

    
    
    public BigDecimal getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(BigDecimal subTotal) {
        this.subTotal = subTotal;
    }

    
    
    public BigDecimal getIva() {
        return iva;
    }

    public void setIva(BigDecimal iva) {
        this.iva = iva;
    }

    
    
    public Compra(Integer idcompra) {
        this.idcompra = idcompra;
    }

    public Integer getIdcompra() {
        return idcompra;
    }

    public void setIdcompra(Integer idcompra) {
        this.idcompra = idcompra;
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

    public Proveedor getProveedorIdproveedor() {
        return proveedorIdproveedor;
    }

    public void setProveedorIdproveedor(Proveedor proveedorIdproveedor) {
        this.proveedorIdproveedor = proveedorIdproveedor;
    }

    public List<PagoCompra> getPagoCompraList() {
        return pagoCompraList;
    }

    public void setPagoCompraList(List<PagoCompra> pagoCompraList) {
        this.pagoCompraList = pagoCompraList;
    }
    

    @XmlTransient
    public List<CompraDet> getCompraDetList() {
        return compraDetList;
    }

    public void setCompraDetList(List<CompraDet> compraDetList) {
        this.compraDetList = compraDetList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idcompra != null ? idcompra.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Compra)) {
            return false;
        }
        Compra other = (Compra) object;
        if ((this.idcompra == null && other.idcompra != null) || (this.idcompra != null && !this.idcompra.equals(other.idcompra))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.entities.Compra[ idcompra=" + idcompra + " ]";
    }
    
}
