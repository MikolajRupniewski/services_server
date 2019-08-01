package pl.rupniewski.service_server.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;



@Entity
@Table(name = "services")
public class Shop extends BaseModel {

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "shop_categories")
    private List<Category> categories = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "users_id", referencedColumnName = "id")
    private Users users;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "geoLocation_id", referencedColumnName = "id")
    private GeoLocation geoLocation;

    @ElementCollection
    @Column(name = "pictures")
    private List<String>  pictures = new LinkedList<>();

    @Enumerated(EnumType.STRING)
    @Column(name = "service_place")
    private ServicePlace servicePlace;

    @Column(name = "max_distance")
    private Double maxDistance = 0.0;

    public Shop() {
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public GeoLocation getGeoLocation() {
        return geoLocation;
    }

    public void setGeoLocation(GeoLocation geoLocation) {
        this.geoLocation = geoLocation;
    }

    public List<String> getPictures() {
        return pictures;
    }

    public void setPictures(List<String> pictures) {
        this.pictures = pictures;
    }

    public ServicePlace getServicePlace() {
        return servicePlace;
    }

    public void setServicePlace(ServicePlace servicePlace) {
        this.servicePlace = servicePlace;
    }
}
