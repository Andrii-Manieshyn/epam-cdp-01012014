import java.util.concurrent.CountDownLatch;

/**
 * Created by Andrii_Manieshyn on 24.02.14.
 */
public class BankService {

    private CountDownLatch countDownLatch;

    private Account[] accounts = new Account[10];

    private BankServiceTransfer bankServiceTransfer;

    public BankService(CountDownLatch countDownLatch) {
        bankServiceTransfer = new BankServiceTransfer();
        this.countDownLatch = countDownLatch;
        for (int ind = 0; ind < accounts.length; ind++) {
            Account acc = new Account(1000);
            accounts[ind] = acc;
        }
    }

    public boolean makeTransfer(int fromIndex, int toIndex, int amount) throws InterruptedException {

        countDownLatch.await();

        if (fromIndex != toIndex) {
            Account accountFrom = accounts[fromIndex];
            Account accountTo = accounts[toIndex];
            while (true) {
                if (accountFrom.semaphore.tryAcquire()) {
                    if (accountTo.semaphore.tryAcquire()) {
                        boolean result = bankServiceTransfer.transfer(accountFrom,accountTo,amount);
                        accountFrom.semaphore.release();
                        accountTo.semaphore.release();

                        return result;
                    }
                    accountFrom.semaphore.release();
                }
            }
        }
        return true;
    }

    public double getTotal() {
        for (Account account : accounts) {
            while (true) {
                if (account.semaphore.tryAcquire()) {
                    break;
                }
            }
        }
        int summ = 0;
        for (Account account : accounts) {
            summ += account.getBalance();
            account.semaphore.release();
        }
        return summ;
    }

}
