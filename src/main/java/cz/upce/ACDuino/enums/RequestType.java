package cz.upce.ACDuino.enums;

public enum RequestType {
    REGISTRATION("REGISTRATION", "/registration"),
    UNREGISTRATION("UNREGISTRATION", "/unregistration");

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
