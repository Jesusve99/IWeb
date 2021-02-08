/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import data.TokenCache;
import entity.Comentarios;
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
@Path("entity.comentarios")
public class ComentariosFacadeREST extends AbstractFacade<Comentarios> {

    @PersistenceContext(unitName = "ServidorPU")
    private EntityManager em;

    @EJB
    UsuariosFacadeREST uf;
    
    public ComentariosFacadeREST() {
        super(Comentarios.class);
    }

    @POST
    //@Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(@Context HttpHeaders httpHeaders, Comentarios entity) {
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
    public void edit(@Context HttpHeaders httpHeaders, @PathParam("id") Integer id, Comentarios entity) {
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
    public Comentarios find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Comentarios> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Comentarios> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
    public List<String> findAutor(@PathParam("autor") String autor) {
        Query q;
        q = this.getEntityManager().createQuery("Select c.descripcion From Comentarios c Where Upper(c.autor.apodo) Like :autor");
        q.setParameter("autor", "%" +  autor.toUpperCase() + "%");
        
        return q.getResultList();
    }
    
    @GET
    @Path("palabra/{palabra}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<String> findPalabra(@PathParam("palabra") String palabra) {
        Query q;
        q = this.getEntityManager().createQuery("Select c.descripcion,c.publicacion.ubicacion From Comentarios c Where Upper(c.descripcion) Like :palabra");
        q.setParameter("palabra", "%" +  palabra.toUpperCase() + "%");
        
        return q.getResultList();
    }
}
