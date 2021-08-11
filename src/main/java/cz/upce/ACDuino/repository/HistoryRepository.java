package cz.upce.ACDuino.repository;

import cz.upce.ACDuino.entity.ClientEntity;
import cz.upce.ACDuino.entity.HistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface HistoryRepository extends JpaRepository<HistoryEntity,Integer> {

}
