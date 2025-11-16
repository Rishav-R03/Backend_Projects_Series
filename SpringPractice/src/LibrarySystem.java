/**
 * LibrarySystem.java
 *
 * This file contains a simulation of a Dependency Injection (DI) system
 * demonstrating multiple beans, different scopes, and various injection methods
 * as typically handled by a framework like Spring.
 */

import java.util.Date;
import java.util.UUID;

// --- 1. Java Config Bean (Simulated) ---

/**
 * Simulates a utility bean configured via Java Config.
 * In a real Spring environment, this would be created by a @Bean method.
 */
class SystemLogger {
    private final String startTime;

    public SystemLogger() {
        this.startTime = new Date().toString();
        System.out.println("-> [SystemLogger] Initialized at: " + this.startTime);
    }

    public void log(String message) {
        System.out.printf("[LOG %s] %s%n", this.startTime.substring(11, 19), message);
    }
}

// --- 2. Service Classes with Scopes and Injection Methods ---

/**
 * Service 1: BookService
 * Scope: Singleton (Default scope in Spring/DI)
 * Manages the static library catalog.
 * In Spring, this would use: @Service
 */
class BookService {
    private final SystemLogger logger;
    private final UUID instanceId;

    // Dependency on SystemLogger (injected via AppConfig)
    public BookService(SystemLogger logger) {
        this.logger = logger;
        this.instanceId = UUID.randomUUID();
        logger.log("BookService instance created (Singleton ID: " + instanceId.toString().substring(0, 8) + ")");
    }

    public String findBook(String title) {
        logger.log("Searching for book: " + title);
        if (title.equals("The Great Gatsby")) {
            return "Book found: The Great Gatsby (Available)";
        }
        return "Book not found: " + title;
    }

    public UUID getInstanceId() {
        return instanceId;
    }
}


/**
 * Service 2: TransactionService
 * Scope: Prototype (A new instance is created every time it's requested/injected)
 * Handles unique loan/return operations.
 * In Spring, this would use: @Service @Scope("prototype")
 */
class TransactionService {
    private final SystemLogger logger;
    private final String transactionId;

    public TransactionService(SystemLogger logger) {
        this.logger = logger;
        this.transactionId = "TX-" + UUID.randomUUID().toString().substring(0, 4);
        logger.log("TransactionService instance created (Prototype ID: " + transactionId + ")");
    }

    public void loanBook(String memberId, String bookTitle) {
        logger.log(String.format("[%s] Member %s loaned book '%s'", transactionId, memberId, bookTitle));

    }

    public String getTransactionId() {
        return transactionId;
    }
}

/**
 * Service 3: MemberService
 * Injection: Setter Injection
 * Manages member accounts and uses TransactionService.
 * In Spring, this would use: @Service
 */
class MemberService {
    private TransactionService transactionService; // Dependency to be injected via setter

    public MemberService() {
        System.out.println("-> [MemberService] Instance created.");
    }

    // Setter Injection Method (simulates @Autowired on the setter)
    public void setTransactionService(TransactionService transactionService) {
        this.transactionService = transactionService;
        System.out.println("-> [MemberService] TransactionService set via setter injection.");
    }

    public void registerMember(String name) {
        System.out.println("Member registered: " + name);
    }

    public void checkoutBook(String memberId, String bookTitle) {
        if (transactionService != null) {
            transactionService.loanBook(memberId, bookTitle);
        } else {
            System.err.println("Error: TransactionService not injected.");
        }
    }
}

/**
 * Service 4: LibraryService (The main facade)
 * Injection: Constructor Injection
 * Orchestrates the core library functionality.
 * In Spring, this would use: @Service
 */
class LibraryService {
    private final BookService bookService;        // Dependency 1 (Injected via constructor)
    private final TransactionService transactionService; // Dependency 2 (Injected via constructor)

    // Constructor Injection (simulates @Autowired on the constructor)
    public LibraryService(BookService bookService, TransactionService transactionService) {
        this.bookService = bookService;
        this.transactionService = transactionService;
        System.out.println("-> [LibraryService] Instance created with Constructor Injection.");
    }

    public void processNewLoan(String memberId, String bookTitle) {
        System.out.println("\n--- Starting New Loan Process (Orchestrated by LibraryService) ---");
        String bookStatus = bookService.findBook(bookTitle);
        System.out.println("Book Status Check: " + bookStatus);

        // Use the injected TransactionService
        transactionService.loanBook(memberId, bookTitle);
        System.out.println("Loan recorded by Transaction ID: " + transactionService.getTransactionId());
        System.out.println("--- Loan Process Complete ---");
    }
}


// --- 3. Java Configuration Class (Simulated) ---

/**
 * Simulates the Java Configuration class.
 * In Spring, this would use: @Configuration
 */
class AppConfig {
    /**
     * Simulates a @Bean method for creating a Singleton SystemLogger.
     */
    public SystemLogger systemLogger() {
        return new SystemLogger();
    }

    /**
     * Simulates a @Bean method for creating a Singleton BookService.
     * Dependencies (SystemLogger) are resolved by calling other @Bean methods.
     */
    public BookService bookService(SystemLogger logger) {
        return new BookService(logger);
    }

    /**
     * Simulates a @Bean method for creating a Prototype TransactionService.
     */
    public TransactionService newTransactionService(SystemLogger logger) {
        // NOTE: In a real DI container, fetching this bean multiple times would
        // result in a new instance due to the 'prototype' scope setting.
        return new TransactionService(logger);
    }
}

// --- 4. Main Application Class ---

public class LibrarySystem {

    public static void main(String[] args) {
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
        memberService.checkoutBook("M002", "Moby Dick");

        System.out.println("\n--- Dependency Injection Container Simulation End ---");
    }
}