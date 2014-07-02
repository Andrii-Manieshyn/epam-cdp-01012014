import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Andrii_Manieshyn on 24.02.14.
 */
public class MainTaskFourPartOne {

    public static final int NUMBER_OF_THREADS = 100;

    /**
     *
     * @param args
     * @throws InterruptedException
     */

    public static void main (String [] args) throws InterruptedException {
        BankService bankService = new BankService();
        for (int i = 0; i < NUMBER_OF_THREADS; i++) {
            Transaction transaction = new Transaction(bankService);
            new Thread(transaction).start();
        }
    }
}
