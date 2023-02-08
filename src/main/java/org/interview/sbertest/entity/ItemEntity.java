package org.interview.sbertest.entity;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.interview.sbertest.listener.PostPersistItemEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Entity
@EntityListeners(PostPersistItemEntityListener.class)
public class ItemEntity {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(length = 36, columnDefinition = "uuid")
    private UUID id;

    @CreationTimestamp
    private LocalDateTime createdAt;

    public ItemEntity() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Item " + id + " created at " + DateTimeFormatter.ofPattern("HH:mm:ss").format(createdAt) + ".";
    }
}
