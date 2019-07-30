package pl.rupniewski.service_server.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;



@Entity
@Table(name = "services")
public class Shop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "service_category",
            joinColumns = @JoinColumn(name = "service_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    private List<Category> tags = new ArrayList<>();

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
    private ServicePlace servicePlace;
}
