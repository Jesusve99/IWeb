/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
 * @author Chechu
 */
@Entity
@Table(name = "USUARIOS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Usuarios.findAll", query = "SELECT u FROM Usuarios u")
    , @NamedQuery(name = "Usuarios.findByCorreo", query = "SELECT u FROM Usuarios u WHERE u.correo = :correo")
    , @NamedQuery(name = "Usuarios.findByApodo", query = "SELECT u FROM Usuarios u WHERE u.apodo = :apodo")
    , @NamedQuery(name = "Usuarios.findByNombre", query = "SELECT u FROM Usuarios u WHERE u.nombre = :nombre")
    , @NamedQuery(name = "Usuarios.findByFoto", query = "SELECT u FROM Usuarios u WHERE u.foto = :foto")
    , @NamedQuery(name = "Usuarios.findByContrase\u00f1a", query = "SELECT u FROM Usuarios u WHERE u.contrase\u00f1a = :contrase\u00f1a")
    , @NamedQuery(name = "Usuarios.findByUbicacion", query = "SELECT u FROM Usuarios u WHERE u.ubicacion = :ubicacion")})
public class Usuarios implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "CORREO")
    private String correo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "APODO")
    private String apodo;
    @Size(max = 50)
    @Column(name = "NOMBRE")
    private String nombre;
    @Size(max = 50)
    @Column(name = "FOTO")
    private String foto;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "CONTRASE\u00d1A")
    private String contraseña;
    @Size(max = 25)
    @Column(name = "UBICACION")
    private String ubicacion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "autor")
    private Collection<Comentarios> comentariosCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "receptor")
    private Collection<Mensajes> mensajesCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "emisor")
    private Collection<Mensajes> mensajesCollection1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "autor")
    private Collection<Publicaciones> publicacionesCollection;

    public Usuarios() {
    }

    public Usuarios(String correo) {
        this.correo = correo;
    }

    public Usuarios(String correo, String apodo, String contraseña) {
        this.correo = correo;
        this.apodo = apodo;
        this.contraseña = contraseña;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getApodo() {
        return apodo;
    }

    public void setApodo(String apodo) {
        this.apodo = apodo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    @XmlTransient
    public Collection<Comentarios> getComentariosCollection() {
        return comentariosCollection;
    }

    public void setComentariosCollection(Collection<Comentarios> comentariosCollection) {
        this.comentariosCollection = comentariosCollection;
    }

    @XmlTransient
    public Collection<Mensajes> getMensajesCollection() {
        return mensajesCollection;
    }

    public void setMensajesCollection(Collection<Mensajes> mensajesCollection) {
        this.mensajesCollection = mensajesCollection;
    }

    @XmlTransient
    public Collection<Mensajes> getMensajesCollection1() {
        return mensajesCollection1;
    }

    public void setMensajesCollection1(Collection<Mensajes> mensajesCollection1) {
        this.mensajesCollection1 = mensajesCollection1;
    }

    @XmlTransient
    public Collection<Publicaciones> getPublicacionesCollection() {
        return publicacionesCollection;
    }

    public void setPublicacionesCollection(Collection<Publicaciones> publicacionesCollection) {
        this.publicacionesCollection = publicacionesCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (correo != null ? correo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Usuarios)) {
            return false;
        }
        Usuarios other = (Usuarios) object;
        if ((this.correo == null && other.correo != null) || (this.correo != null && !this.correo.equals(other.correo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Usuarios[ correo=" + correo + " ]";
    }
    
}
