package pl.rupniewski.service_server.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "geo_locations")
public class GeoLocation extends BaseModel {

    @Column(name = "longitude")
    private float longitude;

    @Column(name = "latitude")
    private float latitude;

    public GeoLocation(float longitude, float latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public GeoLocation() {
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    @Override
    public String toString() {
        return "GeoLocation{" +
                "longitude=" + longitude +
                ", latitude=" + latitude +
                '}';
    }
}
