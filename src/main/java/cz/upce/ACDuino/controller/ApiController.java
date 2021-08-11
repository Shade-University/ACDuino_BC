package cz.upce.ACDuino.controller;

import cz.upce.ACDuino.entity.ClientEntity;
import cz.upce.ACDuino.entity.TagEntity;
import cz.upce.ACDuino.enums.ResponseStatus;
import cz.upce.ACDuino.model.OpenRequest;
import cz.upce.ACDuino.model.Response;
import cz.upce.ACDuino.security.encryption.ContentEncryptor;
import cz.upce.ACDuino.service.ClientService;
import cz.upce.ACDuino.service.RestService;
import cz.upce.ACDuino.service.TagService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

@RestController
public class ApiController {

    private final RestService restService;
    private final TagService tagService;
    private final ClientService clientService;
    private final ContentEncryptor encryptor;

    public ApiController(RestService restService, TagService tagService, ClientService clientService, ContentEncryptor encryptor) {
        this.restService = restService;
        this.tagService = tagService;
        this.encryptor = encryptor;
        this.clientService = clientService;
    }

    @GetMapping("/api/v1/registration/{ip}")
    public Response sendRegistrationRequest(@PathVariable String ip) {
        return restService.sendRegistrationRequest(ip);
    }

    @GetMapping("/api/v1/unregistration/{ip}")
    public Response sendUnregistrationRequest(@PathVariable String ip) {
        return restService.sendUnregistrationRequest(ip);
    }

    @GetMapping("/api/v1/{ip}/open")
    public Response sendCommandOpenRequest(@PathVariable String ip) {
        return restService.sendCommandOpenRequest(ip);
    }

    @PostMapping("/api/v1/openRequest")
    public Response openRequest(@RequestBody OpenRequest openRequest) {
        if(tagService.checkIfExistAndValid(openRequest.getRfidTag())) {
            return new Response(ResponseStatus.OPEN_SUCCESS);
        } else {
            return new Response(ResponseStatus.OPEN_DENIED);
        }
    }

    @PostMapping("/api/v1/saveTag")
    public TagEntity saveTag(@RequestBody TagEntity entity)
    {
        return tagService.saveTag(entity);
    }

    @DeleteMapping("/api/v1/deleteTag/{tagId}")
    public void saveTag(@PathVariable String tagId)
    {
        tagService.deleteTag(tagId);
    }

    @PostMapping("/api/v1/saveClient")
    public ClientEntity saveTag(@RequestBody ClientEntity entity)
    {
        return clientService.saveEntity(entity);
    }

    @DeleteMapping("/api/v1/deleteClient/{clientIp}")
    public void saveClient(@PathVariable String clientIp)
    {
        clientService.deleteEntity(clientIp);
    }

    @GetMapping("/test")
    public String decrypt() throws NoSuchPaddingException, InvalidKeyException, NoSuchAlgorithmException, IllegalBlockSizeException, IOException, BadPaddingException, InvalidAlgorithmParameterException, InvalidKeySpecException {
        String test = "test22";
        String encrypted = new String(encryptor.encrypt(test));
        String output = new String(encryptor.decrypt(encrypted));
        return output;
    }

}
