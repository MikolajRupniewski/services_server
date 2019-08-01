package pl.rupniewski.service_server.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "authorities")
public class Authorities extends BaseModel {

    @Column(name = "username")
    private String username;
    @Column(name = "authority")
    private String authority;

    public Authorities(String username, String authority) {
        this.username = username;
        this.authority = "ROLE_" + authority;
    }
    public Authorities() {
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getAuthority() {
        return authority;
    }
    public void setAuthority(String authority) {
        this.authority = authority;
    }
}
