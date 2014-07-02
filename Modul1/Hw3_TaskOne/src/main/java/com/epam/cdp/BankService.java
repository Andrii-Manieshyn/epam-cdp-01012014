package com.epam.cdp;

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
        if (accountFrom.getBalance() < amount)
            return false;
        else {
            accountFrom.setBalance(accountFrom.getBalance() - amount);
            Thread.sleep((long) (100 * Math.random()));
            accountTo.setBalance(accountTo.getBalance() + amount);
            return true;
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
