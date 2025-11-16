package java.config;

import java.Services.BookService;
import java.Services.TransactionService;
import java.model.SystemLogger;

public class AppConfig {
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
