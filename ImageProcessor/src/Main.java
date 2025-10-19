import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

class ImageProcessor{
    static class Image{
        String name;
        Image(String name){this.name = name;}
    }

    public static void processImageInParallel(){
        System.out.println("=== Image processing in parallel ===");
        ExecutorService executor = Executors.newFixedThreadPool(3);
        List<String> imageFiles = Arrays.asList("photo1.jpg","photo2.jpg","photo3.jpg","photo4.jpg","photo5.jpg");

        List<Future<String>> result = new ArrayList<>();
        for(String image: imageFiles){
            //Simulate image processing
            Future<String> future = executor.submit(()->{
                System.out.println(Thread.currentThread().getName() + " processing " + image);
                Thread.sleep(1000 + (int)(Math.random()*1000));
                return image + " processed successfully";
            });
            result.add(future);
        }
        System.out.println("\nWaiting for all images to be processed");
        for(Future<String> res : result){
            try{
                System.out.println(res.get());
            } catch (InterruptedException  | RuntimeException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            }
        }
        executor.shutdown();
        System.out.println("All images processed!\n");
    }
}


public class Main{
    public static void main(String [] args){
        ImageProcessor ip = new ImageProcessor();
        ip.processImageInParallel();
    }
}