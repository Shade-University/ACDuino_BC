package cz.upce.ACDuino.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import cz.upce.ACDuino.enums.ResponseStatus;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

public class Response implements Serializable {

    private String uid;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime created;

    private ResponseStatus status;

    public Response(ResponseStatus status) {
        uid = UUID.randomUUID().toString();
        created = LocalDateTime.now();
        this.status = status;
    }

    public Response() {}

    public String getUid() {
        return uid;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public ResponseStatus getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "Response{" +
                "uid='" + uid + '\'' +
                ", created=" + created +
                ", status=" + status +
                '}';
    }
}
