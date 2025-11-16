package java.Services;

public class MemberService {
    private TransactionService transactionService;
    public MemberService(){
        System.out.println("-> [MemberService] Instance created");
    }
    public void setTransactionService(TransactionService transactionService){
        this.transactionService = transactionService;
        System.out.println("-> [MemberService] TransactionService set via setter");
    }
    public void registerMember(String name){
        System.out.println("Member registered: " + name);
    }

    public void checkOutBook(String memberId, String bookTitle) {
        if (transactionService != null) {
            transactionService.loanBook(memberId, bookTitle);
        } else {
            System.err.println("Error: TransactionService not injected.");
        }
    }
}
