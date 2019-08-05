package pl.rupniewski.service_server.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "enabled_users")
public class EnabledUsers extends BaseModel {

    @Column(name = "email")
    private String email;

    @Column(name = "uuid")
    private String uuid;

    public EnabledUsers(String email) {
        this.email = email;
        UUID uuid = UUID.randomUUID();
        this.uuid = uuid.toString();
    }

    public EnabledUsers(String email, String uuid) {
        this.email = email;
        this.uuid = uuid;
    }

    public EnabledUsers() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public void setUuid() {
        UUID uuid = UUID.randomUUID();
        this.uuid = uuid.toString();
    }
}
