import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.*;
// ===================================================================
// SCHEDULEDEXECUTORSERVICE - DELAYED & PERIODIC TASKS
// ===================================================================

class ScheduledExecutorExample{
    private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm:ss");
    private static void printTime(String message){
        System.out.println("["+ LocalTime.now().format(TIME_FORMAT)+"]" + message);
    }
    // Example 1: Schedule() - Execute after delay"
    public static void scheduleAfterDelay() throws InterruptedException{
        System.out.println("=== Schedule after delay ===");
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(2);
        printTime("Schedule tasks...");
        // schedule task to run after 2 seconds
        scheduler.schedule(()->{
            printTime("Task 1 executed (2 seconds delay) ");
        },2, TimeUnit.SECONDS);

        // Schedule tasks to run after 4 seconds
        scheduler.schedule(()->{
            printTime("Task 2 executed after 4 seconds");
        },4,TimeUnit.SECONDS);

        // Schedule callable that returns value
        ScheduledFuture<String> future = scheduler.schedule(()->{
            printTime("Calculating result...");
            Thread.sleep(1000);
            return "Computation complete";
        },1,TimeUnit.SECONDS);

        try{
            String result = future.get();
            printTime("Got result: "+ result);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
        Thread.sleep(5000);
        scheduler.shutdown();
        System.out.println();
    }
}

public class Main{
    public static void main(String [] args) throws InterruptedException{
    ScheduledExecutorExample see = new ScheduledExecutorExample();
    ScheduledExecutorExample.scheduleAfterDelay();

    }
}