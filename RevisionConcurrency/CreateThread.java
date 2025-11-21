// Thread can be created using two ways
// 1. Extend Thread class
// 2. Implement Runnable

class Task implements Runnable{
    private String name;
    public Task(String name){
        this.name = name;
    }
    @Override
    public void run(){
        try{
            for(int i = 0; i < 3; i++){
                System.out.println(name + " running " + i);
                Thread.sleep(100);
            }
        }catch(InterruptedException e){
            System.out.println(name + " interrupted exception");
        }
        System.out.println(name + " finished.");
    }
}

class WorkerThread extends Thread{
    public WorkerThread(String name){
        super(name);
    }
    @Override 
    public void run(){
        try{
            for(int i = 0;i<3;i++){
                System.out.println(getName() + " working: "+i);
                Thread.sleep(150);
            }
        }catch(InterruptedException e){
            System.out.println(getName() + " interrupted");
        }
        System.out.println(getName()+ " finished");
    }
}
public class CreateThread {
    public static void main(String [] args){
        Thread t1 = new Thread(new Task("Runnable-Thread-1"));
        Thread t2 = new Thread(new Task("Runnable-Thread-2"));
        WorkerThread t3 = new WorkerThread("Worker-Thread-3");

        System.out.println("Starting thread... This is main thread");
        t1.start();
        t2.start();
        t3.start();

        System.out.println("Main thread finished startingg all threads.");
    }
    
}
