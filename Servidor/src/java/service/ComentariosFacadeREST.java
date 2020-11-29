/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import entity.Comentarios;
import java.util.List;
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

    public ComentariosFacadeREST() {
        super(Comentarios.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Comentarios entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, Comentarios entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) {
        super.remove(super.find(id));
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
