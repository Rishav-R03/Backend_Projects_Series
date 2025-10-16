import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DeadlockPrevention_TryLock {
    private final Lock lock1 = new ReentrantLock();
    private final Lock lock2 = new ReentrantLock();

    public void transferMoney(String from, String to){
        while (true){
            boolean gotLock1 = false;
            boolean gotLock2 = false;

            try{
                gotLock1 = lock1.tryLock(50, TimeUnit.MILLISECONDS);
                gotLock2 = lock2.tryLock(50,TimeUnit.MILLISECONDS);
                if(gotLock1 && gotLock2){
                    System.out.println(Thread.currentThread().getName() + " acquired both locks, transferring from " + from + " to" + to);
                    Thread.sleep(100); // simulate work
                    System.out.println(Thread.currentThread().getName() + " completed transfer");
                    return;
                }else{
                    System.out.println(" could not acquire locks, retrying...");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                if(gotLock1) lock1.unlock();
                if(gotLock2) lock2.unlock();
            }
            // small delay before retrying
            try{
                Thread.sleep(10);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void main(String [] args){
        DeadlockPrevention_TryLock demo = new DeadlockPrevention_TryLock();
        Thread t1 = new Thread(()-> demo.transferMoney("Account-A","Account-B"),"Thread-1");
        Thread t2 = new Thread(()-> demo.transferMoney("Account-B","Account-A"),"Thread-2");

        System.out.println("\n=== DEADLOCK PREVENTION (try-lock)");
        t1.start();
        t2.start();
        try{
            t1.join();
            t1.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Both transfers successful");
    }
}
