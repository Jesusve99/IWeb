/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Chechu
 */
@Embeddable
public class EtiquetaspublicacionesPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "IDETIQUETA")
    private int idetiqueta;
    @Basic(optional = false)
    @NotNull
    @Column(name = "IDPUBLICACION")
    private int idpublicacion;

    public EtiquetaspublicacionesPK() {
    }

    public EtiquetaspublicacionesPK(int idetiqueta, int idpublicacion) {
        this.idetiqueta = idetiqueta;
        this.idpublicacion = idpublicacion;
    }

    public int getIdetiqueta() {
        return idetiqueta;
    }

    public void setIdetiqueta(int idetiqueta) {
        this.idetiqueta = idetiqueta;
    }

    public int getIdpublicacion() {
        return idpublicacion;
    }

    public void setIdpublicacion(int idpublicacion) {
        this.idpublicacion = idpublicacion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idetiqueta;
        hash += (int) idpublicacion;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EtiquetaspublicacionesPK)) {
            return false;
        }
        EtiquetaspublicacionesPK other = (EtiquetaspublicacionesPK) object;
        if (this.idetiqueta != other.idetiqueta) {
            return false;
        }
        if (this.idpublicacion != other.idpublicacion) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.EtiquetaspublicacionesPK[ idetiqueta=" + idetiqueta + ", idpublicacion=" + idpublicacion + " ]";
    }
    
}
