package java.Services;

import java.model.SystemLogger;
import java.util.UUID;

public class TransactionService {
    private final SystemLogger logger;
    private final String transactionId;

    public TransactionService(SystemLogger logger){
        this.logger = logger;
        this.transactionId = "TX-" + UUID.randomUUID().toString().substring(0,4);
        logger.log("TransactionService instance created (Prototype ID: " + transactionId);
    }

    public void loanBook(String memberId, String bookTitle){
        logger.log(String.format("[%s] Member %s loaned book '%s'", transactionId, memberId, bookTitle));

    }
    public String getTransactionId() {
        return transactionId;
    }

}
