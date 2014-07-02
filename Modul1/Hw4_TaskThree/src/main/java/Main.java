import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Andrii_Manieshyn on 24.02.14.
 */
public class Main {

    public static final int NUMBER_OF_THREADS = 100;

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(20);
        CountDownLatch countDownLatch = new CountDownLatch(10);
        BankService bankService = new BankService(countDownLatch);
        for (int i = 0; i < NUMBER_OF_THREADS; i++) {
            Transaction transaction = new Transaction(bankService);
            countDownLatch.countDown();
            executorService.execute(transaction);
        }
        executorService.shutdown();
    }
}

