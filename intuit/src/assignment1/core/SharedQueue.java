package assignment1.core;

import java.util.LinkedList;
import java.util.Queue;

public class SharedQueue<T> {
	private final Queue<T> queue = new LinkedList<>();
    private final int capacity;

    public SharedQueue(int capacity) {
        this.capacity = capacity;
    }

    public synchronized void put(T item) throws InterruptedException {
        while (queue.size() == capacity) {
        	System.out.println("*****Queue FULL → Producer is WAITING *****");
            wait(); // wait until space is available
        }
        queue.add(item);
        System.out.println("***** Producer added: " + item + " | Current Queue Size: " + queue.size() + " *****");
        
        notifyAll(); // notify consumer waiting threads
    }

    public synchronized T take() throws InterruptedException {
        while (queue.isEmpty()) {
        	System.out.println("***** Queue EMPTY → Consumer is WAITING *****");
            wait(); // wait until item is produced
        }
        T item = queue.poll();
        System.out.println("***** Consumer removed: " + item + " | Current Queue Size: " + queue.size() + " *****");
        
        notifyAll(); // notify producers waiting on queue space
        return item;
    }

    public synchronized int size() {
        return queue.size();
    }

}
