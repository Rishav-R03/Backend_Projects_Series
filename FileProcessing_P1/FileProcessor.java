import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;

public class FileProcessor implements Callable<Result> {
    private final File inputFile;
    private final BlockingQueue<Result> writeQueue;

    public FileProcessor(File inputFile, BlockingQueue<Result> writeQueue){
        this.inputFile = inputFile;
        this.writeQueue = writeQueue;
    }

    @Override
    public Result call(){
        System.out.println(Thread.currentThread().getName() + " started processing: " + inputFile.getName()); // Added filename for clarity
        String longestLine = "";
        Set<String> uniqueWords = new HashSet<>();
        int lineCount = 0;

        // Use try-with-resources for resource management
        try(BufferedReader reader = new BufferedReader(new FileReader(inputFile))){
            String line;
            while((line = reader.readLine()) != null){
                lineCount++;

                // 1. Find longest line
                if(line.length() > longestLine.length()){
                    longestLine = line;
                }

                // 2. Count unique words
                // Split by one or more whitespace characters
                String [] words = line.toLowerCase()
                        .replaceAll("[^a-z0-9\\s]", " ") // Replaced non-alphanumeric with space
                        .split("\\s+");

                for(String word : words){
                    if(!word.isEmpty()){
                        uniqueWords.add(word);
                    }
                }
            }

            // 3. Create result object
            // FIX APPLIED HERE: Use .size() for performance and convention
            Result result = new Result(inputFile.getName(), lineCount, uniqueWords.size(), longestLine);

            // 4. Send result to the shared queue
            writeQueue.put(result);

            System.out.println(Thread.currentThread().getName() + " finished processing: " + inputFile.getName());
            return result;

        }catch (IOException e){
            System.err.println("Error reading file " + inputFile.getName() + ": " + e.getMessage());
            return new Result(inputFile.getName() + " (IO ERROR)", 0, 0, null);
        }catch(InterruptedException e){
            Thread.currentThread().interrupt();
            System.err.println("FileProcessor interrupted: " + inputFile.getName());
            return new Result(inputFile.getName() + " (INTERRUPTED)", 0, 0, null);
        }
    }
}