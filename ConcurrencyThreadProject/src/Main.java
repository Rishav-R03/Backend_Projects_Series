public class Main {
    public static void main(String [] args) throws InterruptedException{
        // 1.  Create single thread resource instance
        SharedResource sharedResource = new SharedResource();
        // 2. Create tasks
        Runnable producerTask = new Producer(sharedResource);
        Runnable consumerTask = new Consumer(sharedResource);

        // 3. Create actual thread (Multi-thread)
        Thread producerThread1 = new Thread(producerTask, "Producer-1");
        Thread consumerThread1 = new Thread(consumerTask,"Consumer-1");
        Thread producerThread2 = new Thread(producerTask,"Producer-2");
        Thread consumerThread2 = new Thread(consumerTask,"Consumer-2");
        // 4.Start the threads

        System.out.println("Starting producer consumer threads...");

        producerThread1.start();
        producerThread2.start();
        consumerThread1.start();
        consumerThread2.start();

        // wait for producer to finish their 20 items (total 40)
        // .join method forces the main thread to wait until target thread has finished its
        // execution (Thread sync)
        producerThread1.join();
        producerThread2.join();

        // let consumer continue running for a moment to clear queue
        System.out.println("\n---Producer Finished. Waiting for consumer to clear queue...---");
        Thread.sleep(2000);

        // using interrupt we signal the consumers to stop as they were in a infinite loop
        // this is a common way to gracefully stop a thread.
        consumerThread1.interrupt();
        consumerThread2.interrupt();

        System.out.println("\nSystem Shutdown Complete");
    }
}
