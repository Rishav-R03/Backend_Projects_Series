package java.Services;

import java.model.SystemLogger;
import java.util.UUID;

public class BookService {
    private final SystemLogger logger;
    private final UUID instanceId;


    // Dependency on SystemLogger (Injected via Appconfig)
    public BookService(SystemLogger logger){
        this.logger = logger;
        this.instanceId = UUID.randomUUID();
        logger.log("BookService instance created (Singleton ID: " + instanceId.toString());
    }

    public String findBook(String title){
        logger.log("Searching for book: " + title);
        if(title.equals("The Great Gatsby")){
            return "Book found: The Great Gatsby (Available)";
        }
        return "Book not found: " + title;
    }

    public UUID getInstanceId(){
        return instanceId;
    }
}
