package service;

import java.util.concurrent.atomic.AtomicInteger;

public class UserIdService {
    static AtomicInteger idGen = new AtomicInteger();

    protected static Integer genId() {
        return idGen.incrementAndGet();
    }
}
