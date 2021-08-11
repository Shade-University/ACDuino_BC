package cz.upce.ACDuino.service;

import cz.upce.ACDuino.entity.ClientEntity;
import cz.upce.ACDuino.entity.HistoryEntity;
import cz.upce.ACDuino.repository.ClientRepository;
import cz.upce.ACDuino.repository.HistoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HistoryService {

    private final HistoryRepository repository;

    public HistoryService(HistoryRepository repository) {
        this.repository = repository;
    }

    public List<HistoryEntity> getAll() {
        return repository.findAll();
    }

    public HistoryEntity saveHistoryEntity(HistoryEntity entity) {
        return repository.save(entity);
    }

}
