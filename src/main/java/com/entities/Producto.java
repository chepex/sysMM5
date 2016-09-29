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
@Table(name = "producto")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Producto.findAll", query = "SELECT p FROM Producto p"),
    @NamedQuery(name = "Producto.findByIdproducto", query = "SELECT p FROM Producto p WHERE p.idproducto = :idproducto"),
    @NamedQuery(name = "Producto.findByNombre", query = "SELECT p FROM Producto p WHERE p.nombre = :nombre"),
    @NamedQuery(name = "Producto.findByNombreCodigo", query = "SELECT p FROM Producto p WHERE p.nombre like :nombre and p.activo=1"),
    @NamedQuery(name = "Producto.findByCategoria", query = "SELECT p FROM Producto p WHERE p.categoriaIdcategoria = :categoria "),    
    
    @NamedQuery(name = "Producto.findByCategoriaNombre", query = "SELECT p FROM Producto p WHERE p.categoriaIdcategoria = :categoria and p.nombre like :nombre "),        
    @NamedQuery(name = "Producto.findByDescripion", query = "SELECT p FROM Producto p WHERE p.descripion = :descripion"),
    @NamedQuery(name = "Producto.findByCosto", query = "SELECT p FROM Producto p WHERE p.costo = :costo"),
    @NamedQuery(name = "Producto.findByPrecio", query = "SELECT p FROM Producto p WHERE p.precio = :precio"),
    @NamedQuery(name = "Producto.findByMin", query = "SELECT p FROM Producto p WHERE p.min = :min"),
    @NamedQuery(name = "Producto.findByMax", query = "SELECT p FROM Producto p WHERE p.max = :max"),
    @NamedQuery(name = "Producto.findByExistencia", query = "SELECT p FROM Producto p WHERE p.existencia = :existencia"),
    @NamedQuery(name = "Producto.findByActivo", query = "SELECT p FROM Producto p WHERE p.activo = true"),
    @NamedQuery(name = "Producto.findByUsuarioCreate", query = "SELECT p FROM Producto p WHERE p.usuarioCreate = :usuarioCreate"),
    @NamedQuery(name = "Producto.findByFechaCreate", query = "SELECT p FROM Producto p WHERE p.fechaCreate = :fechaCreate"),
    @NamedQuery(name = "Producto.findByUsuarioUpdate", query = "SELECT p FROM Producto p WHERE p.usuarioUpdate = :usuarioUpdate"),
    @NamedQuery(name = "Producto.findByFechaUpdate", query = "SELECT p FROM Producto p WHERE p.fechaUpdate = :fechaUpdate")})
public class Producto implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idproducto")
    private Integer idproducto;
    @Size(max = 45)
    @Column(name = "nombre")
    private String nombre;
    @Size(max = 50)
    @Column(name = "descripion")
    private String descripion;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "costo")
    private BigDecimal costo;
    @Column(name = "precio")
    private BigDecimal precio;
    @Column(name = "min")
    private Integer min;
    @Column(name = "max")
    private Integer max;
    @Column(name = "margen")
    private Integer margen;    
    @Column(name = "existencia")
    private Integer existencia;
    @Column(name = "activo")
    private Boolean activo;
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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "productoIdproducto")
    private List<FacturaDet> facturaDetList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "productoIdproducto")
    private List<InvDetm> invDetmList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "productoIdproducto")
    private List<CompraDet> compraDetList;
    @JoinColumn(name = "categoria_idcategoria", referencedColumnName = "idcategoria")
    @ManyToOne(optional = false)
    private Categoria categoriaIdcategoria;

    public Producto() {
    }

    public Integer getMargen() {
        return margen;
    }

    public void setMargen(Integer margen) {
        this.margen = margen;
    }

    
    public Producto(Integer idproducto) {
        this.idproducto = idproducto;
    }

    public Integer getIdproducto() {
        return idproducto;
    }

    public void setIdproducto(Integer idproducto) {
        this.idproducto = idproducto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripion() {
        return descripion;
    }

    public void setDescripion(String descripion) {
        this.descripion = descripion;
    }

    public BigDecimal getCosto() {
        return costo;
    }

    public void setCosto(BigDecimal costo) {
        this.costo = costo;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public Integer getMin() {
        return min;
    }

    public void setMin(Integer min) {
        this.min = min;
    }

    public Integer getMax() {
        return max;
    }

    public void setMax(Integer max) {
        this.max = max;
    }

    public Integer getExistencia() {
        return existencia;
    }

    public void setExistencia(Integer existencia) {
        this.existencia = existencia;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
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

    @XmlTransient
    public List<FacturaDet> getFacturaDetList() {
        return facturaDetList;
    }

    public void setFacturaDetList(List<FacturaDet> facturaDetList) {
        this.facturaDetList = facturaDetList;
    }

    @XmlTransient
    public List<InvDetm> getInvDetmList() {
        return invDetmList;
    }

    public void setInvDetmList(List<InvDetm> invDetmList) {
        this.invDetmList = invDetmList;
    }

    @XmlTransient
    public List<CompraDet> getCompraDetList() {
        return compraDetList;
    }

    public void setCompraDetList(List<CompraDet> compraDetList) {
        this.compraDetList = compraDetList;
    }

    public Categoria getCategoriaIdcategoria() {
        return categoriaIdcategoria;
    }

    public void setCategoriaIdcategoria(Categoria categoriaIdcategoria) {
        this.categoriaIdcategoria = categoriaIdcategoria;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idproducto != null ? idproducto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Producto)) {
            return false;
        }
        Producto other = (Producto) object;
        if ((this.idproducto == null && other.idproducto != null) || (this.idproducto != null && !this.idproducto.equals(other.idproducto))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.entities.Producto[ idproducto=" + idproducto + " ]";
    }
    
}

