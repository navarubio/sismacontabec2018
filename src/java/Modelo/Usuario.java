/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
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
 * @author Inpeca
 */
@Entity
@Table(name = "usuario")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Usuario.findAll", query = "SELECT u FROM Usuario u"),
    @NamedQuery(name = "Usuario.findByIdusuario", query = "SELECT u FROM Usuario u WHERE u.idusuario = :idusuario"),
    @NamedQuery(name = "Usuario.findByNombre", query = "SELECT u FROM Usuario u WHERE u.nombre = :nombre"),
    @NamedQuery(name = "Usuario.findByCedula", query = "SELECT u FROM Usuario u WHERE u.cedula = :cedula"),
    @NamedQuery(name = "Usuario.findByTelefono", query = "SELECT u FROM Usuario u WHERE u.telefono = :telefono"),
    @NamedQuery(name = "Usuario.findByCorreo", query = "SELECT u FROM Usuario u WHERE u.correo = :correo"),
    @NamedQuery(name = "Usuario.findByFechainscripcion", query = "SELECT u FROM Usuario u WHERE u.fechainscripcion = :fechainscripcion"),
    @NamedQuery(name = "Usuario.findByUsuario", query = "SELECT u FROM Usuario u WHERE u.usuario = :usuario"),
    @NamedQuery(name = "Usuario.findByClave", query = "SELECT u FROM Usuario u WHERE u.clave = :clave")})
public class Usuario implements Serializable {
    @OneToMany(mappedBy = "idusuario")
    private Collection<Consumocajachica> consumocajachicaCollection;
    @OneToMany(mappedBy = "idcustodio")
    private Collection<Cajachica> cajachicaCollection;
    @OneToMany(mappedBy = "idrepositor")
    private Collection<Reposicioncajachica> reposicioncajachicaCollection;
    @OneToMany(mappedBy = "idresponsable")
    private Collection<Centrodecosto> centrodecostoCollection;
    @OneToMany(mappedBy = "idusuario")
    private Collection<Otroingreso> otroingresoCollection;
    @OneToMany(mappedBy = "idusuario")
    private Collection<Autorizacion> autorizacionCollection;
    @OneToMany(mappedBy = "idusuario")
    private Collection<Factura> facturaCollection;
    @OneToMany(mappedBy = "idusuario")
    private Collection<Compra> compraCollection;
    @OneToMany(mappedBy = "idusuario")
    private Collection<Conciliacion> conciliacionCollection;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idusuario")
    private Integer idusuario;
    @Size(max = 100)
    @Column(name = "nombre")
    private String nombre;
    @Size(max = 10)
    @Column(name = "cedula")
    private String cedula;
    @Size(max = 50)
    @Column(name = "telefono")
    private String telefono;
    @Size(max = 50)
    @Column(name = "correo")
    private String correo;
    @Column(name = "fechainscripcion")
    @Temporal(TemporalType.DATE)
    private Date fechainscripcion;
    @Size(max = 10)
    @Column(name = "usuario")
    private String usuario;
    @Size(max = 8)
    @Column(name = "clave")
    private String clave;
    @OneToMany(mappedBy = "idusuario")
    private Collection<Auxiliarrequerimiento> auxiliarrequerimientoCollection;
    @OneToMany(mappedBy = "idusuario")
    private Collection<Cliente> clienteCollection;
    @OneToMany(mappedBy = "idusuario")
    private Collection<Proveedor> proveedorCollection;
    @JoinColumn(name = "iddepartamento", referencedColumnName = "iddepartamento")
    @ManyToOne
    private Departamento iddepartamento;
    @OneToMany(mappedBy = "idusuario")
    private Collection<Articulo> articuloCollection;
    @OneToMany(mappedBy = "idusuario")
    private Collection<Produccionpicadora> produccionpicadoraCollection;
    @OneToMany(mappedBy = "idusuario")
    private Collection<Notacarga> notacargaCollection;
    @JoinColumn(name = "idrol", referencedColumnName = "idrol")
    @ManyToOne
    private Rol idrol;

    public Usuario() {
    }

    public Usuario(Integer idusuario) {
        this.idusuario = idusuario;
    }

    public Integer getIdusuario() {
        return idusuario;
    }

