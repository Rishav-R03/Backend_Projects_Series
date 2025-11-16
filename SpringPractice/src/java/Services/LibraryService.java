package java.Services;

public class LibraryService {
    private final BookService bookService;
    private final TransactionService transactionService;

    public LibraryService(BookService bookService, TransactionService transactionService){
        this.bookService = bookService;
        this.transactionService = transactionService;
        System.out.println("-> [LibraryService] Instance created with Constructor Injection");
    }

    public void processNewLoan(String memberId, String bookTitle){
        System.out.println("\n---Starting New Loan Process (Orchestrated by LibraryService)---");
        String bookStatus = bookService.findBook(bookTitle);
        System.out.println("Book status: " + bookStatus);

        transactionService.loanBook(memberId,bookTitle);
        System.out.println("Loan recoreded by Transaction Id: " + transactionService.getTransactionId());
        System.out.println("--- Loan Process Complete ---");
    }
}
