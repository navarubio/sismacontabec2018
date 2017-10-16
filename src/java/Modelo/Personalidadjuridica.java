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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Inpeca
 */
@Entity
@Table(name = "personalidadjuridica")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Personalidadjuridica.findAll", query = "SELECT p FROM Personalidadjuridica p"),
    @NamedQuery(name = "Personalidadjuridica.findByIdpersonalidad", query = "SELECT p FROM Personalidadjuridica p WHERE p.idpersonalidad = :idpersonalidad"),
    @NamedQuery(name = "Personalidadjuridica.findByPersonalidad", query = "SELECT p FROM Personalidadjuridica p WHERE p.personalidad = :personalidad")})
public class Personalidadjuridica implements Serializable {
    @OneToMany(mappedBy = "idpersonalidad")
    private Collection<Tiporetencionislr> tiporetencionislrCollection;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idpersonalidad")
    private Integer idpersonalidad;
    @Size(max = 20)
    @Column(name = "personalidad")
    private String personalidad;
    @Size(max = 3)
    @Column(name = "abreviatura")
    private String abreviatura;
    @OneToMany(mappedBy = "idpersonalidad")
    private Collection<Cliente> clienteCollection;
    @OneToMany(mappedBy = "idpersonalidad")
    private Collection<Proveedor> proveedorCollection;
    @OneToMany(mappedBy = "idpersonalidad")
    private Collection<Contribuyente> contribuyenteCollection;
    @OneToMany(mappedBy = "idpersonalidad")
    private Collection<Residenciajuridica> residenciajuridicaCollection;
    @OneToMany(mappedBy = "idpersonalidad")
    private Collection<Empresa> empresaCollection;
    
    public Personalidadjuridica() {
    }

    public Personalidadjuridica(Integer idpersonalidad) {
        this.idpersonalidad = idpersonalidad;
    }

    public Integer getIdpersonalidad() {
        return idpersonalidad;
    }

    public void setIdpersonalidad(Integer idpersonalidad) {
        this.idpersonalidad = idpersonalidad;
    }

    public String getPersonalidad() {
        return personalidad;
    }

    public void setPersonalidad(String personalidad) {
        this.personalidad = personalidad;
    }

    public String getAbreviatura() {
        return abreviatura;
    }

    public void setAbreviatura(String abreviatura) {
        this.abreviatura = abreviatura;
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

    @XmlTransient
    public Collection<Contribuyente> getContribuyenteCollection() {
        return contribuyenteCollection;
    }

    public void setContribuyenteCollection(Collection<Contribuyente> contribuyenteCollection) {
        this.contribuyenteCollection = contribuyenteCollection;
    }
    
    @XmlTransient
    public Collection<Empresa> getEmpresaCollection() {
        return empresaCollection;
    }

    public void setEmpresaCollection(Collection<Empresa> empresaCollection) {
        this.empresaCollection = empresaCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idpersonalidad != null ? idpersonalidad.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Personalidadjuridica)) {
            return false;
        }
        Personalidadjuridica other = (Personalidadjuridica) object;
        if ((this.idpersonalidad == null && other.idpersonalidad != null) || (this.idpersonalidad != null && !this.idpersonalidad.equals(other.idpersonalidad))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return personalidad;
    }

    @XmlTransient
    public Collection<Tiporetencionislr> getTiporetencionislrCollection() {
        return tiporetencionislrCollection;
    }

    public void setTiporetencionislrCollection(Collection<Tiporetencionislr> tiporetencionislrCollection) {
        this.tiporetencionislrCollection = tiporetencionislrCollection;
    }
    
    @XmlTransient
    public Collection<Residenciajuridica> getResidenciajuridicaCollection() {
        return residenciajuridicaCollection;
    }

    public void setResidenciajuridicaCollection(Collection<Residenciajuridica> ResidenciajuridicaCollection) {
        this.residenciajuridicaCollection = ResidenciajuridicaCollection;
    }

    
}
