import com.epam.cdp.BankService;
import com.epam.cdp.Transaction;

/**
 * Created by Andrii_Manieshyn on 24.02.14.
 */
public class MainTaskOne {

    private static final int NUMBER_OF_THREADS = 100000;

    public static void main (String [] args){
        BankService bankService = new BankService();
        for (int i = 0 ; i < NUMBER_OF_THREADS ; i++){
            Transaction transaction = new Transaction(bankService);
            new Thread(transaction).start();
            if (i % 1000 == 0){
                System.out.println(bankService.getTotal());
            }
        }
    }
}
