package cdp;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andrii_Manieshyn on 24.02.14.
 */
public class MainTaskFourPartTwo {

    private static final int NUMBER_OF_THREADS = 1000;
    private static final int NUMBER_OF_THREADS_TO_OUT = 100;

    private static List<Thread> threadList = new ArrayList<Thread>();

    public static void main (String [] args) throws InterruptedException {
        BankService bankService = new BankService();
        for (int i = 0; i < NUMBER_OF_THREADS; i++) {
            Transaction transaction = new Transaction(bankService);
            Thread currentNewThread = new Thread(transaction);
            currentNewThread.start();
            threadList.add(currentNewThread);
            if (i % NUMBER_OF_THREADS_TO_OUT == 0 ){
                System.out.println(bankService.getTotal());
            }
        }
        for (Thread thread : threadList){
            thread.join();
        }
        System.out.println(bankService.getTotal());
    }
}
