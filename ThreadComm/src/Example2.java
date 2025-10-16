class SharedQueue{
    private final int [] buffer = new int[5];
    private int count = 0;

    // producer adds item

    public synchronized void put(int item) throws InterruptedException{
        while(count == buffer.length){
            System.out.println(Thread.currentThread().getName() + " waiting... Queue is full");
            wait();
        }
        // produce data
        buffer[count++] = item;
        System.out.println(Thread.currentThread().getName() +
                " added: " + item + " (count=" + count + ")");

        notifyAll();
    }
    public synchronized int take() throws InterruptedException{
        while(count == 0){
            System.out.println(Thread.currentThread().getName() + " consumer waiting for data");
            wait();
        }
        int item = buffer[--count];
        System.out.println(Thread.currentThread().getName() +
                " removed: " + item + " (count=" + count + ")");
        notifyAll();
        return item;
    }
}
public class Example2 {
    static void main(String [] args){
        // single producer
        SharedQueue sq = new SharedQueue();
        Thread producer = new Thread(()->{
            try{
                for(int i = 1;i<=10;i++){
                    sq.put(i);
                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        },"Producer-1");

        Thread consumer1 = new Thread(()->{
            try{
                for(int i = 1;i<=4;i++){
                    sq.take();
                    Thread.sleep(1500);
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        },"Consumer-1");

        Thread consumer2 = new Thread(()->{
            try{
                for(int i = 1;i<=3;i++){
                    sq.take();
                    Thread.sleep(1500);
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        },"Consumer-2");

        Thread consumer3 = new Thread(()->{
            try{
                for(int i = 1;i<=3;i++){
                    sq.take();
                    Thread.sleep(1500);
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        },"Consumer-3");

        System.out.println("\n=== Multiple-Consumers ===");
        producer.start();
        consumer1.start();
        consumer2.start();
        consumer3.start();
        try{
            producer.join();
            consumer1.join();
            consumer2.join();
            consumer3.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Finished...");
    }
}
