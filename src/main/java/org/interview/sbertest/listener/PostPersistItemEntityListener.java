package org.interview.sbertest.listener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.persistence.PostPersist;

public class PostPersistItemEntityListener {

    private static final Log LOG = LogFactory.getLog(PostPersistItemEntityListener.class);

    @PostPersist
    public void postPersist(Object o) {
        LOG.info("insert into ItemEntity (id, createdAt) values (?, ?)");
        LOG.info(o);
    }
}
