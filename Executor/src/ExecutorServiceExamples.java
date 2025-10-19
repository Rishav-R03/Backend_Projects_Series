import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

// ================================================================
// ExecutorService - Advanced Thread Pool Management
// ================================================================
class ExecutorServiceExample{
    // Example 1: Single Thread Executor
    public void singleThreadExecutor(){
        System.out.println("=== SINGLE THREAD EXECUTOR ===");
        ExecutorService executor = Executors.newSingleThreadExecutor();
        for(int i = 1;i<=5;i++){
            final int taskId = i;
            executor.execute(()->{
                System.out.println(Thread.currentThread().getName() + " executing Task-" + taskId);
                try{
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
        }

        executor.shutdown();
        System.out.println("Executor shutdown initiated\n");
        try{
            executor.awaitTermination(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    // Example 2: FixedThreadPool
    public void fixedThreadPool(){
        System.out.println("=== Fixed Thread Pool (3 Threads) ===");
        ExecutorService executor = Executors.newFixedThreadPool(3);
            for(int i = 1;i<=10;i++){
                final int taskId = i;
                executor.execute(()->{
                    System.out.println(Thread.currentThread().getName() + " started Task-" + taskId);
                    try{
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                });
            }
        executor.shutdown();
        try{
            executor.awaitTermination(10,TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    // Example 3: Cached Dynamic Sizing
    public void cachedThreadPool(){
        System.out.println("=== Cached Thread Pool ===");
        ExecutorService exeSer = Executors.newCachedThreadPool();
        for(int i = 1;i<=8;i++){
            final int taskId = i;
            exeSer.execute(()->{
                System.out.println(Thread.currentThread().getName() + " executing Task-" + taskId);
                try{
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
        }
        exeSer.shutdown();
        try{
            exeSer.awaitTermination(5,TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println();
    }

    // Example 4: Submit() with Future - Get return values
    public void submitWithFuture(){
        System.out.println("=== Submit With Future (Return Values");
        ExecutorService exeSer = Executors.newFixedThreadPool(3);
        //Submit Callable tasks that return values
        List<Future<Integer>> futuresList = new ArrayList<>();
        for(int i = 1;i<=5;i++){
            final int num = i;
            Future<Integer> future = exeSer.submit(new Callable<Integer>(){
                @Override
                public Integer call() throws Exception{
                    System.out.println(Thread.currentThread().getName() + " calculating square of " + num);
                    Thread.sleep(1000);
                    return num*num;
                }
            });
            futuresList.add(future);
        }

        // get results
        System.out.println("\nRetrieving results:");
        for(int i = 0;i<futuresList.size();i++){
            try{
                Integer result = futuresList.get(i).get();
                System.out.println("Result "+ (i+1) + ": " + result);
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        }
        exeSer.shutdown();
        System.out.println();
    }
}
public class ExecutorServiceExamples {
    static void main(String [] args){
        ExecutorServiceExample exeSer = new ExecutorServiceExample();
        exeSer.singleThreadExecutor();
        exeSer.cachedThreadPool();
    }
}
