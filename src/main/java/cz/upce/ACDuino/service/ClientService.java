package cz.upce.ACDuino.service;

import cz.upce.ACDuino.entity.ClientEntity;
import cz.upce.ACDuino.entity.TagEntity;
import cz.upce.ACDuino.repository.ClientRepository;
import cz.upce.ACDuino.repository.TagRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ClientService {

    private final ClientRepository repository;

    public ClientService(ClientRepository repository) {
        this.repository = repository;
    }

    public List<ClientEntity> getAll() {
        return repository.findAll();
    }

    public ClientEntity saveClient(ClientEntity client)
    {
        return repository.save(client);
    }

    public void deleteClient(String clientIp)
    {
        repository.deleteByClientIp(clientIp);
    }

}
