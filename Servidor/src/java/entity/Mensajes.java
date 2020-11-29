/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Chechu
 */
@Entity
@Table(name = "MENSAJES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Mensajes.findAll", query = "SELECT m FROM Mensajes m")
    , @NamedQuery(name = "Mensajes.findById", query = "SELECT m FROM Mensajes m WHERE m.id = :id")
    , @NamedQuery(name = "Mensajes.findByFecha", query = "SELECT m FROM Mensajes m WHERE m.fecha = :fecha")
    , @NamedQuery(name = "Mensajes.findByMensaje", query = "SELECT m FROM Mensajes m WHERE m.mensaje = :mensaje")})
public class Mensajes implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FECHA")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "MENSAJE")
    private String mensaje;
    @JoinColumn(name = "RECEPTOR", referencedColumnName = "CORREO")
    @ManyToOne(optional = false)
    private Usuarios receptor;
    @JoinColumn(name = "EMISOR", referencedColumnName = "CORREO")
    @ManyToOne(optional = false)
    private Usuarios emisor;

    public Mensajes() {
    }

    public Mensajes(Integer id) {
        this.id = id;
    }

    public Mensajes(Integer id, Date fecha, String mensaje) {
        this.id = id;
        this.fecha = fecha;
        this.mensaje = mensaje;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public Usuarios getReceptor() {
        return receptor;
    }

    public void setReceptor(Usuarios receptor) {
        this.receptor = receptor;
    }

    public Usuarios getEmisor() {
        return emisor;
    }

    public void setEmisor(Usuarios emisor) {
        this.emisor = emisor;
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
        if (!(object instanceof Mensajes)) {
            return false;
        }
        Mensajes other = (Mensajes) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Mensajes[ id=" + id + " ]";
    }
    
}
