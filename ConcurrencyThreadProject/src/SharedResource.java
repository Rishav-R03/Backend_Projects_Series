import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

//STEP 1: Create shared resource

// this class will hold the shared queue for the producer and consumer
public class SharedResource {
    // blocking queue is thread safe and acts as buffer
    // the 10 specifies the  capacity (size limit ) of queue.
    private final BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(10);

    // method for producer to add items
    // put() will block (wait) if queue is full.
    public void put(int item) throws InterruptedException{
        queue.put(item); // blocking call: it will wait until an item is taken out
        System.out.println("Producer produced: " + item  + "| Queue size: "+ queue.size());
    }
    // Method for consumer to take items
    // 'take()' will block(wait) if queue is empty.
    public int take() throws InterruptedException{
        int item = queue.take(); // blocking call: it will wait until another item gets in.
        System.out.println("Consumer consumed: "+ item + "| Queue size: " + queue.size());
        return item;
    }
}
