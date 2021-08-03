package cz.upce.ACDuino.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import cz.upce.ACDuino.enums.RequestType;

import java.time.LocalDateTime;
import java.util.UUID;

public abstract class Request {

    private final String uid;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime created;

    private final RequestType request;

    public Request(RequestType request) {
        uid = UUID.randomUUID().toString();
        created = LocalDateTime.now();
        this.request = request;
    }

    public String getUid() {
        return uid;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public RequestType getRequest() {
        return request;
    }

    @Override
    public String toString() {
        return "Request{" +
                "uid='" + uid + '\'' +
                ", created=" + created +
                ", request=" + request +
                '}';
    }
}
