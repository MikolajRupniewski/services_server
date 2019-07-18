package pl.rupniewski.service_server.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "users")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Users implements Serializable {

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

    public Users(String firstName, String lastName, String phoneNumber, String zipCode, String city, String streetName, String houseNumber,String apartmentNumber, String username, String password, boolean enabled) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.zipCode = zipCode;
        this.city = city;
        this.streetName = streetName;
        this.houseNumber = houseNumber;
        this.apartmentNumber = apartmentNumber;
        this.username = username;
        this.password = password;
        this.enabled = enabled;
    }

    public Users(Users users) {
        this.firstName = users.firstName;
        this.lastName = users.lastName;
        this.phoneNumber = users.phoneNumber;
        this.zipCode = users.zipCode;
        this.city = users.city;
        this.streetName = users.streetName;
        this.houseNumber = users.houseNumber;
        this.apartmentNumber = users.apartmentNumber;
        this.username = users.username;
        this.password = users.password;
        this.enabled = users.enabled;
    }
    public Users() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getApartmentNumber() {
        return apartmentNumber;
    }

    public void setApartmentNumber(String apartmentNumber) {
        this.apartmentNumber = apartmentNumber;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
