package pl.rupniewski.service_server.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "category")
public class Category extends BaseModel {

    @Column(name = "name")
    private String name;
    @ManyToMany(mappedBy = "categories")
    private List<Shop> shops = new ArrayList<>();

    public Category(String name) {
        this.name = name;
    }
    public Category() {
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public List<Shop> getShops() {
        return shops;
    }

    public void setShops(List<Shop> shops) {
        this.shops = shops;
    }
}
