import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class Main {
    private static final int NUM_THREADS = 4; // max 4 file processing concurrently
    private static final String OUTPUT_FILE_NAME = "analysis_report.txt";
    public static void main(String [] args){
        // 1. Setup input file
        File inputDir = new File("input_files");
        if(!inputDir.exists()){
            inputDir.mkdirs(); // create dir
            System.out.println("Created input files directory...");
            return;
        }
        File [] inputFiles = inputDir.listFiles((dir,name)->name.endsWith(".txt"));
        if(inputFiles == null || inputFiles.length == 0){
            System.out.println("No files...");
            return;
        }

        // 2. Concurrency setup
        BlockingQueue<Result> writeQueue = new LinkedBlockingQueue<>();

        // fixed size thread pool for file processing
        ExecutorService processorPool = Executors.newFixedThreadPool(NUM_THREADS);

        // dedicated thread for writer task
        FileReportWriter writerTask = new FileReportWriter(new File(OUTPUT_FILE_NAME),writeQueue);
        Thread writerThread = new Thread(writerTask, "Writer-Thread");
        writerThread.start();
        // 3. Submit all processing tasks
        List<Future<Result>> futures = new ArrayList<>();
        for(File file : inputFiles){
            FileProcessor task = new FileProcessor(file,writeQueue);
            Future<Result> future = processorPool.submit(task);
            futures.add(future);
        }
        // 4. await completion
        System.out.println("\n---All file processing tasks completed...");
        try{
            for(Future<Result> future : futures){
                future.get();
            }
            // 5. shutdown sequence
            // shutdown processor pool
            processorPool.shutdown();
            // wait for all the tasks to finish
            processorPool.awaitTermination(5,TimeUnit.SECONDS);

            writeQueue.put(Result.POISON_PILL);

            writerThread.join();
            System.out.println("\n---Application finished. Results are in " + OUTPUT_FILE_NAME);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Main thread interrupted while waiting.");
        } catch (ExecutionException e) {
            System.err.println("A processing task threw an exception: " + e.getCause().getMessage());
        }
    }
}
