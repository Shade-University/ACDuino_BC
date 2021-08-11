package cz.upce.ACDuino.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "rfid_tag", uniqueConstraints = {
        @UniqueConstraint(name = "tagId_uniq_constraint", columnNames = "tag_id")
})
public class TagEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="tag_id", nullable = false)
    private String tagId;

    @Column(name="owner")
    private String owner;

    @Column(name="tag_first_seen")
    private LocalDateTime tagFirstSeen;

    @Column(name="tag_last_seen")
    private LocalDateTime tagLastSeen;

    @Column(name="tag_seen_count", nullable = false)
    private int tagSeenCount;

    @Column(name="disabled", nullable = false)
    private boolean disabled;


    public TagEntity() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTagId() {
        return tagId;
    }

    public void setTagId(String tagId) {
        this.tagId = tagId;
    }

    public LocalDateTime getTagFirstSeen() {
        return tagFirstSeen;
    }

    public void setTagFirstSeen(LocalDateTime tagFirstSeen) {
        this.tagFirstSeen = tagFirstSeen;
    }

    public LocalDateTime getTagLastSeen() {
        return tagLastSeen;
    }

    public void setTagLastSeen(LocalDateTime tagLastSeen) {
        this.tagLastSeen = tagLastSeen;
    }

    public int getTagSeenCount() {
        return tagSeenCount;
    }

    public void setTagSeenCount(int tagSeenCount) {
        this.tagSeenCount = tagSeenCount;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
}
