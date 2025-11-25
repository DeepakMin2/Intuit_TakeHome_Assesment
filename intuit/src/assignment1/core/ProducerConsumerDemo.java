package assignment1.core;

import java.util.LinkedList;
import java.util.Queue;

public class ProducerConsumerDemo {
	public static void main(String[] args) throws InterruptedException {
		SharedQueue<Integer> sq = new SharedQueue<>(5);

        int[] srcContainer = {1, 2, 3, 4, 5, 6, 7, 8};
        Queue<Integer> destContainer = new LinkedList<>();

        Producer producer = new Producer(sq, srcContainer);
        Consumer consumer = new Consumer(sq, destContainer);

        Thread producerThread = new Thread(producer);
        Thread consumerThread = new Thread(consumer);

        producerThread.start();
        consumerThread.start();

        producerThread.join();     // wait for producer to finish
        Thread.sleep(1500);        // allow consumer to process remaining items
        consumerThread.interrupt(); // stop consumer after a while

        System.out.println("Final Destination Container: " + destContainer);
        System.out.println("Execution Completed.");
	}

}
