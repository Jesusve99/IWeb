/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Chechu
 */
@Entity
@Table(name = "PUBLICACIONES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Publicaciones.findAll", query = "SELECT p FROM Publicaciones p")
    , @NamedQuery(name = "Publicaciones.findById", query = "SELECT p FROM Publicaciones p WHERE p.id = :id")
    , @NamedQuery(name = "Publicaciones.findByDescripcion", query = "SELECT p FROM Publicaciones p WHERE p.descripcion = :descripcion")
    , @NamedQuery(name = "Publicaciones.findByFoto", query = "SELECT p FROM Publicaciones p WHERE p.foto = :foto")
    , @NamedQuery(name = "Publicaciones.findByUbicacion", query = "SELECT p FROM Publicaciones p WHERE p.ubicacion = :ubicacion")
    , @NamedQuery(name = "Publicaciones.findByFecha", query = "SELECT p FROM Publicaciones p WHERE p.fecha = :fecha")})
public class Publicaciones implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Size(max = 500)
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "FOTO")
    private String foto;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 25)
    @Column(name = "UBICACION")
    private String ubicacion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FECHA")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "publicacion")
    private Collection<Comentarios> comentariosCollection;
    @JoinColumn(name = "AUTOR", referencedColumnName = "CORREO")
    @ManyToOne(optional = false)
    private Usuarios autor;

    public Publicaciones() {
    }

    public Publicaciones(Integer id) {
        this.id = id;
    }

    public Publicaciones(Integer id, String foto, String ubicacion, Date fecha) {
        this.id = id;
        this.foto = foto;
        this.ubicacion = ubicacion;
        this.fecha = fecha;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    @XmlTransient
    public Collection<Comentarios> getComentariosCollection() {
        return comentariosCollection;
    }

    public void setComentariosCollection(Collection<Comentarios> comentariosCollection) {
        this.comentariosCollection = comentariosCollection;
    }

    public Usuarios getAutor() {
        return autor;
    }

    public void setAutor(Usuarios autor) {
        this.autor = autor;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Publicaciones)) {
            return false;
        }
        Publicaciones other = (Publicaciones) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Publicaciones[ id=" + id + " ]";
    }
    
}
