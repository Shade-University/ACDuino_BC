package cz.upce.ACDuino.repository;

import cz.upce.ACDuino.entity.TagEntity;
import cz.upce.ACDuino.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface TagRepository extends JpaRepository<TagEntity,Integer> {

    TagEntity findByTagId(String tagId); // spring supported keyword findBy__

    @Transactional
    long deleteByTagId(String tagId);
}
