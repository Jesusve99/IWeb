/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Chechu
 */
@Entity
@Table(name = "ETIQUETASPUBLICACIONES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Etiquetaspublicaciones.findAll", query = "SELECT e FROM Etiquetaspublicaciones e")
    , @NamedQuery(name = "Etiquetaspublicaciones.findByIdetiqueta", query = "SELECT e FROM Etiquetaspublicaciones e WHERE e.etiquetaspublicacionesPK.idetiqueta = :idetiqueta")
    , @NamedQuery(name = "Etiquetaspublicaciones.findByIdpublicacion", query = "SELECT e FROM Etiquetaspublicaciones e WHERE e.etiquetaspublicacionesPK.idpublicacion = :idpublicacion")})
public class Etiquetaspublicaciones implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected EtiquetaspublicacionesPK etiquetaspublicacionesPK;

    public Etiquetaspublicaciones() {
    }

    public Etiquetaspublicaciones(EtiquetaspublicacionesPK etiquetaspublicacionesPK) {
        this.etiquetaspublicacionesPK = etiquetaspublicacionesPK;
    }

    public Etiquetaspublicaciones(int idetiqueta, int idpublicacion) {
        this.etiquetaspublicacionesPK = new EtiquetaspublicacionesPK(idetiqueta, idpublicacion);
    }

    public EtiquetaspublicacionesPK getEtiquetaspublicacionesPK() {
        return etiquetaspublicacionesPK;
    }

    public void setEtiquetaspublicacionesPK(EtiquetaspublicacionesPK etiquetaspublicacionesPK) {
        this.etiquetaspublicacionesPK = etiquetaspublicacionesPK;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (etiquetaspublicacionesPK != null ? etiquetaspublicacionesPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Etiquetaspublicaciones)) {
            return false;
        }
        Etiquetaspublicaciones other = (Etiquetaspublicaciones) object;
        if ((this.etiquetaspublicacionesPK == null && other.etiquetaspublicacionesPK != null) || (this.etiquetaspublicacionesPK != null && !this.etiquetaspublicacionesPK.equals(other.etiquetaspublicacionesPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Etiquetaspublicaciones[ etiquetaspublicacionesPK=" + etiquetaspublicacionesPK + " ]";
    }
    
}
