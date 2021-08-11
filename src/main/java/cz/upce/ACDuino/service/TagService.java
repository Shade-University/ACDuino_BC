package cz.upce.ACDuino.service;

import cz.upce.ACDuino.entity.TagEntity;
import cz.upce.ACDuino.entity.UserEntity;
import cz.upce.ACDuino.repository.TagRepository;
import cz.upce.ACDuino.repository.UserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static java.util.Collections.emptyList;

@Service
public class TagService {

    private final TagRepository repository;

    public TagService(TagRepository repository) {
        this.repository = repository;
    }

    public boolean checkIfExistAndValid(String tagId) {
        TagEntity tag = repository.findByTagId(tagId);
        if(tag != null) {
            tag.setTagLastSeen(LocalDateTime.now());
            if(tag.getTagFirstSeen() == null) {
                tag.setTagFirstSeen(LocalDateTime.now());
            }
            repository.save(tag);
        }
        return tag != null && !tag.isDisabled();
    }

    public List<TagEntity> getAll() {
        return repository.findAll();
    }

    public TagEntity saveTag(TagEntity entity)
    {
        return repository.save(entity);
    }

    public void deleteTag(String tagId)
    {
        repository.deleteByTagId(tagId);
    }

}
