/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import data.Ubicacion;
import java.net.URL;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Scanner;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;

/**
 * REST Web Service
 *
 * @author Chechu
 */
@Path("datosabiertos")
public class DatosAbiertos {

    @Context
    private UriInfo context;
    private final static String urlCalidadDelAire = "https://datosabiertos.malaga.eu/recursos/ambiente/calidadaire/calidadaire.json";
    private final static String urlSedesWifi = "https://datosabiertos.malaga.eu/recursos/urbanismoEInfraestructura/sedesWifi/da_sedesWifi-4326.geojson";
    private final static String urlPtosInformacion = "https://datosabiertos.malaga.eu/recursos/urbanismoEInfraestructura/equipamientos/da_puntosInformacion-4326.geojson";
    /**
     * Creates a new instance of DatosAbiertos
     */
    public DatosAbiertos() {
    }


    /**
     * Retrieves representation of an instance of service.GenericResource
     * @return an instance of java.lang.String
     */
    
    @GET
    @Path("wifi/{lat}/{log}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getSedesWifi(@PathParam("lat") double lat, @PathParam("log") double log) {
        permisos();
        String res = null;
        Gson gson = new Gson();
        Ubicacion u = new Ubicacion(lat,log);
        try {
            String out = new Scanner(new URL(urlSedesWifi).openStream(), "UTF-8").useDelimiter("\\A").next();
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
            res = gson.toJson(sedesCercanas.toArray());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }
    
    @GET
    @Path("info/{lat}/{log}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getPtosInformacion(@PathParam("lat") double lat, @PathParam("log") double log) {
        permisos();
        String res = null;
        Gson gson = new Gson();
        Ubicacion u = new Ubicacion(lat,log);
        try {
            String out = new Scanner(new URL(urlPtosInformacion).openStream(), "UTF-8").useDelimiter("\\A").next();
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
        permisos();
        String res = null;
        Gson gson = new Gson();
        Ubicacion u = new Ubicacion(lat,log);
        String out = null;
        try {
            URL url = new URL(urlCalidadDelAire);
            HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
            out = new Scanner(con.getInputStream(), "UTF-8").useDelimiter("\\A").next();
            JsonParser parser = new JsonParser();
            JsonObject rootObj = parser.parseString(out).getAsJsonObject();
            JsonArray zonas = rootObj.getAsJsonArray("features");

            boolean zonaEncontrada = false;
            int i = 0;
            JsonObject zona = null;
            while (!zonaEncontrada && i < zonas.size()) {
                zona = zonas.get(i).getAsJsonObject();
                zonaEncontrada = comprobarZonaVertices(u, zona);
                i++;
            }

            if (zonaEncontrada) {
                res = zona.getAsJsonObject("properties").toString();
            } else {
                res = "{ \"value\" : \"No se ha encontrado la zona en los datos\"";
            }
        } catch (Exception e) {
            res = "{ \"value\" : \""+e.getMessage()+"\"";
        }
        return res;
    }

    
    private boolean comprobarZonaVertices(Ubicacion u, JsonObject o) {
        ArrayList<Ubicacion> vertices = parse(o);
        return u.getLat() <= vertices.get(2).getLat() && u.getLat() >= vertices.get(0).getLat() &&
                u.getLon() <= vertices.get(2).getLon() && u.getLon() >= vertices.get(0).getLon();
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
    
    private static void permisos(){
        try{ TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return null;
            }
            public void checkClientTrusted(X509Certificate[] certs, String authType) {
            }
            public void checkServerTrusted(X509Certificate[] certs, String authType) {
            }
        } };
        // Install the all-trusting trust manager
        final SSLContext sc = SSLContext.getInstance("SSL");
        sc.init(null, trustAllCerts, new java.security.SecureRandom());
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        // Create all-trusting host name verifier
        HostnameVerifier allHostsValid = new HostnameVerifier() {
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        };

        // Install the all-trusting host verifier
        HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
        }catch(Exception e) {
            e.printStackTrace();
        }
    }

}
