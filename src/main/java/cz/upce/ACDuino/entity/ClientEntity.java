package cz.upce.ACDuino.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "client", uniqueConstraints = {
        @UniqueConstraint(name = "clientIp_uniq_constraint", columnNames = "client_ip")
})
public class ClientEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="name", nullable = false)
    private String name;

    @Column(name="description")
    private String description;

    @Column(name="client_ip", nullable = false)
    private String clientIp;

    @Column(name="active", nullable = false)
    private boolean active;


    public ClientEntity() {

    }

    public ClientEntity(String name, String description, String clientIp, boolean active) {
        this.name = name;
        this.description = description;
        this.clientIp = clientIp;
        this.active = active;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getClientIp() {
        return clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
