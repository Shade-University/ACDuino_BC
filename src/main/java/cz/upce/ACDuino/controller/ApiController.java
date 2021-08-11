package cz.upce.ACDuino.controller;

import cz.upce.ACDuino.entity.HistoryEntity;
import cz.upce.ACDuino.enums.RequestType;
import cz.upce.ACDuino.enums.ResponseStatus;
import cz.upce.ACDuino.model.OpenRequest;
import cz.upce.ACDuino.model.Response;
import cz.upce.ACDuino.security.encryption.ContentEncryptor;
import cz.upce.ACDuino.service.ClientService;
import cz.upce.ACDuino.service.HistoryService;
import cz.upce.ACDuino.service.RestService;
import cz.upce.ACDuino.service.TagService;
import org.springframework.web.bind.annotation.*;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

@RestController
public class ApiController {

    private final RestService restService;
    private final TagService tagService;
    private final HistoryService historyService;


    public ApiController(RestService restService, TagService tagService, HistoryService historyService) {
        this.restService = restService;
        this.tagService = tagService;
        this.historyService = historyService;
    }

    @GetMapping("/api/v1/{ip}/open")
    public Response sendCommandOpenRequest(@PathVariable String ip) throws UnknownHostException {
        Response response = restService.sendRequest(ip, RequestType.COMMAND_OPEN);

        HistoryEntity entity = new HistoryEntity(RequestType.COMMAND_OPEN, response.getStatus(),
                InetAddress.getLocalHost().toString(), ip);
        historyService.saveHistoryEntity(entity);

        return response;
    }

    @PostMapping("/api/v1/openRequest")
    public Response openRequest(@RequestBody OpenRequest openRequest,  HttpServletRequest request) throws UnknownHostException {
        Response response;

        if(tagService.checkIfExistAndValid(openRequest.getRfidTag())) {
            response = new Response(ResponseStatus.OPEN_SUCCESS);
        } else {
            response = new Response(ResponseStatus.OPEN_DENIED);
        }

        HistoryEntity entity = new HistoryEntity(RequestType.OPEN_REQUEST, response.getStatus(),
                request.getRemoteAddr(), InetAddress.getLocalHost().toString());
        historyService.saveHistoryEntity(entity);

        return response;
    }

}
