package assignment1.core;

public class Producer implements Runnable {
	
    private final SharedQueue<Integer> sq; //shared Resource
    private final int[] srcContainer; // original data that will be moved to final Result 

    public Producer(SharedQueue<Integer> sharedQueue, int[] sourceContainer) {
        this.sq = sharedQueue;
        this.srcContainer = sourceContainer;
    }

	@Override
	public void run() {
		try {
            for (int item : srcContainer) {
                sq.put(item);
                Thread.sleep(100); // simulate production time
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
		
	}

}
