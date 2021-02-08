/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import data.TokenCache;
import entity.Etiquetaspublicaciones;
import entity.EtiquetaspublicacionesPK;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.PathSegment;

/**
 *
 * @author Chechu
 */
@Stateless
@Path("entity.etiquetaspublicaciones")
public class EtiquetaspublicacionesFacadeREST extends AbstractFacade<Etiquetaspublicaciones> {

    @PersistenceContext(unitName = "ServidorPU")
    private EntityManager em;

    @EJB
    UsuariosFacadeREST uf;
    
    private EtiquetaspublicacionesPK getPrimaryKey(PathSegment pathSegment) {
        /*
         * pathSemgent represents a URI path segment and any associated matrix parameters.
         * URI path part is supposed to be in form of 'somePath;idetiqueta=idetiquetaValue;idpublicacion=idpublicacionValue'.
         * Here 'somePath' is a result of getPath() method invocation and
         * it is ignored in the following code.
         * Matrix parameters are used as field names to build a primary key instance.
         */
        entity.EtiquetaspublicacionesPK key = new entity.EtiquetaspublicacionesPK();
        javax.ws.rs.core.MultivaluedMap<String, String> map = pathSegment.getMatrixParameters();
        java.util.List<String> idetiqueta = map.get("idetiqueta");
        if (idetiqueta != null && !idetiqueta.isEmpty()) {
            key.setIdetiqueta(new java.lang.Integer(idetiqueta.get(0)));
        }
        java.util.List<String> idpublicacion = map.get("idpublicacion");
        if (idpublicacion != null && !idpublicacion.isEmpty()) {
            key.setIdpublicacion(new java.lang.Integer(idpublicacion.get(0)));
        }
        return key;
    }

    public EtiquetaspublicacionesFacadeREST() {
        super(Etiquetaspublicaciones.class);
    }

    @POST
    //@Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(@Context HttpHeaders httpHeaders, Etiquetaspublicaciones entity) {
        if (Integer.parseInt(httpHeaders.getRequestHeader("tipo").get(0)) == 1) {
            if (TokenCache.isInCache(httpHeaders.getRequestHeader("idtoken").get(0))) {
                super.create(entity);
            }
        } else if (Integer.parseInt(httpHeaders.getRequestHeader("tipo").get(0)) == 0) {
            if (uf.find(httpHeaders.getRequestHeader("email").get(0)) != null) {
                super.create(entity);
            }
        }
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@Context HttpHeaders httpHeaders, @PathParam("id") PathSegment id, Etiquetaspublicaciones entity) {
        if (Integer.parseInt(httpHeaders.getRequestHeader("tipo").get(0)) == 1) {
            if (TokenCache.isInCache(httpHeaders.getRequestHeader("idtoken").get(0))) {
                super.edit(entity);
            }
        } else if (Integer.parseInt(httpHeaders.getRequestHeader("tipo").get(0)) == 0) {
            if (uf.find(httpHeaders.getRequestHeader("email").get(0)) != null) {
                super.edit(entity);
            }
        }
    }

    @DELETE
    @Path("{id}")
    public void remove(@Context HttpHeaders httpHeaders, @PathParam("id") PathSegment id) {
        entity.EtiquetaspublicacionesPK key = getPrimaryKey(id);
        if (Integer.parseInt(httpHeaders.getRequestHeader("tipo").get(0)) == 1) {
            if (TokenCache.isInCache(httpHeaders.getRequestHeader("idtoken").get(0))) {
                super.remove(super.find(key));
            }
        } else if (Integer.parseInt(httpHeaders.getRequestHeader("tipo").get(0)) == 0) {
            if (uf.find(httpHeaders.getRequestHeader("email").get(0)) != null) {
                super.remove(super.find(key));
            }
        }
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Etiquetaspublicaciones find(@PathParam("id") PathSegment id) {
        entity.EtiquetaspublicacionesPK key = getPrimaryKey(id);
        return super.find(key);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Etiquetaspublicaciones> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Etiquetaspublicaciones> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}
