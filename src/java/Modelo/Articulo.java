/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
 * @author Inpeca
 */
@Entity
@Table(name = "articulo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Articulo.findAll", query = "SELECT a FROM Articulo a"),
    @NamedQuery(name = "Articulo.findByCodigo", query = "SELECT a FROM Articulo a WHERE a.codigo = :codigo"),
    @NamedQuery(name = "Articulo.findByDescripcion", query = "SELECT a FROM Articulo a WHERE a.descripcion = :descripcion"),
    @NamedQuery(name = "Articulo.findByMinimo", query = "SELECT a FROM Articulo a WHERE a.minimo = :minimo"),
    @NamedQuery(name = "Articulo.findByMaximo", query = "SELECT a FROM Articulo a WHERE a.maximo = :maximo"),
    @NamedQuery(name = "Articulo.findByPcosto", query = "SELECT a FROM Articulo a WHERE a.pcosto = :pcosto"),
    @NamedQuery(name = "Articulo.findByPventa", query = "SELECT a FROM Articulo a WHERE a.pventa = :pventa")})
public class Articulo implements Serializable {
    @OneToMany(mappedBy = "codigo")
    private Collection<Detallefactura> detallefacturaCollection;
    @OneToMany(mappedBy = "codigo")
    private Collection<Detallecompra> detallecompraCollection;
    @OneToMany(mappedBy = "codigo")
    private Collection<Requerimiento> requerimientoCollection;
    @OneToMany(mappedBy = "codigo")
    private Collection<Despachopicadora> despachopicadoraCollection;
    @OneToMany(mappedBy = "codigo")
    private Collection<Detalleproduccionpicadora> detalleproduccionpicadoraCollection;
    @OneToMany(mappedBy = "codigo")
    private Collection<Inventariopicadora> inventariopicadoraCollection;
    @OneToMany(mappedBy = "codigo")
    private Collection<Movimientoinventariopicadora> movimientoinventariopicadoraCollection;
    @OneToMany(mappedBy = "codigo")
    private Collection<Detallenotacarga> detallenotacargaCollection;

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 14)
    @Column(name = "codigo")
    private String codigo;
    @Size(max = 100)
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "minimo")
    private Integer minimo;
    @Column(name = "maximo")
    private Integer maximo;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @NotNull
    @Column(name = "pcosto")
    private Double pcosto;
    @NotNull
    @Column(name = "pventa")
    private Double pventa;
    @JoinColumn(name = "idgrupo", referencedColumnName = "idgrupo")
    @ManyToOne
    private Grupo idgrupo;
    @JoinColumn(name = "idsubgrupo", referencedColumnName = "idsubgrupo")
    @ManyToOne
    private Subgrupo idsubgrupo;
    @JoinColumn(name = "idgravamen", referencedColumnName = "idgravamen")
    @ManyToOne
    private Gravamen idgravamen;
    @JoinColumn(name = "idusuario", referencedColumnName = "idusuario")
    @ManyToOne
    private Usuario idusuario;
    @JoinColumn(name = "idmedida", referencedColumnName = "idmedida")
    @ManyToOne
    private Medida idmedida;
    @JoinColumn(name = "idplandecuenta", referencedColumnName = "idplandecuenta")
    @ManyToOne
    private Plandecuenta idplandecuenta;
    @JoinColumn(name = "idcuentaventa", referencedColumnName = "idplandecuenta")
    @ManyToOne
    private Plandecuenta idcuentaventa;

    
    public Articulo() {
    }

    public Articulo(String codigo) {
        this.codigo = codigo;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getMinimo() {
        return minimo;
    }

    public void setMinimo(Integer minimo) {
        this.minimo = minimo;
    }

    public Integer getMaximo() {
        return maximo;
    }

    public void setMaximo(Integer maximo) {
        this.maximo = maximo;
    }

    public Double getPcosto() {
        return pcosto;
    }

    public void setPcosto(Double pcosto) {
        this.pcosto = pcosto;
    }

    public Double getPventa() {
        return pventa;
    }

    public void setPventa(Double pventa) {
        this.pventa = pventa;
    }

    public Grupo getIdgrupo() {
        return idgrupo;
    }

    public void setIdgrupo(Grupo idgrupo) {
        this.idgrupo = idgrupo;
    }

    public Subgrupo getIdsubgrupo() {
        return idsubgrupo;
    }

    public void setIdsubgrupo(Subgrupo idsubgrupo) {
        this.idsubgrupo = idsubgrupo;
    }

    public Gravamen getIdgravamen() {
        return idgravamen;
    }

    public void setIdgravamen(Gravamen idgravamen) {
        this.idgravamen = idgravamen;
    }

    public Usuario getIdusuario() {
        return idusuario;
    }

    public void setIdusuario(Usuario idusuario) {
        this.idusuario = idusuario;
    }

    public Medida getIdmedida() {
        return idmedida;
    }

    public void setIdmedida(Medida idmedida) {
        this.idmedida = idmedida;
    }

    public Plandecuenta getIdplandecuenta() {
        return idplandecuenta;
    }

    public void setIdplandecuenta(Plandecuenta idplandecuenta) {
        this.idplandecuenta = idplandecuenta;
    }

    public Plandecuenta getIdcuentaventa() {
        return idcuentaventa;
    }

    public void setIdcuentaventa(Plandecuenta idcuentaventa) {
        this.idcuentaventa = idcuentaventa;
    }

    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigo != null ? codigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Articulo)) {
            return false;
        }
        Articulo other = (Articulo) object;
        if ((this.codigo == null && other.codigo != null) || (this.codigo != null && !this.codigo.equals(other.codigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return descripcion;
    }

    @XmlTransient
    public Collection<Requerimiento> getRequerimientoCollection() {
        return requerimientoCollection;
    }

    public void setRequerimientoCollection(Collection<Requerimiento> requerimientoCollection) {
        this.requerimientoCollection = requerimientoCollection;
    }

    @XmlTransient
    public Collection<Detallecompra> getDetallecompraCollection() {
        return detallecompraCollection;
    }

    public void setDetallecompraCollection(Collection<Detallecompra> detallecompraCollection) {
        this.detallecompraCollection = detallecompraCollection;
    }

    @XmlTransient
    public Collection<Detallefactura> getDetallefacturaCollection() {
        return detallefacturaCollection;
    }

    public void setDetallefacturaCollection(Collection<Detallefactura> detallefacturaCollection) {
        this.detallefacturaCollection = detallefacturaCollection;
    }
    
    @XmlTransient
    public Collection<Despachopicadora> getDespachopicadoraCollection() {
        return despachopicadoraCollection;
    }

    public void setDespachopicadoraCollection(Collection<Despachopicadora> despachopicadoraCollection) {
        this.despachopicadoraCollection = despachopicadoraCollection;
    }
    
    @XmlTransient
    public Collection<Detalleproduccionpicadora> getDetalleproduccionpicadoraCollection() {
        return detalleproduccionpicadoraCollection;
    }

    public void setDetalleproduccionpicadoraCollection(Collection<Detalleproduccionpicadora> detalleproduccionpicadoraCollection) {
        this.detalleproduccionpicadoraCollection = detalleproduccionpicadoraCollection;
    }
    
    @XmlTransient
    public Collection<Inventariopicadora> getInventariopicadoraCollection() {
        return inventariopicadoraCollection;
    }

    public void setInventariopicadoraCollection(Collection<Inventariopicadora> inventariopicadoraCollection) {
        this.inventariopicadoraCollection = inventariopicadoraCollection;
    }
    
    @XmlTransient
    public Collection<Movimientoinventariopicadora> getMovimientoinventariopicadoraCollection() {
        return movimientoinventariopicadoraCollection;
    }

    public void setMOvimientoinventariopicadoraCollection(Collection<Movimientoinventariopicadora> movimientoinventariopicadoraCollection) {
        this.movimientoinventariopicadoraCollection = movimientoinventariopicadoraCollection;
    }
    
    @XmlTransient
    public Collection<Detallenotacarga> getDetallenotacargaCollection() {
        return detallenotacargaCollection;
    }

    public void setDetallenotacargaCollection(Collection<Detallenotacarga> detallenotacargaCollection) {
        this.detallenotacargaCollection = detallenotacargaCollection;
    }
}
