package cz.upce.ACDuino.enums;

public enum RequestType {
    REGISTRATION("REGISTRATION", "/registration"),
    UNREGISTRATION("UNREGISTRATION", "/unregistration"),
    OPEN_REQUEST("OPEN_REQUEST", "/api/v1/openRequest"),
    COMMAND_OPEN("COMMAND_OPEN", "/open");

    private final String request;
    private final String url;

    RequestType(String request, String url) {
        this.request = request;
        this.url = url;
    }

    public String getRequest() {
        return request;
    }

    public String getUrl() {
        return url;
    }
}
