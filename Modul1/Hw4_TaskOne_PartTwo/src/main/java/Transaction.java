import java.util.concurrent.Callable;

/**
 * Created by Andrii_Manieshyn on 24.02.14.
 */
public class Transaction implements Runnable{
    private BankService bankService;

    public Transaction(BankService bank) {
        this.bankService = bank;
    }

    @Override
    public void run() {
        int fromIndex = (int) (10 * Math.random());
        int toIndex = (int) (10 * Math.random());
        int amount = (int) (100 * Math.random());
        try {
            bankService.makeTransfer(fromIndex, toIndex, amount);
            Thread.sleep((long) (1000 * Math.random()));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
