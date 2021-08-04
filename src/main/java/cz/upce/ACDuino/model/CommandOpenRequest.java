package cz.upce.ACDuino.model;

import cz.upce.ACDuino.enums.RequestType;

public class CommandOpenRequest extends Request {
    public CommandOpenRequest() {
        super(RequestType.COMMAND_OPEN);
    }
}
