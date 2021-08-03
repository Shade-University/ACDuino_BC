package cz.upce.ACDuino.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Arrays;

public enum ResponseStatus {
    OK("OK"),
    ERROR("ERROR"),
    HOST_UNREACHABLE("HOST_UNREACHABLE");

    private final String status;

    ResponseStatus(String status) {
        this.status = status;
    }

    @JsonCreator
    public static ResponseStatus forValue(@JsonProperty("status") String status)
    {
        return Arrays.stream(ResponseStatus.values())
                .filter(value -> value.status.equals(status))
                .findFirst()
                .orElse(null);
    }
}