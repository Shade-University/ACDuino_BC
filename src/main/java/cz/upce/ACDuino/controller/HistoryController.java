package cz.upce.ACDuino.controller;

import cz.upce.ACDuino.entity.ClientEntity;
import cz.upce.ACDuino.entity.HistoryEntity;
import cz.upce.ACDuino.service.ClientService;
import cz.upce.ACDuino.service.HistoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class HistoryController {

    private final HistoryService historyService;

    public HistoryController(HistoryService historyService) {
        this.historyService = historyService;
    }

    @GetMapping("/api/v1/history")
    public List<HistoryEntity> getHistory() {
        return historyService.getAll();
    }
}
