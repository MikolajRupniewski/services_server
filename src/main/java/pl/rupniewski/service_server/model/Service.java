package pl.rupniewski.service_server.model;

import org.springframework.context.annotation.EnableMBeanExport;

import javax.persistence.*;
import java.time.Duration;

@Entity
@Table(name = "service")
public class Service {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "duration")
    private Duration duration;
}
