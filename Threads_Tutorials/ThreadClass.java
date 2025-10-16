class MyThread extends Thread{
    private String threadName;

    public MyThread(String threadName){
        this.threadName = threadName;
    }
    @Override
    public void run(){
        for(int i = 1;i<=5;i++){
            System.out.println(threadName + ": Count" + i);
            try{
                Thread.sleep(500); // pause of 500 ms
            }catch(InterruptedException e){
                System.out.println(threadName+ "interrupted");
            }
        }
    }
}

class MyRunnable implements Runnable{
    private String taskName;

    public MyRunnable(String threadName){
        this.taskName = taskName;
    }
    @Override
    public void run(){for(int i = 1;i<=5;i++){
        System.out.println(taskName + ": Count " + i);
        try{
            Thread.sleep(500);
        }catch(InterruptedException e){
            System.out.println(taskName + " interrupted");
        }
    }
    }
}

public class ThreadClass{
    public static void main(String [] args){
        System.out.println("=========Method 1==========");
        MyThread t1 = new MyThread("Thread-1");
        MyThread t2 = new MyThread("Thread-1");

        t1.start();
        t2.start();

        System.out.println("\n Thread-1 state: " + t1.getState());
        System.out.println("\n Thread-1 priority: " + t1.getPriority());
        System.out.println("\n Thread-1 Thread-1 alive " + t1.isAlive());

        try{
            t1.join();
            t2.join();
        }catch(InterruptedException e){
            e.printStackTrace();
        }
        System.out.println("\n===Method 2: Implementing Runnable===");
        Thread t3 = new Thread(new MyRunnable("Task-1"));
        Thread t4 = new Thread(new MyRunnable("Task-2"));

        t3.setPriority(Thread.MAX_PRIORITY);
        t3.setPriority(Thread.MIN_PRIORITY);

        t4.start();
        t3.start();

        System.out.println("\n ===Method 3: Using Lambda===");
        Thread t5 = new Thread(()->{
            for(int i = 1;i<=5;i++){
                System.out.println("Lambda-Thread: Count " + i);
                try{
                    Thread.sleep(500);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }
            }
        });
        t5.start();
        System.out.println("\n Main thread continues...");

    }
}