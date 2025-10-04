import java.io.File;
import java.io.IOException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.concurrent.BlockingQueue;
// Explicit import for clarity (optional)

public class FileReportWriter implements Runnable { // RENAMED CLASS
    private final BlockingQueue<Result> writeQueue;
    private final File outputFile;
    private volatile boolean running = true;

    // Renamed constructor to match the class name
    public FileReportWriter(File outputFile, BlockingQueue<Result> writeQueue){
        this.outputFile = outputFile;
        this.writeQueue = writeQueue;
    }

    public void stopRunning(){
        running = false;
    }

    @Override
    public void run(){
        System.out.println("\n--- Writer Thread Started----");

        // FIX: Now the compiler correctly sees java.io.FileWriter
        try(PrintWriter writer = new PrintWriter(new FileWriter(outputFile))) {

            while(running){
                // take() blocks until a Result is available
                Result result;
                try {
                    result = writeQueue.take();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    System.err.println("Writer thread was interrupted during take().");
                    break;
                }

                if(result == Result.POISON_PILL){
                    System.out.println("Writer received POISON_PILL. Shutting down...");
                    running = false;
                    writer.println(Result.POISON_PILL.toString());
                    break;
                }

                writer.println(result.toString());
                writer.flush(); // Ensure data is written immediately

                // Note: The logging logic is highly dependent on the Result.toString() format.
                // Assuming it works for now.
                String[] parts = result.toString().split(" | ");
                System.out.println("Writer wrote result for: " + (parts.length > 1 ? parts[1] : result.toString()));
            }
        }catch(IOException e){
            System.err.println("Error writing to output file: " + e.getMessage());
        }finally{
            System.out.println("--- Writer Thread finished. ---");
        }
    }
}