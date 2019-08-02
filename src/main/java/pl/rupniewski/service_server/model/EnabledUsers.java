package pl.rupniewski.service_server.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "enabled_users")
public class EnabledUsers extends BaseModel{

    @Column(name = "username")
    private String username;

    @Column(name = "uuid")
    private String uuid;

    public EnabledUsers(String username) {
        this.username = username;
        UUID uuid = UUID.randomUUID();
        this.uuid = uuid.toString();
    }

    public EnabledUsers(String username, String uuid) {
        this.username = username;
        this.uuid = uuid;
    }

    public EnabledUsers() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid() {
        UUID uuid = UUID.randomUUID();
        this.uuid = uuid.toString();
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
