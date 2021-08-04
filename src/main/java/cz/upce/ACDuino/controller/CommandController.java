package cz.upce.ACDuino.controller;

import cz.upce.ACDuino.model.Response;
import cz.upce.ACDuino.service.RestService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class CommandController {

    private final RestService restService;

    public CommandController(RestService restService) {
        this.restService = restService;
    }

    @GetMapping("/command/{ip}/open")
    public Response sendCommandOpenRequest(@PathVariable String ip) {
        return restService.sendCommandOpenRequest(ip);
    }

}
