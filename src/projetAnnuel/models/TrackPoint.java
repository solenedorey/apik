package projetAnnuel.models;

import java.util.Date;

public class TrackPoint {

    /**
     * Attributs
     */

    // Latitude degrés décimaux, WGS84
    private double latitude;
    // Longitude degrés décimaux, WGS84
    private double longitude;
    // Altitude en mètres
    private double elevation;
    // Horodatage
    private Date time;
    // Distance avec le prochain point
    private double distanceToNextPoint;
    // Type de points
    private TrackPointCategory trackPointCategory;

    public enum TrackPointCategory {
        LocalMinimum,
        LocalMaximum,
        Uphill,
        Downhill,
        Flat
    }

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

    public double getDistanceToNextPoint() {
        return distanceToNextPoint;
    }

    public TrackPointCategory getTrackPointCategory() {
        return trackPointCategory;
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

    public void setDistanceToNextPoint(double distanceToNextPoint) {
        this.distanceToNextPoint = distanceToNextPoint;
    }

    public void setTrackPointCategory(TrackPointCategory trackPointCategory) {
        this.trackPointCategory = trackPointCategory;
    }

    @Override
    public String toString() {
        return "TrackPoint :" +
                /*"\n\tDistanceToNextTrackPoint : " +
                distanceToNextPoint +
                "\n\tLatitude : " +
                latitude +
                "\n\tLongitude : " +
                longitude +*/
                "\n\tAltitude : " +
                elevation +
                /*"\n\tHorodatage : " +
                time +*/
                "\n\tCatégorie : " +
                trackPointCategory +
                "\n";
    }
}
