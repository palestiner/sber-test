package org.interview.sbertest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.interview.sbertest.entity.ItemEntity;
import org.interview.sbertest.entity.Lock;
import org.interview.sbertest.listener.PostPersistItemEntityListener;
import org.interview.sbertest.transactor.EntityManagerTransactor;
import org.interview.sbertest.transactor.Transactor;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class Main {

    private static final Log LOG = LogFactory.getLog(PostPersistItemEntityListener.class);

    private static final String SCHED_NAME = "item_scheduler";

    private static final int period = 1000;

    private static final Main INSTANCE = new Main();

    public static void main(String[] args) {
        LOG.info("Starting...");
        String jdbcUrl = System.getenv("JDBC_URL");
        jdbcUrl = jdbcUrl != null ? jdbcUrl : "jdbc:postgresql://localhost:5432/test";
        Map<String, String> props = new HashMap<>();
        props.put("javax.persistence.jdbc.url", jdbcUrl);
        final EntityManagerFactory emf = Persistence.createEntityManagerFactory("org.interview.sbertest", props);
        final Transactor<EntityManager> transactor = new EntityManagerTransactor();
        AtomicBoolean isLocked = new AtomicBoolean(false);
        ScheduledExecutorService ses = Executors.newScheduledThreadPool(1);
        ScheduledFuture<?> scheduledFuture = ses.scheduleAtFixedRate(() -> {
                    EntityManager entityManager = emf.createEntityManager();
                    try {
                        transactor.runInTransaction(entityManager, () -> {
                            Lock persistentLock = entityManager.find(Lock.class, SCHED_NAME, LockModeType.PESSIMISTIC_WRITE);
                            if (!persistentLock.isLocked()) {
                                isLocked.set(true);
                                persistentLock.setLocked(true);
                                entityManager.persist(persistentLock);
                            }
                        });
                        if (isLocked.get()) {
                            INSTANCE.doOp(emf);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        entityManager.close();
                    }
                },
                1000,
                period,
                TimeUnit.MILLISECONDS);
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            EntityManager entityManager = emf.createEntityManager();
            try {
                transactor.runInTransaction(entityManager, () -> {
                    Lock persistentLock = entityManager.find(Lock.class, SCHED_NAME, LockModeType.PESSIMISTIC_WRITE);
                    persistentLock.setLocked(false);
                    entityManager.persist(persistentLock);
                });
            } finally {
                entityManager.close();
            }
            scheduledFuture.cancel(true);
            emf.close();
        }));
    }

    public void doOp(EntityManagerFactory emf) {
        EntityManager entityManager = emf.createEntityManager();
        try {
            EntityTransaction transaction = entityManager.getTransaction();
            transaction.begin();
            ItemEntity item = new ItemEntity();
            entityManager.persist(item);
            transaction.commit();
        } finally {
            entityManager.close();
        }
    }
}
