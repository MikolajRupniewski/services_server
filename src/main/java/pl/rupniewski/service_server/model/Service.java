package pl.rupniewski.service_server.model;

import javax.persistence.*;
import java.time.Duration;

@Entity
@Table(name = "services")
public class Service extends BaseModel {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "duration")
    private Duration duration;

    @Column(name = "duration_str")
    public String durationStr;

    @Column(name = "price", scale = 2)
    private Double price;

    @ManyToOne(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinColumn(name = "service_category")
    private Category serviceCategory;

    public Service(String name, Duration duration, Double price) {
        this.name = name;
        this.duration = duration;
        this.price = price;
    }

    public Service() {
    }

    public Category getServiceCategory() {
        return serviceCategory;
    }

    public void setServiceCategory(Category serviceCategory) {
        this.serviceCategory = serviceCategory;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }
    public void setDurationFromString() {
        this.duration = Duration.ofMinutes(Long.parseLong(durationStr));
    }
    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Service{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", duration=" + duration.toMinutes() + " minutes" +
                ", price=" + price +
                '}';
    }
}
