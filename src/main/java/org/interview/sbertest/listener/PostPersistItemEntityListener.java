package org.interview.sbertest.listener;

import javax.persistence.*;

public class PostPersistItemEntityListener {

    @PostPersist
    public void postPersist(Object o) {
        System.out.println(o);
    }
}
