package pl.rupniewski.service_server.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


@Entity
@Table(name = "shops")
public class Shop extends BaseModel {

    @ManyToOne( fetch=FetchType.EAGER, cascade ={
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinColumn(name = "category_id")
    @JsonIgnoreProperties("shops")
    private Category category;

    @Column(name = "name")
    private String name;

    @OneToOne(mappedBy = "shop", fetch = FetchType.EAGER,
            cascade = {
                    CascadeType.MERGE,
                    CascadeType.REFRESH,
                    CascadeType.DETACH,
                    CascadeType.REMOVE

            })
    private Users users;
    private Long userId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "geoLocation_id", referencedColumnName = "id")
    private GeoLocation geoLocation;

    @ElementCollection
    @Column(name = "pictures")
    private List<String> pictures = new LinkedList<>();

    @Enumerated(EnumType.STRING)
    @Column(name = "service_place")
    private ServicePlace servicePlace;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="services")
    private List<Service> services;

    private Double maxDistance = 0.0;

    public Shop() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
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

    public List<Service> getServices() {
        return services;
    }

    public void setServices(List<Service> services) {
        this.services = services;
    }

    public Double getMaxDistance() {
        return maxDistance;
    }

    public void setMaxDistance(Double maxDistance) {
        this.maxDistance = maxDistance;
    }

    @Override
    public String toString() {
        return "Shop{" +
                "category=" + category +
                ", users=" + users +
                ", geoLocation=" + geoLocation +
                ", pictures=" + pictures +
                ", servicePlace=" + servicePlace +
                ", services=" + services +
                ", maxDistance=" + maxDistance +
                '}';
    }
}
