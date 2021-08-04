package cz.upce.ACDuino.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import cz.upce.ACDuino.enums.RequestType;

import java.time.LocalDateTime;
import java.util.UUID;

public class RegistrationRequest extends Request {

    @JsonProperty("secret_key")
    private final String secretKey;

    public RegistrationRequest(String secretKey) {
        super(RequestType.REGISTRATION);
        this.secretKey = secretKey;
    }
}
