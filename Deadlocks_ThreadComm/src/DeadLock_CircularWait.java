import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
/*
DEADLOCK CONDITIONS (All 4 must be true):
1. Mutual Exclusion: Resources cannot be shared
2. Hold and Wait: Thread holds resource while waiting for another
3. No Preemption: Resources cannot be forcibly taken
4. Circular Wait: Circular chain of threads waiting for resources

PREVENTION STRATEGIES:
1. Lock Ordering: Always acquire locks in same order
2. Lock Timeout: Use tryLock() with timeout
3. Deadlock Detection: Monitor and break deadlocks
4. Avoid Nested Locks: Minimize holding multiple locks
5. Use Higher-Level Concurrency Utilities: ExecutorService, etc.
*/

class BankAccount{
    private final int accId;
    private int balance;
    private final Lock lock = new ReentrantLock();
    public BankAccount(int id, int balance){
        this.accId = id;
        this.balance = balance;
    }
    public int getAccId(){
        return accId;
    }

    // Transfer money with deadlock prevention using account id ordering
    public static void transfer(BankAccount from, BankAccount to, int amount) {
        BankAccount first = from.accId < to.accId ? from : to;
        BankAccount second = from.accId < to.accId ? to : from;
        first.lock.lock();
        try {
            System.out.println(Thread.currentThread().getName() +
                    " locked account " + first.accId);

            second.lock.lock();
            try {
                System.out.println(Thread.currentThread().getName() +
                        " locked account " + second.accId);

                // Perform transfer
                if (from.balance >= amount) {
                    from.balance -= amount;
                    to.balance += amount;
                    System.out.println(Thread.currentThread().getName() +
                            " transferred " + amount + " from Account-" +
                            from.accId + " to Account-" + to.accId);
                    System.out.println("Balances: Account-" + from.accId +
                            "=" + from.balance + ", Account-" + to.accId +
                            "=" + to.balance);
                }
            } finally {
                second.lock.unlock();
            }
        } finally {
            first.lock.unlock();
        }
    }
}
public class DeadLock_CircularWait {
    public static void main(String [] args) throws InterruptedException {
        BankAccount acc1 = new BankAccount(1, 1000);
        BankAccount acc2 = new BankAccount(2,1000);
        Thread t1 = new Thread(()-> BankAccount.transfer(acc1,acc2,300),"Thread-1");
        Thread t2 = new Thread(()-> BankAccount.transfer(acc2,acc1,200),"Thread-2");

        System.out.println("\n=== DeadLock Prevention (Ordered Locking)");
        t1.start();
        t2.start();
        try{
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("All transfer completed");
    }
}
