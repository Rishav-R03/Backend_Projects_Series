// classic deadlock scenario

class Resource{
    private final String name;
    public Resource(String name){
        this.name = name;
    }
    public String getName(){
        return name;
    }
}

public class DeadlockExample {
    private static final Resource r1 = new Resource("Resource-1");
    private static final Resource r2 = new Resource("Resource-2");

    public static void  method1(){
        synchronized (r1){
            System.out.println(Thread.currentThread().getName()+ " locked " + r1.getName());
            try{
                Thread.sleep(100);
            }catch(InterruptedException e){
                System.out.println("Thread interrupted.");
            }
            System.out.println(Thread.currentThread().getName() + " is waiting for " + r2.getName());

            synchronized (r2){
                System.out.println(Thread.currentThread().getName() + " locked" + r2.getName());
                System.out.println(Thread.currentThread().getName() + " completed method ");

            }
        }

    }
    // Thread 2 first locks resource 2 opposite to thread 1
    public static void method2() {
        synchronized (r2) {
            System.out.println(Thread.currentThread().getName() + " locks " + r2.getName());
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                System.out.println("Thread interrupted");
            }

            System.out.println(Thread.currentThread().getName() + " is waiting for " + r1.getName());
            synchronized (r1) {
                System.out.println(Thread.currentThread().getName() + " locked " + r1.getName());
                System.out.println(Thread.currentThread().getName() + "completed method2");

            }
        }
    }

    public static void main(String [] args){
//        DeadlockExample de = new DeadlockExample();
        // no object is needed as we have required method in the same class
        Thread t1 = new Thread(DeadlockExample::method1,"Thread-1");
        Thread t2 = new Thread(DeadlockExample::method2,"Thread-2");

        System.out.println("=== Deadlock Scenario ===");
        System.out.println("Starting threads...(This is will hang!)");
        t1.start();
        t2.start();
        System.out.println("You will have to end the program forcefully because it is in deadlock");
    }
}
