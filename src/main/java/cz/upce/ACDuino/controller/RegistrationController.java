package cz.upce.ACDuino.controller;

import cz.upce.ACDuino.entity.HistoryEntity;
import cz.upce.ACDuino.enums.RequestType;
import cz.upce.ACDuino.model.Response;
import cz.upce.ACDuino.security.encryption.ContentEncryptor;
import cz.upce.ACDuino.service.HistoryService;
import cz.upce.ACDuino.service.RestService;
import org.springframework.web.bind.annotation.*;

import java.net.InetAddress;
import java.net.UnknownHostException;

@RestController
public class RegistrationController {

    private final RestService restService;
    private final HistoryService historyService;

    public RegistrationController(RestService restService, HistoryService historyService) {
        this.restService = restService;
        this.historyService = historyService;
    }

    @GetMapping("/api/v1/registration/register/{ip}")
    public Response sendRegistrationRequest(@PathVariable String ip) throws UnknownHostException {
        Response response = restService.sendRegistrationRequest(ip);

        HistoryEntity entity = new HistoryEntity(RequestType.REGISTRATION, response.getStatus(),
                InetAddress.getLocalHost().toString(), ip);
        historyService.saveHistoryEntity(entity);

        return response;
    }

    @GetMapping("/api/v1/registration/revoke/{ip}")
    public Response sendRevokeRequest(@PathVariable String ip) throws UnknownHostException {
        Response response = restService.sendRevokeRequest(ip);

        HistoryEntity entity = new HistoryEntity(RequestType.REVOKE, response.getStatus(),
                InetAddress.getLocalHost().toString(), ip);
        historyService.saveHistoryEntity(entity);

        return response;
    }
}
