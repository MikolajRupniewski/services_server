package pl.rupniewski.service_server.model;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "first_name", nullable = false, length = 64)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 64)
    private String lastName;

    @Column(name = "phone_number", nullable = false, length = 10)
    private String phoneNumber;

    @Column(name = "zip_code", nullable = false, length = 6)
    private String zipCode;

    @Column(name = "city", nullable = false, length = 64)
    private String city;

    @Column(name = "street_name", nullable = false, length = 64)
    private String streetName;

    @Column(name = "house_number", nullable = false, length = 8)
    private String houseNumber;

    @Column(name = "apartment_number", nullable = true, length = 10)
    private String apartmentNumber;

    @Column(name = "username", nullable = false, length = 64, unique = true)
    private String username;

    @Column(name = "password", nullable = false, length = 256)
    private String password;

    @Column(name = "enabled", nullable = false)
    private boolean enabled;
}
