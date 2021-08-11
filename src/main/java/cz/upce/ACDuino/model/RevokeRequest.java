package cz.upce.ACDuino.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import cz.upce.ACDuino.enums.RequestType;

public class RevokeRequest extends Request {

    public RevokeRequest() {
        super(RequestType.REVOKE);
    }
}
