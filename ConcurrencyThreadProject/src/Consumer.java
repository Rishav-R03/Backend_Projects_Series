public class Consumer implements Runnable{
    private final SharedResource sharedResource;
    public Consumer(SharedResource sharedResource){
        this.sharedResource = sharedResource;
    }
    @Override
    public void run(){
        try{
            while(true){// consumer runs indefinitely to keep processing
                int item = sharedResource.take(); // calling to take an item from queue.
                Thread.sleep((long)(Math.random()*250));
            }
        }catch (InterruptedException e){
            Thread.currentThread().interrupt();
            System.err.println("Error at Consumer end:" + e.getMessage());
        }
    }
}
