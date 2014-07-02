/**
 * Created by Andrii_Manieshyn on 11.03.14.
 */
public class BankServiceTransfer {

    public boolean transfer(Account accountFrom, Account accountTo, int amount) throws InterruptedException {
        boolean result;
        if (accountFrom.getBalance() < amount)
            result = false;
        else {
            accountFrom.setBalance(accountFrom.getBalance() - amount);
            Thread.sleep((long) (100 * Math.random()));
            accountTo.setBalance(accountTo.getBalance() + amount);
            result = true;
        }

        return result;
    }
}
