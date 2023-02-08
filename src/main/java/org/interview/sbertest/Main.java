package org.interview.sbertest;

import org.interview.sbertest.entity.ItemEntity;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

public class Main {

    public static void main(String[] args) {
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
