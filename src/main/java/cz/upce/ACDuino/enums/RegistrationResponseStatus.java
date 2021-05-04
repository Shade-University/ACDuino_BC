package cz.upce.ACDuino.enums;

public enum RegistrationResponseStatus {
    HOST_UNREACHABLE("HOST_UNREACHABLE"),
    REGISTRATION_SUCCESSFUL("REGISTRATION_SUCCESSFUL"),
    REGISTRATION_UNSUCCESSFUL("REGISTRATION_UNSUCCESSFUL"),
    NO_RESPONSE("NO_RESPONSE");

    private final String status;

    RegistrationResponseStatus(String status) {
        this.status = status;
    }
}