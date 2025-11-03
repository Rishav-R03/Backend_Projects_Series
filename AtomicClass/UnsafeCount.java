class Counter extends Thread{
    int count = 0;

    public void incrementCount(){
        int maxi = 10000;
        for(int i = 0;i<maxi;i++){
            count++;
        }
    }

    public int getCount(){
        return count;
    }
}
public class UnsafeCount {
    public static void main() throws InterruptedException{
        Counter c = new Counter();
        Thread first = new Thread(()-> c.incrementCount(),"thread-1");
        Thread second = new Thread(()-> c.incrementCount(),"thread-2");
        Thread third = new Thread(()-> c.incrementCount(),"thread-3");

        first.start();
        second.start();
        third.start();

        first.join();
        second.join();
        third.join();
        int finalCount = c.getCount();
        System.out.println("Count is: " + finalCount);
    }
}
