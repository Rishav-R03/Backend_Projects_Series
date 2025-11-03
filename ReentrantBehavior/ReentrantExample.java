import java.util.concurrent.locks.ReentrantLock;

public class ReentrantExample {
    private final ReentrantLock lock = new ReentrantLock();
    // private final ReentrantLock lock = new ReentrantLock(true); // fair lock

    public void methodA() {
        lock.lock();
        try {
            System.out.println("Inside Method A");
            methodB();  // Reentrant lock allows the same thread to enter methodB
        } finally {
            lock.unlock();
        }
    }

    public void methodB() {
        lock.lock();
        try {
            System.out.println("Inside Method B");
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        ReentrantExample example = new ReentrantExample();
        example.methodA();
    }
}