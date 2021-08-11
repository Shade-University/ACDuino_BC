package cz.upce.ACDuino.entity;

import cz.upce.ACDuino.enums.RequestType;
import cz.upce.ACDuino.enums.ResponseStatus;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "request_history")
public class HistoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Enumerated(EnumType.STRING)
    @Column(name="request", nullable = false)
    private RequestType request;

    @Enumerated(EnumType.STRING)
    @Column(name="response")
    private ResponseStatus response;

    @Column(name="from_endpoint", nullable = false)
    private String from;

    @Column(name="to_endpoint", nullable = false)
    private String to;

    @Column(name="timestamp", nullable = false)
    private LocalDateTime timestamp;

    public HistoryEntity() {

    }

    public HistoryEntity(RequestType request, ResponseStatus response, String from, String to) {
        this.request = request;
        this.response = response;
        this.from = from;
        this.to = to;
        this.timestamp = LocalDateTime.now();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public RequestType getRequest() {
        return request;
    }

    public void setRequest(RequestType request) {
        this.request = request;
    }

    public ResponseStatus getResponse() {
        return response;
    }

    public void setResponse(ResponseStatus response) {
        this.response = response;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
