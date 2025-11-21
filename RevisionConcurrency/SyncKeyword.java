class Counter{
    private int count = 0;

    public void incrementUnsafe(){
        count++; // not atomic
    }

    public synchronized void incrementSafe(){
        count = count +1;
    }
    public int getCount(){
        return count;
    }
}
public class SyncKeyword {
    public static void main(String [] args) throws InterruptedException{
        Counter counter = new Counter();
        Runnable taskUnsafe = () ->{
            for(int i = 0; i<1000;i++){
                counter.incrementUnsafe();
            }
        };

        Runnable taskSafe = () ->{
            for(int i=0;i<1000;i++){
                counter.incrementSafe();
            }
        };

        Thread tu1 = new Thread(taskUnsafe);
        Thread tu2 = new Thread(taskUnsafe);

        tu1.start();
        tu2.start();

        tu1.join();
        tu2.join();

        System.out.println("Unsafe Counter Result: " + counter.getCount());
        
        Counter safeCounter = new Counter();
        Thread ts1 = new Thread(()->{
            for(int i = 0;i<1000;i++) safeCounter.incrementSafe();
        });
        Thread ts2 = new Thread(()->{
            for(int i = 0;i<1000;i++) safeCounter.incrementSafe();
        });

        ts1.start();
        ts2.start();

        ts1.join();
        ts2.join();

        System.out.println("Safe counter" + safeCounter.getCount());
    }
}
