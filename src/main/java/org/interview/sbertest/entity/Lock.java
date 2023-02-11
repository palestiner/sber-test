package org.interview.sbertest.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Lock {

    @Id
    @Column(name = "NAME_", nullable = false, length = 200)
    private String name;

    @Column(name = "IS_LOCKED", nullable = false)
    private boolean isLocked = false;

    public Lock(String name, boolean isLocked) {
        this.name = name;
        this.isLocked = isLocked;
    }

    public Lock() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isLocked() {
        return isLocked;
    }

    public void setLocked(boolean locked) {
        isLocked = locked;
    }
}
