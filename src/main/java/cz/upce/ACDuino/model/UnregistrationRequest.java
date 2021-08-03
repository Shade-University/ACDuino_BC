package cz.upce.ACDuino.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import cz.upce.ACDuino.enums.RequestType;

public class UnregistrationRequest extends Request {

    public UnregistrationRequest() {
        super(RequestType.UNREGISTRATION);
    }
}
