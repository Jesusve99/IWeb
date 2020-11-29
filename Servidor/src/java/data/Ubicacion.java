/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

/**
 *
 * @author Chechu
 */
public class Ubicacion {
    private double lat, lon;
    
    public Ubicacion(double lat, double lon) {
        this.lat = lat;
        this.lon = lon;
    }
    
    public double getLat() { return lat; }
    
    public void setLat(double lat) { this.lat = lat; }
    
    public double getLon() { return lon; }
    
    public void setLon(double lon) { this.lon = lon; }
}
