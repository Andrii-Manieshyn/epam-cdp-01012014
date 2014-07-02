import java.util.concurrent.CountDownLatch;

/**
 * Created by Andrii_Manieshyn on 24.02.14.
 */
public class Main {

    public static final int NUMBER_OF_THREADS = 100;


    public static void main(String[] args) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(NUMBER_OF_THREADS);
        BankService bankService = new BankService(countDownLatch);
        for (int i = 0; i < NUMBER_OF_THREADS; i++) {
            Transaction transaction = new Transaction(bankService);
            new Thread(transaction).start();
            countDownLatch.countDown();
        }
    }
}

