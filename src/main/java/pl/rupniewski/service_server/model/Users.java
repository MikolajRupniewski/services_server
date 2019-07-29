package pl.rupniewski.service_server.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "users")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Users implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        this.password = bCryptPasswordEncoder.encode(password);
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String toJson(){
        return "{\n" +
                "\t\"firstName\": \"" + firstName + "\",\n" +
                "\t\"lastName\": \"" + lastName + "\",\n" +
                "\t\"phoneNumber\": \"" + phoneNumber + "\",\n" +
                "\t\"zipCode\": \"" + zipCode + "\",\n" +
                "\t\"city\": \"" + city + "\",\n" +
                "\t\"streetName\": \"" + streetName + "\",\n" +
                "\t\"houseNumber\": \"" + houseNumber + "\",\n" +
                "\t\"apartmentNumber\": \"" + apartmentNumber + "\",\n" +
                "\t\"username\": \"" + username + "\",\n" +
                "\t\"password\": \"" + password + "\",\n" +
                "\t\"enabled\": " + enabled +
                "\n}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Users)) return false;
        Users users = (Users) o;
        return isEnabled() == users.isEnabled() &&
                Objects.equals(getFirstName(), users.getFirstName()) &&
                Objects.equals(getLastName(), users.getLastName()) &&
                Objects.equals(getPhoneNumber(), users.getPhoneNumber()) &&
                Objects.equals(getZipCode(), users.getZipCode()) &&
                Objects.equals(getCity(), users.getCity()) &&
                Objects.equals(getStreetName(), users.getStreetName()) &&
                Objects.equals(getHouseNumber(), users.getHouseNumber()) &&
                Objects.equals(getApartmentNumber(), users.getApartmentNumber()) &&
                Objects.equals(getUsername(), users.getUsername()) &&
                Objects.equals(getPassword(), users.getPassword());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFirstName(), getLastName(), getPhoneNumber(), getZipCode(), getCity(), getStreetName(), getHouseNumber(), getApartmentNumber(), getUsername(), getPassword(), isEnabled());
    }
}
