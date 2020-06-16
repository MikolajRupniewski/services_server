package pl.rupniewski.service_server.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

// TODO change shop name to something better
@Entity
@Table(name = "shops")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
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


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "geoLocation_id", referencedColumnName = "id")
    private GeoLocation geoLocation;

    @ElementCollection
    @Column(name = "pictures")
    private List<String> pictures = new LinkedList<>();

    @Enumerated(EnumType.STRING)
    @Column(name = "service_place")
    private ServicePlace servicePlace;

    @OneToMany(cascade = {CascadeType.ALL})
    @JoinColumn(name="services")
    private List<Service> services;

    @OneToMany(cascade = {CascadeType.ALL})
    @JoinColumn(name = "employees")
    private List<Employee> employees;

    @OneToMany(cascade = {CascadeType.ALL})
    @JoinColumn(name = "orders")
    private List<Order> orders = new ArrayList<>();

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

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

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    @Override
    public String toString() {
        return "Shop{" +
                "category=" + category +
                ", name='" + name + '\'' +
                ", geoLocation=" + geoLocation +
                ", pictures=" + pictures +
                ", servicePlace=" + servicePlace +
                ", services=" + services +
                ", employees=" + employees +
                ", orders=" + orders +
                ", maxDistance=" + maxDistance +
                '}';
    }
}
