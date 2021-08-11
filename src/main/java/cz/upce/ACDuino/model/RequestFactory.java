package cz.upce.ACDuino.model;

import cz.upce.ACDuino.enums.RequestType;
import cz.upce.ACDuino.security.encryption.ContentEncryptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Component
public class RequestFactory {

    private final ContentEncryptor encryptor;

    @Value("${server.port}")
    private String serverPort;

    public RequestFactory(ContentEncryptor encryptor) {
        this.encryptor = encryptor;
    }

    public Request getRequest(RequestType type){
        if(type == null) {
            return null;
        }
        else if(type == RequestType.REGISTRATION) {
            return new RegistrationRequest(encryptor.getKey(), serverPort);
        }
        else if (type == RequestType.UNREGISTRATION) {
            return new UnregistrationRequest();
        }
        else if(type == RequestType.COMMAND_OPEN) {
            return new CommandOpenRequest();
        }

        return null;
    }
}
