import java.util.concurrent.atomic.AtomicInteger;

class Count extends Thread {
    AtomicInteger count;
    public Count(){
        count = new AtomicInteger();
    }
    public void incrementCount(){
        int maxi = 10000;
        for(int i = 0;i<maxi;i++){
            count.addAndGet(1);
        }
    }
    public AtomicInteger getCount(){
        return count;
    }
}
public class SafeCount {
    public static void main(String [] args) throws InterruptedException{
        Count c = new Count();
        Thread first = new Thread(()-> c.incrementCount(),"thread-1");
        Thread second = new Thread(()-> c.incrementCount(),"thread-2");
        Thread third = new Thread(()-> c.incrementCount(),"thread-3");

        first.start();
        second.start();
        third.start();

        first.join();
        second.join();
        third.join();
        
        System.out.println(c.count);
    }
}
