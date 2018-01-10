package projetAnnuel;

import java.util.Date;

public class TrackPoint {

    /**
     * Attributs
     */

    // Identifiant du trackPoint
    private int id;
    // Latitude degrés décimaux, WGS84
    private double latitude;
    // Longitude degrés décimaux, WGS84
    private double longitude;
    // Altitude en mètres
    private double elevation;
    // Horodatage
    private Date time;
    // Identifiant du prochain trackPoint
    private int nextTrackPointId;

    /**
     * Constructeur
     */
    public TrackPoint() {
        this.latitude = 0D;
        this.longitude = 0D;
        this.elevation = 0D;
        this.time = new Date();
    }

    /**
     * Constructeur
     *
     * @param latitude (required) Latitude
     * @param longitude (required) Longitude
     * @param elevation (required) Altitude
     * @param time (required) Horodatage
     */
    public TrackPoint(double latitude, double longitude, double elevation, Date time) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.elevation = elevation;
        this.time = time;
    }

    /**
     * Getters
     */
    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getElevation() {
        return elevation;
    }

    public Date getTime() {
        return time;
    }

    /**
     * Setters
     */

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setElevation(double elevation) {
        this.elevation = elevation;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "TrackPoint :\n\tLatitude : " + latitude + "\n\tLongitude : " + longitude + "\n\tAltitude : " + elevation + "\n\tHorodatage : " + time + "\n";
    }
}
