package cz.upce.ACDuino.service;

import cz.upce.ACDuino.entity.ClientEntity;
import cz.upce.ACDuino.entity.TagEntity;
import cz.upce.ACDuino.repository.ClientRepository;
import cz.upce.ACDuino.repository.TagRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ClientService {

    private final ClientRepository repository;

    public ClientService(ClientRepository repository) {
        this.repository = repository;
    }

    public ClientEntity saveEntity(ClientEntity entity)
    {
        return repository.save(entity);
    }

    public void deleteEntity(String tagId)
    {
        repository.deleteByClientIp(tagId);
    }

}
