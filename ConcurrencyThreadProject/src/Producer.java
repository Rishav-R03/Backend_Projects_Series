// this is the producer's job to generate data, and place them into shared queue.
public class Producer implements Runnable{ // runnable is interface to define task.
    private final SharedResource sharedResource;

    public Producer(SharedResource sharedResource){
        this.sharedResource = sharedResource;
    }

    @Override
    public void run() {
        int itemCounter = 0;
        try{
            while (itemCounter < 20){ // limit to 20 items for a controlled test.
                itemCounter++;
                Thread.sleep((long)(Math.random()*100)); // simulate delays in processing data.
                sharedResource.put(itemCounter); // calling resources-blocking method, if queue is full,
                // this line will be paused.
            }
            System.out.println("Producer finished producing 20 items.");
        }catch (InterruptedException e){
        Thread.currentThread().interrupt();
        System.err.println("Producer Interrupted.");
        }
    }
}
