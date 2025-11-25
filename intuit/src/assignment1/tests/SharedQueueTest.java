package assignment1.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import assignment1.core.SharedQueue;

class SharedQueueTest {

	@Test
    void testPutAndTake() throws InterruptedException {
        SharedQueue<Integer> queue = new SharedQueue<>(2);

        queue.put(10);
        queue.put(20);

        assertEquals(10, queue.take());
        assertEquals(20, queue.take());
    }
	
	@Test
    void testQueueBlocksWhenFull() {
        SharedQueue<Integer> queue = new SharedQueue<>(1);

        Thread producerThread = new Thread(() -> {
            try {
                queue.put(1);
                queue.put(2); // this should block until taken
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        producerThread.start();

        assertDoesNotThrow(() -> {
            Thread.sleep(200);
            assertEquals(1, queue.size());
        });

        producerThread.interrupt();
    }
	
	@Test
	void testConsumerBlocksWhenQueueEmpty() throws InterruptedException {
	    SharedQueue<Integer> queue = new SharedQueue<>(2);
	    Thread consumerThread = new Thread(() -> {
	        try {
	            queue.take(); // should block here
	        } catch (InterruptedException e) {
	            Thread.currentThread().interrupt();
	        }
	    });

	    consumerThread.start();
	    Thread.sleep(200); // give consumer time to start and attempt take()
	    assertTrue(consumerThread.isAlive(), "Consumer should be waiting (alive) because queue is empty");
	    consumerThread.interrupt(); // stop test
	}

}
