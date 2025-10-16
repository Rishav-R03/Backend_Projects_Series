class SharedResource{
    private final String name;
    public SharedResource(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }
}

public class DeadLockPrevention_LockOrdering {
    private static final SharedResource r1 = new SharedResource("Resource-1");
    private static final SharedResource r2 = new SharedResource("Resource-2");

    // SOLUTION: Always acquire locks in the same order
    public void method1(){
        synchronized (r1){
            System.out.println(Thread.currentThread().getName() + " locked" + r1.getName());
            try{
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            synchronized (r2){
                System.out.println(Thread.currentThread().getName() + " locked " + r2.getName());
                System.out.println(Thread.currentThread().getName() + " completed method 1" );
            }
        }
    }

    public void method2(){synchronized (r1){
        System.out.println(Thread.currentThread().getName() + " locked " + r1.getName());
        try{Thread.sleep(100);} catch (RuntimeException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        synchronized (r2){
            System.out.println(Thread.currentThread().getName() + " locked " + r2.getName());
            System.out.println(Thread.currentThread().getName() + " method 2");
        }
    }}

    public static void main(String [] args){
        DeadLockPrevention_LockOrdering de = new DeadLockPrevention_LockOrdering();
        Thread t1 = new Thread(()-> de.method1(),"Thread-1");
        Thread t2 = new Thread(()-> de.method2(),"Thread-2");

        System.out.println("\n=== Deadlock prevention (Lock ordering)");
        t1.start();
        t2.start();
        try{
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Both threads completed successfully!");

    }
}
