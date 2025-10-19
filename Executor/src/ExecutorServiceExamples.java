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
    // Example 5: invokeAll - Execute multiple tasks and wait
    public void invokeAllExample() throws InterruptedException {
        System.out.println("=== Invoke All (batch execution) ===");
        ExecutorService exeSer = Executors.newFixedThreadPool(3);
        // Callable lists
        List<Callable<String>> tasks = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            final int taskId = i;
            tasks.add(() -> {
                Thread.sleep(500);
                return "Task-" + taskId + " result by " + Thread.currentThread().getName();
            });
        }

        List<Future<String>> results = exeSer.invokeAll(tasks);
        System.out.println("All tasks completed. Results:");
        for(Future<String> result : results){
            try{
                System.out.println(result.get());
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            }
        }
        // Execute All and wait for completion
        exeSer.shutdown();
        System.out.println();
    }
    // Example 6: InvokeAny - Return first completed task
    public static void invokeAny() throws InterruptedException, ExecutionException {
        System.out.println("=== Invoke Any Example ===");
        ExecutorService executor = Executors.newFixedThreadPool(3);
        List<Callable<String>> tasks = new ArrayList<>();
        tasks.add(()->{
            Thread.sleep(2000);
            return "slow task completed.";
        });
        tasks.add(()->{
            Thread.sleep(500);
            return "Fast task completed";
        });
        tasks.add(()->{
            Thread.sleep(1000);
            return "Intermediate task completed.";
        });
        // Return result of first completed tasks
        String result = executor.invokeAny(tasks);
        System.out.println("First completed: "+ result);
        executor.shutdown();
        System.out.println();
    }
    // Example 7: Graceful shutdown
    public static void shutdownExample(){
        System.out.println("=== Graceful Shutdown ===");
        ExecutorService executor = Executors.newFixedThreadPool(3);
        for(int i = 1;i<=5;i++){
            final int taskId = i;
            executor.execute(()->{
                System.out.println("Task-" + taskId +" completed.");
                try{
                    Thread.sleep(2000);
                    System.out.println("Task-" + taskId);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
        }
        executor.shutdown();
        System.out.println("Shutdown initiated ()no new task accepted");
        try{
            if(!executor.awaitTermination(5,TimeUnit.SECONDS)){
                System.out.println("Timeout! Forcing shutdown...");
                List<Runnable> droppedTasks = executor.shutdownNow();
                System.out.println("Tasks that never started...." + droppedTasks.size());
            }else{
                System.out.println("All tasks completed successfully");
            }
        }catch (InterruptedException e){
            executor.shutdown();
        }
        System.out.println();
    }
    //Example 8: Custom ThreadPoolExecutor
    public static void customThreadPool() {
        System.out.println("=== CUSTOM THREAD POOL EXECUTOR ===");

        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                2,                      // corePoolSize
                4,                      // maximumPoolSize
                60,                     // keepAliveTime
                TimeUnit.SECONDS,       // time unit
                new LinkedBlockingQueue<>(10),  // work queue
                new ThreadPoolExecutor.CallerRunsPolicy()  // rejection policy
        );

        System.out.println("Core pool size: " + executor.getCorePoolSize());
        System.out.println("Max pool size: " + executor.getMaximumPoolSize());

        // Submit tasks
        for (int i = 1; i <= 6; i++) {
            final int taskId = i;
            executor.execute(() -> {
                System.out.println(Thread.currentThread().getName() +
                        " executing Task-" + taskId +
                        " (Active: " + executor.getActiveCount() +
                        ", Pool: " + executor.getPoolSize() + ")");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {}
            });
        }

        executor.shutdown();
        try {
            executor.awaitTermination(10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {}
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
