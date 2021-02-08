/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import data.TokenCache;
import entity.Publicaciones;
import java.sql.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
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

/**
 *
 * @author Chechu
 */
@Stateless
@Path("entity.publicaciones")
public class PublicacionesFacadeREST extends AbstractFacade<Publicaciones> {

    @PersistenceContext(unitName = "ServidorPU")
    private EntityManager em;
    
    @EJB
    UsuariosFacadeREST uf;

    public PublicacionesFacadeREST() {
        super(Publicaciones.class);
    }

    @POST
    //@Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(@Context HttpHeaders httpHeaders, Publicaciones entity) {
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
    public void edit(@Context HttpHeaders httpHeaders, @PathParam("id") Integer id, Publicaciones entity) {
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
    public void remove(@Context HttpHeaders httpHeaders, @PathParam("id") Integer id) {
        if (Integer.parseInt(httpHeaders.getRequestHeader("tipo").get(0)) == 1) {
            if (TokenCache.isInCache(httpHeaders.getRequestHeader("idtoken").get(0))) {
                super.remove(super.find(id));
            }
        } else if (Integer.parseInt(httpHeaders.getRequestHeader("tipo").get(0)) == 0) {
            if (uf.find(httpHeaders.getRequestHeader("email").get(0)) != null) {
                super.remove(super.find(id));
            }
        }
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Publicaciones find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Publicaciones> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Publicaciones> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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

    @GET
    @Path("autor/{autor}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Publicaciones> findAutor(@PathParam("autor") String autor) {
        Query q;
        q = this.getEntityManager().createQuery("Select p From Publicaciones p Where Upper(p.autor.apodo) Like :autor");
        q.setParameter("autor", "%" + autor.toUpperCase() + "%");

        return q.getResultList();
    }

    @GET
    @Path("asc")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Publicaciones> fechaAscendente(@PathParam("fecha") Date fecha) {
        Query q;
        q = this.getEntityManager().createQuery("Select p From Publicaciones p Order By p.fecha ASC ");
        return q.getResultList();
    }

}
