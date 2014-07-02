package com.epam.cdp;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Andrii_Manieshyn on 24.02.14.
 */
public class BankService {

    private Account[] accounts = new Account[10];

    public BankService() {
        for (int ind = 0; ind < accounts.length; ind++) {
            Account acc = new Account(1000);
            accounts[ind] = acc;
        }
    }

    public boolean makeTransfer(int fromIndex, int toIndex, int amount) throws InterruptedException {
        Account accountFrom = accounts[fromIndex];
        Account accountTo = accounts[toIndex];
        while (true) {
            if (accountFrom.lock.tryLock()) {
                if (accountTo.lock.tryLock()) {
                    boolean result;
                    if (accountFrom.getBalance() < amount)
                        result = false;
                    else {
                        accountFrom.setBalance(accountFrom.getBalance() - amount);
                        Thread.sleep((long) (100 * Math.random()));
                        accountTo.setBalance(accountTo.getBalance() + amount);
                        result = true;
                    }
                    accountFrom.lock.unlock();
                    accountTo.lock.unlock();
                    return result;
                }
                accountFrom.lock.unlock();
            }
        }
    }

    public double getTotal() {
        int summ = 0;
        for (Account account : accounts) {
            summ += account.getBalance();
        }
        return summ;
    }


}
