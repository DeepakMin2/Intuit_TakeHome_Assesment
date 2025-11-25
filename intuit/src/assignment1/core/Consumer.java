package assignment1.core;

import java.util.Queue;

public class Consumer implements Runnable {
	
    private final SharedQueue<Integer> sq; // shared Resource
    private final Queue<Integer> destContainer; // final Result

    public Consumer(SharedQueue<Integer> sharedQueue, Queue<Integer> destinationContainer) {
        this.sq = sharedQueue;
        this.destContainer = destinationContainer;
    }
    
	@Override
	public void run() {
		try {
            while (!Thread.currentThread().isInterrupted()) {
                int item = sq.take();
                destContainer.add(item);
                Thread.sleep(200); // simulate processing time
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
	}

}
