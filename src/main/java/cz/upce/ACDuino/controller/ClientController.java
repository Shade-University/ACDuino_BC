package cz.upce.ACDuino.controller;

import cz.upce.ACDuino.entity.ClientEntity;
import cz.upce.ACDuino.entity.TagEntity;
import cz.upce.ACDuino.service.ClientService;
import cz.upce.ACDuino.service.TagService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/api/v1/clients")
    public List<ClientEntity> getClients() {
        return clientService.getAll();
    }

    @PostMapping("/api/v1/clients")
    public ClientEntity saveClient(@RequestBody ClientEntity entity)
    {
        return clientService.saveClient(entity);
    }

    @DeleteMapping("/api/v1/clients/{clientIp}")
    public void deleteClient(@PathVariable String clientIp)
    {
        clientService.deleteClient(clientIp);
    }

}
