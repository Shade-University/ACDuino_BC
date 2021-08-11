package cz.upce.ACDuino.controller;

import cz.upce.ACDuino.entity.ClientEntity;
import cz.upce.ACDuino.entity.TagEntity;
import cz.upce.ACDuino.enums.ResponseStatus;
import cz.upce.ACDuino.model.OpenRequest;
import cz.upce.ACDuino.model.Response;
import cz.upce.ACDuino.security.encryption.ContentEncryptor;
import cz.upce.ACDuino.service.ClientService;
import cz.upce.ACDuino.service.RestService;
import cz.upce.ACDuino.service.TagService;
import org.springframework.web.bind.annotation.*;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;

@RestController
public class TagController {

    private final TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping("/api/v1/tags")
    public List<TagEntity> getTags() {
        return tagService.getAll();
    }

    @PostMapping("/api/v1/tags")
    public TagEntity saveTag(@RequestBody TagEntity entity)
    {
        return tagService.saveTag(entity);
    }

    @DeleteMapping("/api/v1/tags/{tagId}")
    public void deleteTag(@PathVariable String tagId)
    {
        tagService.deleteTag(tagId);
    }

}
