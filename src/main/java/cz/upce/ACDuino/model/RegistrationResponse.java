package cz.upce.ACDuino.model;

import cz.upce.ACDuino.enums.RegistrationResponseStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public class RegistrationResponse {
    private String uid;
    private LocalDateTime created;
    private RegistrationResponseStatus status;

    public RegistrationResponse(String uid, LocalDateTime created, RegistrationResponseStatus status) {
        this.uid = uid;
        this.created = created;
        this.status = status;
    }

    public String getUid() {
        return uid;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public RegistrationResponseStatus getStatus() {
        return status;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("RegistrationResponse{");
        sb.append("uid='").append(uid).append('\'');
        sb.append(", created='").append(created.toString()).append('\'');
        sb.append(", status='").append(status).append('\'');
        sb.append("}");
        return sb.toString();
    }
}
