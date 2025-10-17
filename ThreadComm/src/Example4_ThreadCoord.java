class TaskCoord{
    private boolean taskCompleted = false;
    public synchronized void completeTask(String taskName){
        System.out.println(Thread.currentThread().getName() + " completing " + taskName + "...");
        try{
            Thread.sleep(2000);
        }catch(InterruptedException e){
            e.printStackTrace();
        }

        taskCompleted = true;
        System.out.println(Thread.currentThread().getName() + " completed " + taskName);
        notifyAll();
    }
    public synchronized void waitForTaskCompletion() throws InterruptedException {
        System.out.println(Thread.currentThread().getName() + 
            " waiting for task to complete...");
        
        while (!taskCompleted) {
            wait();
        }
        
        System.out.println(Thread.currentThread().getName() + 
            " proceeding after task completion");
    }
}

public class Example4_ThreadCoord {
    public static void main(String [] args){
        TaskCoord coord = new TaskCoor();

        Thread worker = new Thread(()->{
            coord.completeTask("Data Processing");
        },"Worker");

        Thread dependent1 = new Thread(()->{
            try{
                
            }
        })
    }
}
    