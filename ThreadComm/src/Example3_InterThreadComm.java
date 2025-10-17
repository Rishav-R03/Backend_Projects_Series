// ==========================================
// Example 3: Inter-thread Communication - Bank Account Notification
// ==========================================

class BankAccWithNotification{
    private int balance = 0;

    public synchronized void deposit(int amount){
        balance += amount;
        System.out.println(Thread.currentThread().getName() + " deposited " + amount + " Balance: " + balance);
        // Notify waiting threads that balance has increased
        int MIN_BAL = 500;
        if(balance >= MIN_BAL){
            System.out.println("Sufficient balance available! Notifying...");
            notifyAll();
        }
    }
    public synchronized void withdraw(int amount) throws InterruptedException{
        System.out.println(Thread.currentThread().getName() + " wants to withdraw " + amount);
        // Wait until sufficient balance
        while(balance < amount){
            System.out.println(Thread.currentThread().getName() + " waiting for sufficient balance...");
            wait();
        }
        balance-=amount;
        System.out.println(Thread.currentThread().getName() + " withdraw " + amount + ", Balance: " + balance);
    }

    public synchronized int getBalance(){
        return balance;
    }
}

public class Example3_InterThreadComm {
    static void main(String [] args){
        BankAccWithNotification bwn = new BankAccWithNotification();
        // Thread wanting to withdraw (will wait)
        Thread withdrawThread = new Thread(()->{
            try{
                bwn.withdraw(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        },"Withdraw_Thread-1");

        // Deposit threads that will enable withdrawal
        Thread deposit1 = new Thread(()->{
            try{
                Thread.sleep(1000);
                bwn.deposit(300);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        },"DepositThread-1");

        Thread deposit2 = new Thread(()->{
            try{
                Thread.sleep(2000);
                bwn.deposit(400);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        },"DepositThread-2");

        Thread deposit3 = new Thread(()->{
            try{
                Thread.sleep(3000);
                bwn.deposit(400);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        },"DepositThread-3");

        System.out.println("\n=== BANK ACCOUNT NOTIFICATION ===");
        withdrawThread.start();
        deposit1.start();
        deposit2.start();
        deposit3.start();
        try{
            withdrawThread.join();
            deposit1.join();
            deposit2.join();
            deposit3.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
