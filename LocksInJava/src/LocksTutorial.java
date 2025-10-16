import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LocksTutorial{
    // this class will cause deadlock but java will avoid it through implementation
    // of the ReentrantLock();

    // this works as each lock.lock() is paired with lock.unlock()

    private final Lock lock = new ReentrantLock();

    public void outerMethod(){
        lock.lock();
        try{
            System.out.println("Outer Method");
            innerMethod();
        }finally {
            lock.unlock();
        }
    }
    public void innerMethod() {
        lock.lock();
        try{
            System.out.println("Inner Method");
        }finally{
            lock.unlock();
        }
    }
    public static void main(String [] args){
        LocksTutorial example = new LocksTutorial();
        example.outerMethod();
    }
}