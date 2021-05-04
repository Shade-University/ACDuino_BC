package cz.upce.ACDuino.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.UUID;

public class RegistrationRequest {

    private String uid;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime created;

    @JsonProperty("secret_key")
    private String secretKey;

    public RegistrationRequest(String secret) {
        uid = UUID.randomUUID().toString();
        created = LocalDateTime.now();
        secretKey = secret;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public String getUid() {
        return uid;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("RegistrationRequest{");
        sb.append("uid='").append(uid).append('\'');
        sb.append(", created='").append(created.toString()).append('\'');
        sb.append(", secretKey='").append(secretKey).append('\'');
        sb.append("}");
        return sb.toString();
    }
}
