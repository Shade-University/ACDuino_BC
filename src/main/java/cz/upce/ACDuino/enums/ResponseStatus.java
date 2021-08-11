package cz.upce.ACDuino.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Arrays;

public enum ResponseStatus {
    REGISTER_SUCCESSFUL("REGISTER_SUCCESSFUL"),
    ALREADY_REGISTERED("ALREADY_REGISTERED"),
    UREGISTER_SUCCESSFUL("UREGISTER_SUCCESSFUL"),
    ALREADY_UNREGISTERED("ALREADY_UNREGISTERED"),
    JSON_DESERIALIZE_ERROR("JSON_DESERIALIZE_ERROR"),
    UNRECOGNIZED_REQUEST("UNRECOGNIZED_REQUEST"),
    NOT_REGISTERED("NOT_REGISTERED"),
    OK("OK"),
    OPEN_SUCCESS("OPEN_SUCCESS"),
    OPEN_DENIED("OPEN_DENIED");

    private final String status;

    ResponseStatus(String status) {
        this.status = status;
    }

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static ResponseStatus forValue(@JsonProperty("status") String status)
    {
        return Arrays.stream(ResponseStatus.values())
                .filter(value -> value.status.equals(status))
                .findFirst()
                .orElse(null);
    }
}