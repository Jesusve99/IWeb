/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import data.Ubicacion;
import java.util.Scanner;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.cert.X509Certificate;
import java.net.URL;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;

/**
 * REST Web Service
 *
 * @author JoseRullva
 */
@Path("logged")
public class LoggedResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of LoggedResource
     */
    public LoggedResource() {
    }

    /**
     * Retrieves representation of an instance of service.LoggedResource
     *
     * @return an instance of java.lang.String
     */
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_PLAIN)
    public String validar(String token/*@Context HttpHeaders httpHeaders*/) {
        //String idtoken = httpHeaders.getRequestHeader("idtoken").get(0);
        String idtoken = token.split("=")[1];
        String urlStr = "https://www.googleapis.com/oauth2/v3/tokeninfo?id_token=" + idtoken;

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

        String res = null;
        Gson gson = new Gson();
        String out = null;
        try {
            URL url = new URL(urlStr);
            HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
            out = new Scanner(con.getInputStream(), "UTF-8").useDelimiter("\\A").next();
            JsonParser parser = new JsonParser();
            JsonObject rootObj = parser.parseString(out).getAsJsonObject();
            
            if (rootObj.get("error_description") != null) {
                res = "ERROR";
            } else {
                res = rootObj.get("name").toString();
            }
                
        } catch (Exception e) {
            res = "ERROR";
        }
        return res;
    }
}
