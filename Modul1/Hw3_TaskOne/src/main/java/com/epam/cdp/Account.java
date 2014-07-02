package com.epam.cdp;

/**
 * Created by Andrii_Manieshyn on 24.02.14.
 */
public class Account {
    private int balance;

    public Account(int initialBalance) {
        this.balance = initialBalance;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }
}
