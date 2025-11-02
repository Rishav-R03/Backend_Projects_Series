import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class UnsafeCollectionDemo {
    public static void main(String[] args) throws InterruptedException {
        // A non-thread-safe list
        List<Integer> unsafeList = new ArrayList<>();
        int numThreads = 10;
        int operationsPerThread = 2000;
        ExecutorService executor = Executors.newFixedThreadPool(numThreads);

        System.out.println("--- Starting concurrent additions to ArrayList ---");
        for (int i = 0; i < numThreads; i++) {
            executor.submit(() -> {
                for (int j = 0; j < operationsPerThread; j++) {
                    unsafeList.add(j);
                }
            });
        }

        executor.shutdown();
        executor.awaitTermination(5, TimeUnit.SECONDS);

        System.out.println("Expected final size: " + (numThreads * operationsPerThread));
        System.out.println("Actual ArrayList Final Size: " + unsafeList.size());
    }
}