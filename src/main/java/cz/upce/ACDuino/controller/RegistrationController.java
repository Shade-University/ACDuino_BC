package cz.upce.ACDuino.controller;

import cz.upce.ACDuino.model.Response;
import cz.upce.ACDuino.security.encryption.ContentEncryptor;
import cz.upce.ACDuino.service.RegistrationService;
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
public class RegistrationController {

    private final RegistrationService registrationService;
    private final ContentEncryptor encryptor;

    public RegistrationController(RegistrationService registrationService, ContentEncryptor encryptor) {
        this.registrationService = registrationService;
        this.encryptor = encryptor;
    }

    @GetMapping("/registration/{ip}")
    public Response sendRegistrationRequest(@PathVariable String ip) throws IOException {
        return registrationService.sendRegistrationRequest(ip);
    }

    @GetMapping("/unregistration/{ip}")
    public Response sendUnregistrationRequest(@PathVariable String ip) throws IOException {
        return registrationService.sendUnregistrationRequest(ip);
    }

    @GetMapping("/test")
    public String decrypt() throws NoSuchPaddingException, InvalidKeyException, NoSuchAlgorithmException, IllegalBlockSizeException, IOException, BadPaddingException, InvalidAlgorithmParameterException, InvalidKeySpecException {
        String test = "test22";
        String encrypted = new String(encryptor.encrypt(test));
        String output = new String(encryptor.decrypt(encrypted));
        return output;
    }

}
