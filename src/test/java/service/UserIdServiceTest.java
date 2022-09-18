package service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.*;

class UserIdServiceTest {
    @Test
    @DisplayName("Test generate unique userid")
    void assertAddRole() throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 10; i++) {
            executor.submit(() -> {
//                System.out.println("thread id is: " + Thread.currentThread().getId());
                UserIdService.genId();
            });
        }

        Thread.sleep(1000L);

        assertEquals(10, UserIdService.idGen.get());
    }
}