    public void setIdusuario(Integer idusuario) {
        this.idusuario = idusuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public Date getFechainscripcion() {
        return fechainscripcion;
    }

    public void setFechainscripcion(Date fechainscripcion) {
        this.fechainscripcion = fechainscripcion;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getClave() {
        return clave;
    }

    public Rol getIdrol() {
        return idrol;
    }

    public void setIdrol(Rol idrol) {
        this.idrol = idrol;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    @XmlTransient
    public Collection<Auxiliarrequerimiento> getAuxiliarrequerimientoCollection() {
        return auxiliarrequerimientoCollection;
    }

    public void setAuxiliarrequerimientoCollection(Collection<Auxiliarrequerimiento> auxiliarrequerimientoCollection) {
        this.auxiliarrequerimientoCollection = auxiliarrequerimientoCollection;
    }

    @XmlTransient
    public Collection<Cliente> getClienteCollection() {
        return clienteCollection;
    }

    public void setClienteCollection(Collection<Cliente> clienteCollection) {
        this.clienteCollection = clienteCollection;
    }

    @XmlTransient
    public Collection<Proveedor> getProveedorCollection() {
        return proveedorCollection;
    }

    public void setProveedorCollection(Collection<Proveedor> proveedorCollection) {
        this.proveedorCollection = proveedorCollection;
    }

    public Departamento getIddepartamento() {
        return iddepartamento;
    }

    public void setIddepartamento(Departamento iddepartamento) {
        this.iddepartamento = iddepartamento;
    }

    @XmlTransient
    public Collection<Articulo> getArticuloCollection() {
        return articuloCollection;
    }

    public void setArticuloCollection(Collection<Articulo> articuloCollection) {
        this.articuloCollection = articuloCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idusuario != null ? idusuario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Usuario)) {
            return false;
        }
        Usuario other = (Usuario) object;
        if ((this.idusuario == null && other.idusuario != null) || (this.idusuario != null && !this.idusuario.equals(other.idusuario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return nombre;
    }

    @XmlTransient
    public Collection<Compra> getCompraCollection() {
        return compraCollection;
    }

    public void setCompraCollection(Collection<Compra> compraCollection) {
        this.compraCollection = compraCollection;
    }

    @XmlTransient
    public Collection<Factura> getFacturaCollection() {
        return facturaCollection;
    }

    public void setFacturaCollection(Collection<Factura> facturaCollection) {
        this.facturaCollection = facturaCollection;
    }

    @XmlTransient
    public Collection<Autorizacion> getAutorizacionCollection() {
        return autorizacionCollection;
    }

    public void setAutorizacionCollection(Collection<Autorizacion> autorizacionCollection) {
        this.autorizacionCollection = autorizacionCollection;
    }

    @XmlTransient
    public Collection<Otroingreso> getOtroingresoCollection() {
        return otroingresoCollection;
    }

    public void setOtroingresoCollection(Collection<Otroingreso> otroingresoCollection) {
        this.otroingresoCollection = otroingresoCollection;
    }
    
    @XmlTransient
    public Collection<Produccionpicadora> getProduccionpicadoraCollection() {
        return produccionpicadoraCollection;
    }

    public void setProduccionpicadoraCollection(Collection<Produccionpicadora> produccionpicadoraCollection) {
        this.produccionpicadoraCollection = produccionpicadoraCollection;
    }
    
    @XmlTransient
    public Collection<Notacarga> getNotacargaCollection() {
        return notacargaCollection;
    }

    public void setNotacargaCollection(Collection<Notacarga> notacargaCollection) {
        this.notacargaCollection = notacargaCollection;
    }

    @XmlTransient
    public Collection<Centrodecosto> getCentrodecostoCollection() {
        return centrodecostoCollection;
    }

    public void setCentrodecostoCollection(Collection<Centrodecosto> centrodecostoCollection) {
        this.centrodecostoCollection = centrodecostoCollection;
    }

    @XmlTransient
    public Collection<Consumocajachica> getConsumocajachicaCollection() {
        return consumocajachicaCollection;
    }

    public void setConsumocajachicaCollection(Collection<Consumocajachica> consumocajachicaCollection) {
        this.consumocajachicaCollection = consumocajachicaCollection;
    }

    @XmlTransient
    public Collection<Cajachica> getCajachicaCollection() {
        return cajachicaCollection;
    }

    public void setCajachicaCollection(Collection<Cajachica> cajachicaCollection) {
        this.cajachicaCollection = cajachicaCollection;
    }

    @XmlTransient
    public Collection<Reposicioncajachica> getReposicioncajachicaCollection() {
        return reposicioncajachicaCollection;
    }

    public void setReposicioncajachicaCollection(Collection<Reposicioncajachica> reposicioncajachicaCollection) {
        this.reposicioncajachicaCollection = reposicioncajachicaCollection;
    }
    
    @XmlTransient
    public Collection<Conciliacion> getConciliacionCollection() {
        return conciliacionCollection;
    }

    public void setConciliacionCollection(Collection<Conciliacion> conciliacionCollection) {
        this.conciliacionCollection = conciliacionCollection;
    }
}
