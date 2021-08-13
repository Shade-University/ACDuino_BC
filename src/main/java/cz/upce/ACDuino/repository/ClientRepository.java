package cz.upce.ACDuino.repository;

import cz.upce.ACDuino.entity.ClientEntity;
import cz.upce.ACDuino.entity.TagEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ClientRepository extends JpaRepository<ClientEntity,Integer> {

    ClientEntity findByClientIp(String clientIp); // spring supported keyword findBy__

    @Transactional
    long deleteByClientIp(String clientIp);
}
