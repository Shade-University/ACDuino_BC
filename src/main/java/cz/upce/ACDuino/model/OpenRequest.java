package cz.upce.ACDuino.model;

import cz.upce.ACDuino.enums.RequestType;

public class OpenRequest extends Request {

    private String rfidTag;

    public OpenRequest(String rfidTag) {
        super(RequestType.OPEN_REQUEST);
        this.rfidTag = rfidTag;
    }

    public OpenRequest() { super(RequestType.OPEN_REQUEST); }

    public String getRfidTag() {
        return rfidTag;
    }
}
