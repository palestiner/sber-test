package org.interview.sbertest.transactor;

public interface Transactor<EM> {

    void runInTransaction(EM em, Runnable target);
}
