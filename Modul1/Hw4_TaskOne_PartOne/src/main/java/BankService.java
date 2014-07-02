import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * Created by Andrii_Manieshyn on 24.02.14.
 */
public class BankService {

    private CyclicBarrier cyclicBarrier = new CyclicBarrier(MainTaskFourPartOne.NUMBER_OF_THREADS);

    private Account[] accounts = new Account[10];

    public BankService() {
        for (int ind = 0; ind < accounts.length; ind++) {
            Account acc = new Account(1000);
            accounts[ind] = acc;
        }
    }

    public boolean makeTransfer(int fromIndex, int toIndex, int amount) throws InterruptedException, BrokenBarrierException {
        cyclicBarrier.await();
        if (fromIndex != toIndex) {
            Account accountFrom = accounts[fromIndex];
            Account accountTo = accounts[toIndex];
            while (true) {
                if (accountFrom.semaphore.tryAcquire()) {
                    if (accountTo.semaphore.tryAcquire()) {
                        boolean result;
                        if (accountFrom.getBalance() < amount)
                            result = false;
                        else {
                            accountFrom.setBalance(accountFrom.getBalance() - amount);
                            Thread.sleep((long) (100 * Math.random()));
                            accountTo.setBalance(accountTo.getBalance() + amount);
                            result = true;
                        }
                        accountFrom.semaphore.release();
                        accountTo.semaphore.release();
                        System.out.println("DONE");
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
