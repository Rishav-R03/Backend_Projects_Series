// ================================================================
// Basic Executor Example: EXECUTOR INTERFACE - Basic Task Execution
// ================================================================

import java.util.concurrent.Executor;

class BasicExecutorExample{
    public void demonstrate(){
        System.out.println("=== Basic Executor Interface ===");
        // Simple Executor Implementation
        Executor executor = new Executor(){
            @Override
            public void execute(Runnable command){
                // This just runs task in a new thread
                new Thread(command).start();
            }
        };
        // Execute tasks
        executor.execute(()->{
            System.out.println(Thread.currentThread().getName()+ " executing Task 1");
        });

        executor.execute(()->{
            System.out.println(Thread.currentThread().getName() + " executing Task 2");
        });

        System.out.println("Task submitted to basic executor");

        try{
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}


public class Example1 {
    static void main(String [] args){
        BasicExecutorExample bse = new BasicExecutorExample();
        bse.demonstrate();
    }
}
