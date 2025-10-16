// understanding need for synchronized

class BankUnsafe{
    private int bankBalance = 1000;

    public void withdrawAmount(int amount){
        if(bankBalance >= amount){
            System.out.println(Thread.currentThread().getName() + " is withdrawing " + amount);
            try{
                Thread.sleep(500);
            }catch(InterruptedException e){
                bankBalance -= amount;
                System.out.println(Thread.currentThread().getName() + " completeed. Balance: " +bankBalance);
            }
        }else{
            System.out.println(Thread.currentThread().getName() + " - Insufficient funds!");
        }
    }
}
/*
Key Points about synchronized:
1. Prevents race conditions by mutual exclusion
2. Uses intrinsic lock (monitor) of the object
3. Synchronized method locks entire method
4. Synchronized block allows finer control
5. Static synchronized locks on Class object
6. Can cause deadlock if not careful
7. Automatic lock release (even on exception)
*/


// Solution 1: To use Synchronized method

class BankSafe{
    private int bankBalance = 1000;
    public synchronized void withdraw(int amount){
        if(bankBalance >= amount) {
            System.out.println(Thread.currentThread().getName() + " is trying to withdraw " + amount);

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                bankBalance -= amount;
                System.out.println(Thread.currentThread().getName() + " completed. Balance: " + bankBalance);
            }
        }else{
            System.out.println(Thread.currentThread().getName() + " - Insufficient funds.");
        }
    }
    public synchronized int getBalance(){
        return bankBalance;
    }
}

// Solution 2: Synchronized block (more fine-grained control)
// using locks
class BankOptimized{
    private int balance = 1000;
    private final Object lock = new Object(); // lock object

    public void withdraw(int amount){
        synchronized (lock){
            if(balance >= amount){
                System.out.println(Thread.currentThread().getName() + " is withdrawing " + amount);
                try{
                    Thread.sleep(500);
                }catch(InterruptedException e){
                    balance -= amount;
                    System.out.println(Thread.currentThread().getName() + " completed. Balance " + balance);
                }
            }else{
                System.out.println(Thread.currentThread().getName() + " insufficient balance. Can't withdraw!");
            }
        }
    }
    public void deposit(int amount){
        synchronized (this){
            balance+=amount;
            System.out.println(Thread.currentThread().getName() + "deposited" + amount + ". Balance "+ balance);
        }
    }
}

class Counter{
    private static int count = 0;
    public static synchronized void increment(){
        count++;
        System.out.println(Thread.currentThread().getName() +": Count = " + count);
    }
    public static synchronized int getCount(){
        return count;
    }
}
public class SynchronizedKeyword {
    public static void main(String [] args) {
        System.out.println("=== Without Synchronization (Race Condition) ===");
        BankUnsafe unsafeBank = new BankUnsafe();
        Thread t1 = new Thread(() -> unsafeBank.withdrawAmount(700), "Thread-1");
        Thread t2 = new Thread(() -> unsafeBank.withdrawAmount(700), "Thread-2");
        Thread t10 = new Thread(() -> unsafeBank.withdrawAmount(700), "Thread-10");
        t1.start();
        t2.start();
        t10.start();
        try{
            Thread.sleep(500);
            t1.join();
            t2.join();
            t10.join();
        }catch(InterruptedException e){
            System.out.println(e.getMessage());
        }


        // With synchronized method
        System.out.println("\n=== With Synchronized Method ===");
        BankSafe bankSafe = new BankSafe();
        Thread t3 = new Thread(() -> bankSafe.withdraw(600), "Thread-3");
        Thread t4 = new Thread(() -> bankSafe.withdraw(700), "Thread-4");
        Thread t11 = new Thread(() -> bankSafe.withdraw(700), "Thread-11");
        t3.start();
        t4.start();
        t11.start();
        try{
            Thread.sleep(500);
            t3.join();
            t4.join();
            t11.join();
        }catch(InterruptedException e){
            System.out.println(e.getMessage());
        }

        // With synchronized block
        System.out.println("\n=== With Synchronized Block ===");
        BankOptimized optimizedBank = new BankOptimized();
        Thread t5 = new Thread(() -> optimizedBank.withdraw(600), "Thread-5");
        Thread t6 = new Thread(() -> optimizedBank.withdraw(500), "Thread-6");
        Thread t7 = new Thread(() -> optimizedBank.withdraw(500), "Thread-7");

        t5.start();
        t6.start();
        t7.start();
        try{
            Thread.sleep(500);
            t5.join();
            t6.join();
            t7.join();
        }catch(InterruptedException e){
            System.out.println(e.getMessage());
        }

        // method 4: Static Synchronized block method
        System.out.println("\n=== Static Synchronized Method ===");
        Thread t8 = new Thread(() -> {
            for (int i = 0; i < 3; i++) Counter.increment();
        }, "Thread-8");

        Thread t9 = new Thread(() -> {
            for (int i = 0; i < 3; i++) Counter.increment();
        }, "Thread-9");
        t8.start();
        t9.start();
    }
}