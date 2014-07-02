import junit.framework.Assert;
import org.junit.Test;

/**
 * Created by Andrii_Manieshyn on 11.03.14.
 */

public class BankServiceTransferTest {

    BankServiceTransfer bankServiceTransfer = new BankServiceTransfer();

    @Test
    public void whenTransfereOccured_SummBeforeShouldBeEqualAfter() throws InterruptedException {
        Account accountFrom  = new Account(200);
        Account accountTo  = new Account(200);
        Assert.assertEquals(accountFrom.getBalance() + accountTo.getBalance(), 400);
        bankServiceTransfer.transfer(accountFrom, accountTo, 100);
        Assert.assertEquals(accountFrom.getBalance() + accountTo.getBalance(), 400);
    }

    @Test
    public void whenTransfereOccured_ammountOfMoneyShouldSetTheVlue() throws InterruptedException {
        Account accountFrom  = new Account(200);
        Account accountTo  = new Account(200);
        Assert.assertEquals(accountTo.getBalance(), 200);
        Assert.assertEquals(accountFrom.getBalance(), 200);
        bankServiceTransfer.transfer(accountFrom, accountTo, 100);
        Assert.assertEquals(accountTo.getBalance(), 300);
        Assert.assertEquals(accountFrom.getBalance(), 100);
    }
}
