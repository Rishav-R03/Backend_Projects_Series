// ====================================
// THREAD COMMUNICATION (wait, notify, notifyAll)
// ====================================

// Example 1: Basic wait() and notify() - Producer <=> Consumer model;

class SharedBuffer{
    private int data;
    private boolean hasData = false;

    public synchronized void produce(int value) throws InterruptedException{
        while(hasData){
            System.out.println(Thread.currentThread().getName() + " waiting to produce...buffer is full");
            wait();
        }
        // produce data
        data = value;
        hasData = true;
        System.out.println(Thread.currentThread().getName() + " produced data" + data);
        notify(); // wake up waiting threads
    }

    public synchronized int consume() throws InterruptedException{
        while(!hasData){
            System.out.println(Thread.currentThread().getName() + " waititng to consume data");
            wait();
        }
        // consume
        hasData = false;
        System.out.println(Thread.currentThread().getName() + " consumed data " + data);
        notify();
        return data;
    }
}

public class Example1 {
    public static void main(String [] args){
        SharedBuffer sb = new SharedBuffer();
        Thread producer = new Thread(()->{
            try{
                for(int i = 0;i<=5;i++){
                    sb.produce(i);
                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        },"Producer-1");

        Thread consumer = new Thread(()->{
            try{
                for(int i = 1;i<=5;i++){
                    sb.consume();
                    Thread.sleep(1500);
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        },"Consumer-1");

        System.out.println("=== Producer- Consumer (wait/notify) ===");
        producer.start();
        consumer.start();
        try{
            producer.join();
            consumer.join();
        }catch (InterruptedException e){
            throw new RuntimeException(e);
        }
    }
}
