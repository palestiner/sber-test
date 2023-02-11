package org.interview.sbertest.transactor;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public class EntityManagerTransactor implements Transactor<EntityManager> {

    @Override
    public void runInTransaction(EntityManager entityManager, Runnable target) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        target.run();
        transaction.commit();
    }
}
