/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.MediaType;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import data.Ubicacion;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;
import javax.ejb.Stateless;
import javax.json.Json;
import javax.net.ssl.HttpsURLConnection;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

/**
 * REST Web Service
 *
 * @author Chechu
 */
@Path("datosbiertos")
public class GenericResource {

    @Context
    private UriInfo context;
    private final String urlCalidadDelAire = "https://datosabiertos.malaga.eu/recursos/ambiente/calidadaire/calidadaire.json";
    private final String urlSedesWifi = "https://datosabiertos.malaga.eu/recursos/urbanismoEInfraestructura/sedesWifi/da_sedesWifi-4326.geojson";
    private final String urlPtosInformacion = "https://datosabiertos.malaga.eu/recursos/urbanismoEInfraestructura/equipamientos/da_puntosInformacion-4326.geojson";
    /**
     * Creates a new instance of GenericResource
     */
    public GenericResource() {
    }

    /**
     * Retrieves representation of an instance of service.GenericResource
     * @return an instance of java.lang.String
     */
    @GET
    @Path("wifi/{lat}/{log}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getSedesWifi(@PathParam("lat") double lat, @PathParam("log") double log) {
        String res = null;
        Gson gson = new Gson();
        Ubicacion u = new Ubicacion(lat,log);
        try {
            Object js = new URL(urlSedesWifi).getContent();
            /*String out = new Scanner(st, "UTF-8").useDelimiter("\\A").next();
            JsonParser parser = new JsonParser();
            JsonObject rootObj = parser.parseString(out).getAsJsonObject();
            JsonArray sedes = rootObj.getAsJsonArray("features");
            
            ArrayList<Ubicacion> sedesCercanas = new ArrayList<>();
            for (JsonElement e : sedes) {
                JsonArray aux = e.getAsJsonObject().getAsJsonObject("geometry").getAsJsonArray("coordinates");
                Ubicacion ubi = new Ubicacion(aux.get(1).getAsDouble(), aux.get(0).getAsDouble());
                if (Math.abs(ubi.getLat() - u.getLat()) <= 0.01 && Math.abs(ubi.getLon() - u.getLon()) <= 0.01) {
                    sedesCercanas.add(ubi);
                }
            }
            res = gson.toJson(sedesCercanas.toArray());*/
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }
    
    @GET
    @Path("info/{lat}/{log}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getPtosInformacion(@PathParam("lat") double lat, @PathParam("log") double log) {
        String res = null;
        Gson gson = new Gson();
        Ubicacion u = new Ubicacion(lat,log);
        try {
            String out = new Scanner(new URL(urlSedesWifi).openStream(), "UTF-8").useDelimiter("\\A").next();
            JsonParser parser = new JsonParser();
            JsonObject rootObj = parser.parseString(out).getAsJsonObject();
            JsonArray ptosInformacion = rootObj.getAsJsonArray("features");
            
            ArrayList<Ubicacion> ptosCercanos = new ArrayList<>();
            for (JsonElement e : ptosInformacion) {
                JsonArray aux = e.getAsJsonObject().getAsJsonObject("geometry").getAsJsonArray("coordinates");
                Ubicacion ubi = new Ubicacion(aux.get(1).getAsDouble(), aux.get(0).getAsDouble());
                if (Math.abs(ubi.getLat() - u.getLat()) <= 0.01 && Math.abs(ubi.getLon() - u.getLon()) <= 0.01) {
                    ptosCercanos.add(ubi);
                }
            }
            res = gson.toJson(ptosCercanos.toArray());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }
    
    @GET
    @Path("calidad/{lat}/{log}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getCalidadAire(@PathParam("lat") double lat, @PathParam("log") double log) {
        String res = null;
        Gson gson = new Gson();
        Ubicacion u = new Ubicacion(lat,log);
        String out = "puta";
        try {
            out = new Scanner(new URL(urlCalidadDelAire).openStream(), "UTF-8").useDelimiter("\\A").next();
            JsonParser parser = new JsonParser();
            JsonObject rootObj = parser.parseString(out).getAsJsonObject();
            JsonArray zonas = rootObj.getAsJsonArray("features");
            
            boolean zonaEncontrada = false;
            int i = 0;
            JsonObject zona = null;
            while (!zonaEncontrada && i < zonas.size()) {
                zona = zonas.get(i).getAsJsonObject();
                zonaEncontrada = comprobarZonaVertices(u, zona.getAsJsonObject("geometry"));
                i++;
            }
            
            if (zonaEncontrada) {
                res = zona.getAsString();
            } else {
                res = "{ \"value\" : \"No se ha encontrado la zona en los datos\"";
            }
        } catch (Exception e) {
            res = "{ \"value\" : \""+out+"\"";
        }
        return res;
    }

    /**
     * PUT method for updating or creating an instance of GenericResource
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void putXml(String content) {
    }
    
    private boolean comprobarZonaVertices(Ubicacion u, JsonObject o) {
            ArrayList<Ubicacion> vertices = parse(o);
            return u.getLat() <= vertices.get(3).getLat() && u.getLat() >= vertices.get(0).getLat() &&
                    u.getLon() <= vertices.get(3).getLon() && u.getLon() >= vertices.get(0).getLon();
    }
    
    private ArrayList<Ubicacion> parse(JsonObject o) {
        ArrayList<Ubicacion> res = new ArrayList<>();
        JsonArray vertices = o.getAsJsonObject("geometry").getAsJsonArray("coordinates").get(0).getAsJsonArray();
        for (JsonElement v : vertices) {
            JsonArray aux = v.getAsJsonArray();
            res.add(new Ubicacion(aux.get(1).getAsDouble(), aux.get(0).getAsDouble()));
        }
        return res;
    }
    
    public void consulta(String x){
        URL servicio = null;
        HttpsURLConnection connection = null;
        int codigo = -2;
        Gson parser = null;
        InputStream in = null;
        try {
            servicio = new URL(x);
            connection = (HttpsURLConnection) servicio.openConnection();
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("User-Agent", "My simple application");
            connection.setRequestMethod("GET");
            //codigo = connection.getResponseCode();
            /*if(codigo<200 || codigo>299) {
                    throw new CodigoException("Error "+codigo);
            }*/
            parser = new Gson();
        //c = parser.fromJson(new InputStreamReader(in), CountResponse.class);
        //codigo = c.count;
        }catch (MalformedURLException e) {
                e.printStackTrace();
        }catch (IOException e) {
                e.printStackTrace();
        }/*catch (CodigoException e) {
                //e.printStackTrace();
        }*/
    }
}
