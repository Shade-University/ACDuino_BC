package cz.upce.ACDuino.controller;

import cz.upce.ACDuino.model.RegistrationResponse;
import cz.upce.ACDuino.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

@RestController
public class RegistrationController {

    private final RegistrationService registrationService;

    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @GetMapping("/registration/{ip}")
    public RegistrationResponse sendRegistrationRequest(@PathVariable String ip) throws IOException {
        return registrationService.sendRegistrationRequest(ip, false);
    }

}
