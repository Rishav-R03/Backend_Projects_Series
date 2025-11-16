package java;

import java.Services.BookService;
import java.Services.LibraryService;
import java.Services.MemberService;
import java.Services.TransactionService;
import java.config.AppConfig;
import java.model.SystemLogger;

public class Main {
    public static void main(String [] args){
        System.out.println("--- Dependency Injection Container Simulation Start ---");

        // 1. Setup Java Config
        AppConfig config = new AppConfig();
        SystemLogger logger = config.systemLogger();

        // 2. Setup Singleton Beans (BookService will be the same instance everywhere)
        BookService bookServiceSingleton = config.bookService(logger);
        BookService anotherBookServiceRef = config.bookService(logger);

        System.out.println("\n--- Scope Check (Singleton vs. Prototype) ---");
        System.out.println("BookService 1 ID: " + bookServiceSingleton.getInstanceId().toString().substring(0, 8));
        System.out.println("BookService 2 ID: " + anotherBookServiceRef.getInstanceId().toString().substring(0, 8));
        logger.log("Result: IDs are the same -> Singleton Scope confirmed.");

        // 3. Setup Prototype Beans (TransactionService will be a new instance per call)
        TransactionService tx1 = config.newTransactionService(logger);
        TransactionService tx2 = config.newTransactionService(logger);

        System.out.println("TransactionService 1 ID: " + tx1.getTransactionId());
        System.out.println("TransactionService 2 ID: " + tx2.getTransactionId());
        logger.log("Result: IDs are different -> Prototype Scope confirmed.");


        // 4. Setup LibraryService (Demonstrates Constructor Injection)
        // LibraryService needs BookService (Singleton) and a fresh TransactionService (Prototype)
        LibraryService libraryService = new LibraryService(
                bookServiceSingleton,
                config.newTransactionService(logger) // Injecting a new Prototype instance
        );

        // 5. Setup MemberService (Demonstrates Setter Injection)
        MemberService memberService = new MemberService();
        // Manually performing Setter Injection
        memberService.setTransactionService(config.newTransactionService(logger));

        System.out.println("\n--- Usage Demonstration ---");

        // Use LibraryService (Constructor Injection)
        libraryService.processNewLoan("M001", "The Great Gatsby");

        // Use MemberService (Setter Injection)
        memberService.registerMember("Alice Johnson");
        memberService.checkOutBook("M002", "Moby Dick");

        System.out.println("\n--- Dependency Injection Container Simulation End ---");
    }
}
